package com.bupt.account.enums;

public enum BadResult {
    /**
     * BadResultCode
     */
    Token_Access_Fail(2,"Toekn 认证失败，你没有权限"),

    Rule_Is_Null(3,"rule不存在"),

    Group_Is_Null(4,"group不存在"),

    System_Error(5,"系统异常"),

    Rule_Upload_Error(6,"规则上传失败"),

    Rule_Json_Error(7, "json解析错误"),

    Date_Error(8, "date解析错误"),

    Job_Is_Null(9 , "定时任务不存在");


    BadResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getmsg() {
        return msg;
    }

    private Integer code;

    private String msg;
}