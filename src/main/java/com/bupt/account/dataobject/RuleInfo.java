package com.bupt.account.dataobject;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table(name = "rule_info")
@Entity
public class RuleInfo {

    /**自增主键id*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    /**设备id*/
    private String deviceId;

    /**操作*/
    private String op;

    /**时期*/
    private Timestamp date ;

    /**用户id*/
    private String userId;
    /**规则id*/
    @Id
    private String ruleId;

    /**
     * 是否已上传
     */
    private String status;

    private String add3;

}
