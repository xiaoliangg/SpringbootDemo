package com.cdtelecom.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;


/**
 * 异常邮件发送工具类
 * @author asus
 *
 */
public class ExceptionEmailUtil {
    public static Logger logger = LoggerFactory.getLogger(ExceptionEmailUtil.class);

    private static final String defaultPlatFormReceiver="xiaoliangyuu@163.com";//管理平台默认邮件接收人
    private static final String defaultOperatorReceiver="xiaoliangyuu@163.com";//运营商支持默认邮件接收人

    /**
     * 异常邮件发送
     * @param systemName 系统名称
     * @param interfaceName 接口名称
     * @param exceptionDesc 异常描述
     * @param receiver 邮件接收人(多个接收人时,邮箱间用","(英文)分割)
     * @param department 归属部门 1-管理平台部门 2-运营商支持部门 （当填写的邮件接收无效时，根据部门标识来发送给该部门直属上级）
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendExceptionMail(String systemName,String interfaceName,String exceptionDesc,String receiver,String department) throws MessagingException{
        boolean valid=true;
        //创建一封邮件
        //用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        Properties properties = new Properties();
        // 创建信件服务器
        properties.put("mail.smtp.host", "smtp.exmail.qq.com");//主机host，跟邮件发送者必须一致
        properties.put("mail.smtp.auth", "true"); // 通过验证，也就是用户名和密码的验证，必须要有这一条　
        properties.put("mail.smtp.port", 465);//加密服务端口465
//      properties.put("mail.smtp.ssl.enable", true);

        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", "smtp");

        //解决  阿里云上 Could not connect to SMTP host: smtp.163.com, port: 25
        final String smtpPort = "465";
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

        properties.put("mail.smtp.ssl.enable", "true");//加密
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                //登陆邮箱，密码
                return new PasswordAuthentication("platformwarning@linksfield.net","Links2019");
            }
        });
        session.setDebug(true);
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //邮件几个必须的：发件人，收件人，邮件主题，邮件内容
        //1、from :发件人
        //其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress("platformwarning@linksfield.net"));
        //2、TO :收件人
        //MimeMessage.RecipientType.TO  直接发送人
        //MimeMessage.RecipientType.CC  抄送人（可选）
        //MimeMessage.RecipientType.BCC  秘密发送人（可选）
        //验证收件人是否存在（不存在，默认发送给领导）
//            if(!StringHelper.isEmpty(receiver)){
//            	String[] receivers = receiver.split(",");
//            	for(String receive:receivers){
//                  valid = valid(receive,"smtp.exmail.qq.com");
//                  if(!valid){
//                	  logger.info("邮箱验证失败:" + receive);
//                  }
//            	}
//            	System.out.println("验证结果:"+valid);
//                if(!valid){
//                	if("1".equals(department)){//归属管理平台
//                		receiver=defaultPlatFormReceiver;
//                	}else if("2".equals(department)){
//                		receiver=defaultOperatorReceiver;
//                	}
//                }else{
//              	  logger.info("全部邮箱验证成功:" + receiver);
//                }
//            }
        InternetAddress[] internetAddressTo = new InternetAddress().parse(receiver);
        message.setRecipients(MimeMessage.RecipientType.TO, internetAddressTo);
        //3、Suject :邮件主题
        message.setSubject(systemName,"UTF-8");
        //邮件内容
        ///邮件的内容
        //4、Content :邮件正文（可以使用html标签）
        message.setContent(exceptionDesc, "text/html;charset=utf-8");
        //5、设置显示的发件时间
        message.setSentDate(new Date());
        //6、保存前面设置的
        message.saveChanges();
        //7、发送

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            if(receiver.contains(",")){
                logger.info("群发邮件失败，单独发送给各个用户");
                String[] receivers = receiver.split(",");
                for(String r:receivers){
                    new Thread(new ExceptionEmailThread(systemName, interfaceName, exceptionDesc, r, department)).start();
                }
            }else{
                logger.info("群发邮件失败，且收件人为单个用户，不再重复发送！");
            }
        }
        System.out.println("邮件已经发送完毕");
    }

    /**
     * 异常邮件发送
     * @param systemName 系统名称
     * @param interfaceName 接口名称
     * @param exceptionDesc 异常描述
     * @param receiver 邮件接收人(多个接收人时,邮箱间用","(英文)分割)
     * @param department 归属部门 1-管理平台部门 2-运营商支持部门 （当填写的邮件接收无效时，根据部门标识来发送给该部门直属上级）
     * @param file 附件
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendExceptionMailWithFile(String systemName,String interfaceName,String exceptionDesc,String receiver,String department,File file) throws MessagingException, UnsupportedEncodingException {
        boolean valid=true;
        //创建一封邮件
        //用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        Properties properties = new Properties();
        // 创建信件服务器
        properties.put("mail.smtp.host", "smtp.exmail.qq.com");//主机host，跟邮件发送者必须一致
        properties.put("mail.smtp.auth", "true"); // 通过验证，也就是用户名和密码的验证，必须要有这一条　
        properties.put("mail.smtp.port", 465);//加密服务端口465
//      properties.put("mail.smtp.ssl.enable", true);

        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", "smtp");

        //解决  阿里云上 Could not connect to SMTP host: smtp.163.com, port: 25
        final String smtpPort = "465";
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);

        properties.put("mail.smtp.ssl.enable", "true");//加密
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                //登陆邮箱，密码
                return new PasswordAuthentication("platformwarning@linksfield.net","Links2019");
            }
        });
        session.setDebug(true);
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //邮件几个必须的：发件人，收件人，邮件主题，邮件内容
        //1、from :发件人
        //其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress("platformwarning@linksfield.net"));
        InternetAddress[] internetAddressTo = new InternetAddress().parse(receiver);
        message.setRecipients(MimeMessage.RecipientType.TO, internetAddressTo);
        //3、Suject :邮件主题
        message.setSubject(systemName,"UTF-8");
        ///邮件的内容
        //4、Content :邮件正文（可以使用html标签）
        message.setContent(exceptionDesc, "text/html;charset=utf-8");

        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();
        // 添加邮件正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(exceptionDesc, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);

        // 添加附件的内容
        if (file != null) {
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            attachmentBodyPart.setDataHandler(new DataHandler(source));

            // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

            //MimeUtility.encodeWord可以避免文件名乱码
            attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
            multipart.addBodyPart(attachmentBodyPart);
        }

        // 将multipart对象放到message中
        message.setContent(multipart);

        //5、设置显示的发件时间
        message.setSentDate(new Date());
        //6、保存前面设置的
        message.saveChanges();
        //7、发送

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            if(receiver.contains(",")){
                logger.info("群发邮件失败，单独发送给各个用户");
                String[] receivers = receiver.split(",");
                for(String r:receivers){
                    new Thread(new ExceptionEmailThread(systemName, interfaceName, exceptionDesc, r, department)).start();
                }
            }else{
                logger.info("群发邮件失败，且收件人为单个用户，不再重复发送！");
            }
        }
        System.out.println("邮件已经发送完毕");
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {

        String systemName = "系统名Sysname";
        String interfaceName = "接口名interface";
        String exceptionDesc = "异常信息exception";
        String receiver = "yuliang@linksfield.net";
        String department = "1";
        File file = new File("C:/Users/ll/Desktop/a03_cdr/vnm_cdr/Flexy20191209.xml");
        sendExceptionMailWithFile(systemName,interfaceName,exceptionDesc,receiver,department,file);
    }
    /**
     * 验证邮箱是否存在
     * <br>
     * 由于要读取IO，会造成线程阻塞
     *
     * @param toMail
     *         要验证的邮箱
     * @param domain
     *         发出验证请求的域名(是当前站点的域名，可以任意指定)
     * @return
     *         邮箱是否可达
     */
    public static boolean valid(String toMail, String domain) {
        if(StringUtils.isBlank(toMail) || StringUtils.isBlank(domain)) return false;
        if(!StringUtils.contains(toMail, '@')) return false;
        String host = toMail.substring(toMail.indexOf('@') + 1);
        if(host.equals(domain)) return false;
        Socket socket = new Socket();
        try {
            // 查找mx记录
            Record[] mxRecords = new Lookup(host, Type.MX).run();
            if(ArrayUtils.isEmpty(mxRecords)) return false;
            // 邮件服务器地址
            String mxHost = ((MXRecord)mxRecords[0]).getTarget().toString();
            if(mxRecords.length > 1) { // 优先级排序
                List<Record> arrRecords = new ArrayList<Record>();
                Collections.addAll(arrRecords, mxRecords);
                Collections.sort(arrRecords, new Comparator<Record>() {

                    public int compare(Record o1, Record o2) {
                        return new CompareToBuilder().append(((MXRecord)o1).getPriority(), ((MXRecord)o2).getPriority()).toComparison();
                    }

                });
                mxHost = ((MXRecord)arrRecords.get(0)).getTarget().toString();
            }
            // 开始smtp
            socket.connect(new InetSocketAddress(mxHost, 25));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 超时时间(毫秒)
            long timeout = 6000;
            // 睡眠时间片段(50毫秒)
            int sleepSect = 50;

            // 连接(服务器是否就绪)
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
                return false;
            }

            // 握手
            bufferedWriter.write("HELO " + domain + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 身份
            bufferedWriter.write("MAIL FROM: <check@" + domain + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 验证
            bufferedWriter.write("RCPT TO: <" + toMail + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 断开
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();
            return true;
        } catch (NumberFormatException e) {
        } catch (TextParseException e) {
        } catch (IOException e) {
        } catch (InterruptedException e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return false;
    }

    private static int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for(long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if(bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                while(bufferedReader.ready())
                    /*System.out.println(*/bufferedReader.readLine()/*)*/;
                /*System.out.println(outline);*/
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }

}
