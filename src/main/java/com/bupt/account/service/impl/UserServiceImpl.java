package com.bupt.account.service.impl;

import com.bupt.account.Respository.UserInfoRespository;
import com.bupt.account.constant.CookieConstant;
import com.bupt.account.constant.RedisConstant;
import com.bupt.account.dataobject.UserInfo;
import com.bupt.account.enums.LoginReturn;
import com.bupt.account.enums.RegisterReturn;
import com.bupt.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Description:  ---——require需求|ask问题|jira
 * Design :  ----the  design about train of thought 设计思路
 * User: yezuoyao
 * Date: 2019-03-01
 * Time: 15:47
 * Email:yezuoyao@huli.com
 *
 * @author yezuoyao
 * @since 1.0-SNAPSHOT
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRespository userInfoRespository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public LoginReturn loginAuthentication(String openid, String password,Integer type) {
        UserInfo userInfo = userInfoRespository.findUserInfoByOpenid(openid);
        if(userInfo!=null && userInfo.getPassword().equals(password) && userInfo.getType()==type){ //账号密码权限正确
            return LoginReturn.OK;
        }else { //登陆失败
            return LoginReturn.FAIL;
        }
    }

    @Override
    @Transactional
    public RegisterReturn create(UserInfo user) {
        //查询openid是否重复
        UserInfo userInfo = userInfoRespository.findUserInfoByOpenid(user.getOpenid());
        if(userInfo==null) {
            userInfoRespository.save(user);
            return RegisterReturn.OK;
        }else {
            return RegisterReturn.REPEAT;
        }
    }

    @Override
    public void update(UserInfo user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserInfo> getAll() {
        return null;
    }

    @Override
    public void save() {

    }

}
