package com.cdtelecom.controller;

import com.cdtelecom.Exception.BusinessException;
import com.cdtelecom.mapper.AssetInfoMapper;
import com.cdtelecom.po.AssetInfo;
import com.cdtelecom.pojo.request.BasicRequest;
import com.cdtelecom.pojo.request.ValidateTestBean;
import com.cdtelecom.pojo.response.BasicResponse;
import com.cdtelecom.pojo.response.QueryResponse;
import com.cdtelecom.service.TransactionTestService;
import com.cdtelecom.task.TestTask;
import com.cdtelecom.util.GsonUtil;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

@RestController
@EnableAutoConfiguration
public class CdTelecomController {
	public static Logger logger = LoggerFactory.getLogger(CdTelecomController.class);
	//	protected final Log logger = LogFactory.getLg(this.getClass());
	@Autowired
	private AssetInfoMapper assetInfoMapper;
	@Autowired
	private TestTask testTask;
	@Autowired
	private TransactionTestService transactionTestService;
	@Autowired
	Validator globalValidator;
//	@Autowired
//	private MyAppConfiguration configuration;

	@Value("#{${bizMap}}")
	private Map<String, String> bizMap;
//	private Map<String, String> bizMap = new HashMap<String, String>();

	@Value("${testP}")
	private String  testP;

	@RequestMapping("/cdTelecomBiz")
//    public BasicResponse getCountryByIp(String request) {
    public BasicResponse getCountryByIp(@RequestBody BasicRequest request) {
		logger.info("requestBean:" + request);

		logger.info(bizMap.get(request.getBusiType()));


        QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		r.setErrorCode("0");
		r.setErrorDesc("成功");
		r.setDataFlow("999");
		String responseStr = GsonUtil.getJSONString(r);
		logger.info("responseStr:" + responseStr);

		return r;
	}

	@RequestMapping("/test")
	public BasicResponse test(@RequestBody BasicRequest request) {
		logger.info("requestStr:" + request);
		logger.info("testP:" + testP);

		try {
			testTask.reportCurrentTimeCron();
		}catch(Exception e){
			logger.info("" + e);
		}

		//测试yaml注入map
		logger.info(bizMap.get("A01"));
		logger.info(bizMap.get("A02"));
//		logger.info(configuration.toString());

		logger.info("事务性测试开始");
		try {
			transactionTestService.testTransaction();
		} catch (Exception e) {
			logger.info("" + e);
		}
		logger.info("事务性测试完成！");

		//test 数据库
		AssetInfo info = new AssetInfo();
		info.setImsi("1234567");
		info.setIccid("123456789");
		assetInfoMapper.insert(info);

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		r.setErrorCode("0");
		r.setErrorDesc("成功");
		r.setDataFlow("999");
		return r;
	}

	@RequestMapping("/testException") //测试 @ControllerAdvice + @ExceptionHandler 全局处理 Controller 层异常
	public BasicResponse testException(@RequestBody BasicRequest request) throws Exception{

		String[] arr = {"1","2"};
		//测试抛出Exception
//		System.out.println(arr[3]);
		//测试抛出BusinessException
		try {
			System.out.println(arr[3]);
		}catch(Exception e){
			throw new BusinessException("BusinessException222");
		}

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		r.setErrorCode("0");
		r.setErrorDesc("成功");
		return r;
	}
	@RequestMapping("/testUncheckedException") //测试抛出未检查异常
	public BasicResponse testUncheckedException(@RequestBody BasicRequest request){
		String[] arr = {"1","2"};
		System.out.println(arr[3]);

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		r.setErrorCode("0");
		r.setErrorDesc("成功");
		return r;
	}

	@RequestMapping("/testValidateException") //测试validate异常
	public BasicResponse testValidateException(@Validated(Update.class) @RequestBody ValidateTestBean request){
		String[] arr = {"1","2"};
		System.out.println(arr[3]);

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		r.setErrorCode("0");
		r.setErrorDesc("成功");
		return r;
	}
	@RequestMapping("/testValidateException2") //测试validate异常
	public BasicResponse testValidateException2(@RequestBody ValidateTestBean request){

		//手动校验-分组校验
		Set<ConstraintViolation<ValidateTestBean>> set = globalValidator.validate(request,Update.class);

		for (ConstraintViolation<ValidateTestBean> constraintViolation : set) {
			System.out.println(constraintViolation.getMessage());
		}

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		return r;
	}
//	@RequestMapping("/hello3")
//	String home() {
//		return "Hello World3!";
//	}


	public Map<String, String> getBizMap() {
		return bizMap;
	}

	public void setBizMap(Map<String, String> bizMap) {
		this.bizMap = bizMap;
	}

//	public MyAppConfiguration getConfiguration() {
//		return configuration;
//	}
//
//	public void setConfiguration(MyAppConfiguration configuration) {
//		this.configuration = configuration;
//	}

}
