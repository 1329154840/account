package com.bupt.account.enums;

import lombok.Getter;

@Getter
public enum UserType {
    ADMINSTRATOR (0,"管理员"),
    NORMAL(1,"普通用户"),
    ;
    private Integer code;
    private String message;
    UserType(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
