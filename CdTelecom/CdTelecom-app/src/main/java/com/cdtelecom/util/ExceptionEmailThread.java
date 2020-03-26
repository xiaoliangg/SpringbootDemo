package com.cdtelecom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class ExceptionEmailThread implements Runnable{
	public static Logger logger = LoggerFactory.getLogger(ExceptionEmailThread.class);

	private String systemName;
	private String interfaceName;
	private String exceptionDesc;
	private String receiver;
	private String department;
	
	public ExceptionEmailThread(String systemName, String interfaceName, String exceptionDesc, String receiver, String department) {
		this.systemName = systemName;
		this.interfaceName = interfaceName;
		this.exceptionDesc = exceptionDesc;
		this.receiver = receiver;
		this.department = department;
	}
	
	@Override
	public void run() {
		logger.info("发送邮件失败，分别发送给收件人:" + receiver);
		try {
			ExceptionEmailUtil.sendExceptionMail(systemName, interfaceName, exceptionDesc, receiver, department);
		} catch (AddressException e) {
			logger.info("分别发送给收件人失败:" + receiver);
			e.printStackTrace();
		} catch (MessagingException e) {
			logger.info("分别发送给收件人失败:" + receiver);
			logger.error("errorMsg:" + e);
		}
	}

}
