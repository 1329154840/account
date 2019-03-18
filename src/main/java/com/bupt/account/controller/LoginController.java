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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String test(){
        return "login";
    }

    /**
     * 登录校验，成功写入将token，写入redis
     * @param name
     * @param password
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultRegisterVO login(@RequestParam("name") String name,
                                  @RequestParam("password") String password, HttpServletResponse response){
        log.info("login_name:{}",name);
        log.info("login_password:{}",password);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String token = name + "-" + uuid;
        LoginReturn loginReturn = userService.loginAuthentication(name,password,uuid);
        if (loginReturn.getCode().equals(LoginReturn.OK.getCode())){

            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            cookie.setMaxAge(CookieConstant.expire);
            response.addCookie(cookie);
        }
        ResultRegisterVO resultLoginVO = new ResultRegisterVO();
        resultLoginVO.setCode(loginReturn.getCode());
        resultLoginVO.setMsg(loginReturn.getMessage());
        return resultLoginVO;
    }

    /**
     * home例子
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/home")
    public String home(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String[] buffer = cookie.getValue().split("-");
                for (String e: buffer){
                    log.info("{}",e);
                }
                model.addAttribute("uid",buffer[0]);
                return "home";
            }
        }
        model.addAttribute("uid","0");
        return "home";
    }

}
