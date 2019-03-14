package com.bupt.account.enums;

import lombok.Getter;

@Getter
public enum LoginReturn {
    FAIL(0,"登陆失败"),
    OK(1,"登陆成功")
    ;
    private Integer code;
    private String message;
    LoginReturn(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
