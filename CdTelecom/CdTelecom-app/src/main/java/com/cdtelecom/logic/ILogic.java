package com.cdtelecom.logic;


import com.cdtelecom.pojo.response.BasicResponse;
import com.cdtelecom.pojo.response.QueryResponse;

public interface ILogic {
	public QueryResponse operate(String requestStr, String triggerType);
}
