package com.bupt.account.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class RuleDto {
    private String id;

    private String op;

    private long date;
}
