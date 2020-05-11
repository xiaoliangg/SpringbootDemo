package com.cdtelecom.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) //使用确定的端口
@ActiveProfiles("dev")
public class CdTelecomControllerTest {

    //（此处的MockMvc 实例化是通过手工方式创建，如果想通过spring的bean注入方式的话，在类上加注解@AutoConfigureMockMvc，然后在成员变量mockMvc上加注解@Autowired）
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before //准备测试环境
    public void setUp() {
        //此种方式可通过spring上下文来自动配置一个或多个controller
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //此种方式，手工指定想要的controller。
        //注意:此种方式，Controller的注入的属性和实例都为空
//        mockMvc = MockMvcBuilders.standaloneSetup(new Controller1(), new Controller2()).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(new CdTelecomController()).build();
    }

    @Test
    public void testController() throws Exception {
        RequestBuilder request = null;
        //构造请求
//        request = post("/cdTelecomBiz").param("commSeq", "1001");
        request = post("/testAspectAndMock")
                .contentType("application/json")
                .content("{\"busiType\":\"27\",\"commSeq\":\"152019110819046639\",\"imsi\":\"204046848673262\",\"packageCode\":\"bootstrap_c92\",\"supplierCode\":\"1005\",\"supplierName\":\"C9\"}");
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andExpect(jsonPath("$.errorCode", not("1")))//使用jsonPath解析JSON返回值，判断具体的内容, 此处不希望status返回E
//                .andExpect(jsonPath("$.status",nullValue())) //会报错  java.lang.AssertionError: No value at JSON path "$.status"
                .andDo(print())//打印结果
                .andReturn();//想要返回结果，使用此方法
    }
}