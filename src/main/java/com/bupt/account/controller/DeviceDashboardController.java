package com.bupt.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeviceDashboardController {

    @RequestMapping("/device_dashboard")
    public String device_dashboard(){
        return "device_dashboard";
    }
}
