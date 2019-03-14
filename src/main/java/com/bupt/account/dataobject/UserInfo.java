package com.bupt.account.dataobject;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "user_info")
@Entity
public class UserInfo {

    /**自增主键id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    /**主键open_id*/
    private String openid;

    /**密码*/
    private String password;

    /**目前状态*/
    private int status ;

    /**邮箱*/
    private String email;

    /**电话*/
    private String phone;

    /**三个附加段 目前无用*/
    private String add1;
    private String add2;
    private String add3;

}
