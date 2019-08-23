package com.cdtelecom.logic;


import com.cdtelecom.pojo.response.BasicResponse;

public interface ILogic {
	public BasicResponse operate(String requestStr, String triggerType);
}
