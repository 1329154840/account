package com.bupt.account.controller;

import com.bupt.account.VO.ResultVO;
import com.bupt.account.constant.CookieConstant;
import com.bupt.account.enums.LoginReturn;
import com.bupt.account.service.UserService;
import com.bupt.account.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 登录校验，成功写入将token，写入redis
     * @param openid 用户id
     * @param password 用户密码
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestParam("name") String openid,
                                  @RequestParam("password") String password,
                                    @RequestParam("type") Integer type ,
                                    HttpServletResponse response){
        //TODO：对密码进行加密
        log.info("login_openid:{}",openid);
        log.info("login_password:{}",password);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        LoginReturn loginReturn = userService.loginAuthentication(openid,password,type);
        if (loginReturn.getCode().equals(LoginReturn.OK.getCode())){
            Integer expire = CookieConstant.expire;
            String str =  String.format("%s-%s-%s",openid,String.valueOf(type),uuid);
            stringRedisTemplate.opsForValue().set(openid,
                    str,
                    expire,
                    TimeUnit.SECONDS);
            CookieUtil.set(response,CookieConstant.TOKEN,str,CookieConstant.expire);
        }
        ResultVO resultLoginVO = new ResultVO();
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
