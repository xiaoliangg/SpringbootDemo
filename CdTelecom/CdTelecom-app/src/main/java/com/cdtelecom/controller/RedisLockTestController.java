package com.cdtelecom.controller;

import com.cdtelecom.pojo.request.ValidateTestBean;
import com.cdtelecom.pojo.response.BasicResponse;
import com.cdtelecom.pojo.response.QueryResponse;
import com.cdtelecom.redis.RedisLockUtil;
import com.cdtelecom.redis.concurrent.locks.RedisReentrantLock;
import com.cdtelecom.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@EnableAutoConfiguration
public class RedisLockTestController {
	public static Logger logger = LoggerFactory.getLogger(RedisLockTestController.class);
	@Autowired
	Validator globalValidator;

	@Value("${testP}")
	private String  testP;

	private final static RedisReentrantLock redisReentrantLock = new RedisReentrantLock("keyForLock12");
	/**
	 * 测试redis分布式锁 RedisReentrantLock
	 * 测试方法:读取出某个磁盘文件的数，+1后再写入。多体*多线程来操作
	 * @param request
	 * @return
	 */
	@RequestMapping("/redisReentrantLockMultiple")
	public BasicResponse redisReentrantLockMultiple(ValidateTestBean request){

		redisReentrantLock.lock();
		sleep(10);
		addAndWrite();
		redisReentrantLock.unlock();

		QueryResponse r = new QueryResponse();
		r.setCommSeq("111");
		return r;
	}
	private static final AtomicInteger failTimes = new AtomicInteger();

	@RequestMapping("/redisLockUtilMultiple")
	public BasicResponse redisLockUtilMultiple(ValidateTestBean request){

		boolean isWrited = false;
		for(int i = 0;i<1000;i++){
			if(RedisLockUtil.lock("lockKey23")){
				addAndWrite();
				RedisLockUtil.unLock("lockKey23");
				isWrited = true;
				break;
			}
			sleep(10);
		}
		if(!isWrited){
			failTimes.incrementAndGet();
		}


		QueryResponse r = new QueryResponse();
		r.setCommSeq(failTimes + "");
		return r;
	}

	private void sleep(long time) {
		try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	private void addAndWrite() {
		File f = new File("C:\\Users\\ll\\Desktop\\1.txt");
		List<String> dataList = IOUtil.importCsv(f);
		int number = Integer.parseInt(dataList.get(0));
		number++;
		dataList = new ArrayList<>();
		dataList.add(number + "");
		IOUtil.exportCsv(f,dataList);
	}

}
