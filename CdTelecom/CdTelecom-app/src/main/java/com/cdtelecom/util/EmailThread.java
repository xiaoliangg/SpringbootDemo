package com.cdtelecom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailThread implements Runnable {
    public static Logger logger = LoggerFactory.getLogger(EmailThread.class);

    private String systemName;
    private String interfaceName;
    private String exceptionDesc;
    private String receiver;
    private String department;

    public EmailThread(String systemName, String interfaceName, String exceptionDesc, String receiver, String department) {
        this.systemName = systemName;
        this.interfaceName = interfaceName;
        this.exceptionDesc = exceptionDesc;
        this.receiver = receiver;
        this.department = department;
    }

    @Override
    public void run() {
        try{
            ExceptionEmailUtil.sendExceptionMail(this.systemName, this.interfaceName,this.exceptionDesc,this.receiver, this.department);
        }catch(Exception e2){
            logger.error("发送邮件失败:" ,e2);
        }
    }
}
