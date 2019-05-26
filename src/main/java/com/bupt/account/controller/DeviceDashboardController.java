package com.bupt.account.controller;

import com.bupt.account.Respository.UserInfoRespository;
import com.bupt.account.dataobject.UserInfo;
import com.bupt.account.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现页面跳转
 */
@Controller
public class DeviceDashboardController {
    @Autowired
    private UserInfoRespository userInfoRespository;

    @RequestMapping("/device_admi_dashboard")
    public String device_admi_dashboard(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_admi_dashboard";
    }

    @RequestMapping("/device_user_dashboard")
    public String device_user_dashboard(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_user_dashboard";
    }

    @RequestMapping("/device_admi_components")
    public String device_admi_components(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_admi_components";
    }

    @RequestMapping("/device_user_components")
    public String device_user_components(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_user_components";
    }

    @RequestMapping("/device_admi_rule")
    public String device_admi_rule(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_admi_rule";
    }

    @RequestMapping("/device_user_rule")
    public String device_user_rule(HttpServletRequest request, Model model){
        addUserName(request,model);
        return "device_user_rule";
    }

    private void addUserName(HttpServletRequest request, Model model){
        String token = CookieUtil.get(request,"token").getValue();
        String[] tokenArray = token.split("-");
        UserInfo userInfo= userInfoRespository.findUserInfoByOpenid(tokenArray[0]);
        Map<String, String> user = new HashMap<>(2);
        user.put("username",  tokenArray[0]);
        user.put("email",  userInfo.getEmail());
        model.addAttribute("user",user);
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session){
        Cookie[] cookies = request.getCookies();
        // 迭代查找并清除Cookie
        for (Cookie cookie: cookies) {
            //将cookie.setMaxAge(0)表示删除cookie.
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        // 销毁用户session
        session.invalidate();
        return "login";
    }

}
