package com.bupt.account.controller;

import com.bupt.account.VO.ResultVO;
import com.bupt.account.constant.CookieConstant;
import com.bupt.account.constant.RedisConstant;
import com.bupt.account.enums.DeviceReturn;
import com.bupt.account.utils.CookieUtil;
import com.bupt.account.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    /**
     * token 鉴权
     * @return
     */
    @GetMapping("/check")
    public ResultVO tokenAuthentication(@RequestParam("uid") String openid,@RequestParam("type") Integer type,@RequestParam("uuid") String uuid){
        String checksth = String.format("%s-%s-%s",openid, String.valueOf(type),uuid);
        log.info(checksth);
        String str = stringRedisTemplate.opsForValue().get(openid);
        if (!StringUtils.isEmpty(str) && str.equals(checksth)){
            return ResultVOUtil.success(DeviceReturn.OK);
        }else {
            return ResultVOUtil.error(DeviceReturn.FAIL);
        }
    }
}
