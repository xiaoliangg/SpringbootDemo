package com.cdtelecom.pojo.response;

import java.io.Serializable;

public class BasicResponse {

//	public static final String CODE_SUCCESS = "0";
	private String commSeq;
	private String errorCode;
	private String errorDesc;

	public String getCommSeq() {
		return commSeq;
	}
	public void setCommSeq(String commSeq) {
		this.commSeq = commSeq;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
}
