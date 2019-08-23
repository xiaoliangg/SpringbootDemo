package com.cdtelecom.pojo.request;

import lombok.Data;
import org.apache.ibatis.annotations.Update;
import javax.validation.constraints.NotNull;

@Data //@Data 注解是 **Lombok** 项目的注解，可以使我们不用再在代码里手动加 getter & setter。
public class ValidateTestBean {
    @NotNull(message = "10010-commSeq不能为空", groups = {Update.class})
//    @NotNull(message = "10010-commSeq不能为空")
    private String commSeq;

    private String supplierCode;
    private String supplierName;
    private String busiType;
}
