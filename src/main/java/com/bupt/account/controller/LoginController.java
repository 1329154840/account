package com.bupt.account.controller;

import com.bupt.account.VO.ResultLoginVO;
import com.bupt.account.VO.ResultRegisterVO;
import com.bupt.account.VO.ResultVO;
import com.bupt.account.constant.CookieConstant;
import com.bupt.account.dataobject.UserInfo;
import com.bupt.account.enums.LoginReturn;
import com.bupt.account.enums.RegisterReturn;
import com.bupt.account.enums.UserStatus;
import com.bupt.account.service.UserService;
import com.bupt.account.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/a")
    public String test(){

        return "login";
    }

    @PostMapping("/login")
    public ResultRegisterVO login(@RequestParam("name") String name,
                                  @RequestParam("password") String password, HttpServletResponse response){
        log.info("login_name:{}",name);
        log.info("login_password:{}",password);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        LoginReturn loginReturn = userService.loginAuthentication(name,password,uuid);
        if (loginReturn.getCode()==LoginReturn.OK.getCode()){
            Cookie cookie = new Cookie("token",uuid);
            cookie.setPath("/");
            cookie.setMaxAge(CookieConstant.expire);
            response.addCookie(cookie);
        }
        ResultRegisterVO resultLoginVO = new ResultRegisterVO();
        resultLoginVO.setCode(loginReturn.getCode());
        resultLoginVO.setMsg(loginReturn.getMessage());
        return resultLoginVO;
    }

}
