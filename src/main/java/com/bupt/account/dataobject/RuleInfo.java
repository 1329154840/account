package com.bupt.account.dataobject;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "rule_info")
@Entity
public class RuleInfo {

    /**自增主键id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    /**设备id*/
    private String deviceId;

    /**操作*/
    private String op;

    /**时期*/
    private Date date ;

    /**用户id*/
    private String userId;

    private String add1;
    private String add2;
    private String add3;

}