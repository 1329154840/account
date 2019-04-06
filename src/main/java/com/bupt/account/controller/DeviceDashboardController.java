package com.bupt.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现页面跳转
 */
@Controller
public class DeviceDashboardController {

    @RequestMapping("/device_admi_dashboard")
    public String device_admi_dashboard(){
        return "device_admi_dashboard";
    }

    @RequestMapping("/device_user_dashboard")
    public String device_user_dashboard(){
        return "device_user_dashboard";
    }

    @RequestMapping("/device_admi_components")
    public String device_admi_components(){
        return "device_admi_components";
    }

    @RequestMapping("/device_user_components")
    public String device_user_components(){
        return "device_user_components";
    }

    @RequestMapping("/device_admi_rule")
    public String device_admi_rule(){
        return "device_admi_rule";
    }

    @RequestMapping("/device_user_rule")
    public String device_user_rule(){
        return "device_user_rule";
    }

}
