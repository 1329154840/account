package com.bupt.account.controller;

import com.bupt.account.constant.CookieConstant;
import com.bupt.account.dataobject.RuleInfo;
import com.bupt.account.enums.BadResult;
import com.bupt.account.service.RuleService;
import com.bupt.account.utils.CookieUtil;
import com.bupt.account.utils.JsonResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/rule/user")
@Slf4j
public class RuleUserController {

    @Autowired
    RuleService ruleService;

    /**
     * 通过userid查询所有设备
     * @param userId
     * @return
     */
    @PostMapping("/findAllByUserId")
    public String ruleFindAllByUserId(@RequestParam("userId") String userId){
        //1.完成token验证
//        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
//        String str[] =  cookie.getValue().split("-");
        return JsonResponseUtil.ok(ruleService.findAllByUserId(userId));
    }

    /**
     * 创建新的rule
     * @param userId
     * @param deviceId
     * @param op
     * @param date
     * @return
     */
    @PostMapping("/create")
    public String ruleCreate(@RequestParam("userId")String userId,
                             @RequestParam("deviceId")String deviceId,
                             @RequestParam("op")String op,
                             @RequestParam("data")Date date){
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setUserId(userId);
        ruleInfo.setDeviceId(deviceId);
        ruleInfo.setOp(op);
        ruleInfo.setRuleId(UUID.randomUUID().toString().replace("-",""));
        ruleService.create(ruleInfo);
        return JsonResponseUtil.ok(ruleInfo);
    }

    /**
     * 按照ruleid删除rule
     * @param ruleId
     * @return
     */
    @PostMapping("deleteRuleByRuleId")
    public String ruledelete(@RequestParam("ruleId")String ruleId){
        RuleInfo ruleInfo = ruleService.findRuleInfoByRuleId(ruleId);
        if(ruleId.isEmpty()){
            return JsonResponseUtil.badResult(BadResult.Rule_Is_Null.getCode(), BadResult.Rule_Is_Null.getmsg());
        }
        ruleService.deleteRuleByRuleId(ruleId);
        return JsonResponseUtil.ok();
    }

    /**
     *更新规则
     * @param ruleId
     * @param userId
     * @param deviceId
     * @param op
     * @param date
     * @return
     */
    @PostMapping("update")
    public String ruleupdate(@RequestParam("ruleId") String ruleId,
                             @RequestParam("userId") String userId,
                             @RequestParam("deviceId")String deviceId,
                             @RequestParam("op")String op,
                             @RequestParam("data")Date date){
        RuleInfo ruleInfo = ruleService.findRuleInfoByRuleId(ruleId);
        if(ruleId.isEmpty()){
            return JsonResponseUtil.badResult(BadResult.Rule_Is_Null.getCode(), BadResult.Rule_Is_Null.getmsg());
        }
        RuleInfo rule = new RuleInfo();
        rule.setUserId(userId);
        rule.setDeviceId(deviceId);
        rule.setOp(op);
        rule.setRuleId(ruleId);
        ruleService.update(ruleId,rule);
        return JsonResponseUtil.ok();
    }
}
