package com.bupt.account.controller;

import com.bupt.account.VO.ResultRegisterVO;
import com.bupt.account.dataobject.UserInfo;
import com.bupt.account.enums.RegisterReturn;
import com.bupt.account.enums.UserStatus;
import com.bupt.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @return
     */
    @PostMapping("/register")
    public ResultRegisterVO register(@RequestParam("name") String name,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password){
        log.info("register_name:{}",name);
        log.info("register_email:{}",email);
        log.info("register_password:{}",password);
        //创建userinfo实例
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(name);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setStatus(UserStatus.Offline.getCode());
        RegisterReturn registerReturn = userService.create(userInfo);
        ResultRegisterVO resultRegisterVO = new ResultRegisterVO();
        resultRegisterVO.setCode(registerReturn.getCode());
        resultRegisterVO.setMsg(registerReturn.getMessage());
        return resultRegisterVO;
    }
}
