package com.bupt.account.enums;

import lombok.Getter;

@Getter
public enum RegisterReturn {
    FAIL(0,"注册失败"),
    OK(1,"注册成功"),
    REPEAT(2,"name重复")
    ;
    private Integer code;
    private String message;
    RegisterReturn(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
