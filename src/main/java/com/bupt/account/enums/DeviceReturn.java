package com.bupt.account.enums;

import lombok.Getter;

@Getter
public enum DeviceReturn {
    FAIL(1,"失败"),
    OK(0,"成功")
    ;
    private Integer code;
    private String message;
    DeviceReturn(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
