package com.bupt.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Slf4j
public class Jump {
    @GetMapping("/dashboard")
    public String dashboard(){
        return "device_dashboard";
    }
    @GetMapping("/components")
    public String components(){
        return "device_components";
    }
}
