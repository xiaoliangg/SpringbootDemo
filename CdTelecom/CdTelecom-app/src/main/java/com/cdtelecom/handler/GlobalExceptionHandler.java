package com.cdtelecom.handler;

import com.cdtelecom.Exception.BusinessException;
import com.cdtelecom.controller.CdTelecomController;
import com.cdtelecom.pojo.response.QueryResponse;
import com.cdtelecom.util.EmailThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    public static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//    @ExceptionHandler(Exception.class) //如果 @ExceptionHandler 注解中未声明要处理的异常类型，则默认为参数列表中的异常类型
//    @ResponseBody
//    String handleException(){
//        return "Exception Deal!";
//    }

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    QueryResponse handleException(Exception e){
        logger.error("打印异常信息:" + e.getMessage(), e);

        QueryResponse r = new QueryResponse();
        r.setCommSeq("111");
        r.setErrorCode("0");
        r.setErrorDesc("默认失败");
        r.setDataFlow("999");
        sendEmail("testMsg:" + e + "<br>");
        return r;
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    QueryResponse handleBusinessException(BusinessException e){
        logger.error("打印异常信息:" + e.getMessage(), e);

        QueryResponse r = new QueryResponse();
        r.setCommSeq("2222");
        r.setErrorCode("1");
        r.setErrorDesc("失败原因:" + e.getMessage());
        r.setDataFlow("1000");
        sendEmail("testMsg:" + e + "<br>");
        return r;
    }

    /**
     * 处理所有接口数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    QueryResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.error(e.getMessage(), e);
        QueryResponse r = new QueryResponse();
        r.setCommSeq("2222");
        r.setErrorCode("1");
        r.setErrorDesc("失败原因:" + e.getBindingResult().getFieldError().getDefaultMessage());
        r.setDataFlow("1000");
        sendEmail("testMsg:" + e + "<br>");

        return r;
    }

    private void sendEmail(String errorMsg) {

        try{
            new Thread(new EmailThread("test", "",errorMsg,"1535523313@qq.com,xiaoliangyuu@163.com", "")).start();
        }catch(Exception e2){
            logger.error("发送邮件失败:" ,e2);
        }
    }
}
