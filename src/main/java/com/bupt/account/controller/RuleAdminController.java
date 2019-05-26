package com.bupt.account.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bupt.account.dataobject.RuleInfo;
import com.bupt.account.dto.RuleDto;
import com.bupt.account.enums.BadResult;
import com.bupt.account.service.RuleService;
import com.bupt.account.utils.CookieUtil;
import com.bupt.account.utils.JsonResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/rule")
@Slf4j
public class RuleAdminController {
    @Autowired
    RuleService ruleService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;
    /**
     * 查询所有设备
     * @return
     */
    @PostMapping("/findAll")
    public String ruleFindAll(){
        return JsonResponseUtil.ok(ruleService.findAll());
    }

    /**
     * 创建新的rule
     * @param userId 用户id
     * @param deviceId 设备id
     * @param op 操作
     * @param date 操作发生的时间
     * @return
     */
    @PostMapping("/create")
    public String ruleCreate(@RequestParam("userId")String userId,
                             @RequestParam("deviceId")String deviceId,
                             @RequestParam("op")String op,
                             @RequestParam("date")String date,
                             HttpServletRequest request){
        //1.在本地创建rule
        RuleInfo ruleInfo = new RuleInfo();
        ruleInfo.setUserId(userId);
        ruleInfo.setDeviceId(deviceId);
        ruleInfo.setOp(op);
        log.info("createDate={}",date.replace('T',' '));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d = format.parse(date.replace('T',' '));
            log.info("getTime={}",d.getTime());
            java.sql.Timestamp time1 = new java.sql.Timestamp(d.getTime());
            ruleInfo.setDate(time1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("createDate={}",ruleInfo.getDate());
        ruleInfo.setRuleId(UUID.randomUUID().toString().replace("-",""));
        ruleService.create(ruleInfo);
        return JsonResponseUtil.ok(ruleInfo);
    }


    /**
     * 把规则上传到设备模块
     * @param ruleId 规则id
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public String ruleUpload(@RequestParam("ruleId")String ruleId,
                             HttpServletRequest request){
        RuleInfo ruleInfo =  ruleService.findRuleInfoByRuleId(ruleId);

        Cookie cookie = CookieUtil.get(request,"token");
        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        RuleDto ruleDto = new RuleDto();
        ruleDto.setId(ruleInfo.getDeviceId());
        ruleDto.setOp(ruleInfo.getOp());
        ruleDto.setDate(ruleInfo.getDate().getTime());
        log.info("time={}",ruleDto.getDate());
        paramMap.add("rule", JSONObject.toJSONString(ruleDto));

        HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(paramMap, headers);
        ResponseEntity<String> reponse = restTemplate.exchange("http://DEVICES-ACCESS/user/upload", HttpMethod.POST,entity,String.class);
        log.info("response={}",reponse);
        if(reponse.getStatusCode()==HttpStatus.OK){
            ruleInfo.setStatus("UP");
            ruleService.update(ruleId,ruleInfo);
        }
//        return reponse;


        JSONArray rule = new JSONArray();
        JSONObject singleRule = new JSONObject();
        singleRule.put("id",ruleInfo.getDeviceId());
        singleRule.put("op",ruleInfo.getOp());
        singleRule.put("date", ruleInfo.getDate().getTime());
        singleRule.put("type","1");
        rule.add(singleRule);
        JSONObject result = new JSONObject();
        result.put("rule",rule);
        String response = restTemplate.getForObject("http://devices-access/user/upload?rule={rule}",String.class,JSONObject.toJSONString(result));
        log.info("{}",response);
//        if(response.getStatusCode()==HttpStatus.OK){
//            ruleInfo.setStatus("UP");
//            ruleService.update(ruleId,ruleInfo);
//        }
        return response;
    }

    /**
     *删除单个定时任务
     * @param ruleId 规则id
     * @param request
     * @return
     */
    @PostMapping("/download")
    public ResponseEntity<String> ruleDownload(@RequestParam("ruleId")String ruleId,
                                               HttpServletRequest request){
        RuleInfo ruleInfo =  ruleService.findRuleInfoByRuleId(ruleId);

        Cookie cookie = CookieUtil.get(request,"token");
        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        RuleDto ruleDto = new RuleDto();
        ruleDto.setId(ruleInfo.getDeviceId());
        ruleDto.setOp(ruleInfo.getOp());
        ruleDto.setDate(ruleInfo.getDate().getTime());
        log.info("time={}",ruleDto.getDate());
        paramMap.add("rule", JSONObject.toJSONString(ruleDto));

        HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(paramMap, headers);
        ResponseEntity<String> reponse = restTemplate.exchange("http://DEVICES-ACCESS/user/removeJob", HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        if(reponse.getStatusCode()==HttpStatus.OK){
            ruleInfo.setStatus("DOWN");
            ruleService.update(ruleId,ruleInfo);
        }
        return reponse;
    }

    /**
     * 按照ruleid删除rule
     * @param ruleId 规则id
     * @return
     */
    @PostMapping("deleteRuleByRuleId")
    public String ruledelete(@RequestParam("ruleId")String ruleId){
        log.info("ruleId={}",ruleId);
        RuleInfo ruleInfo = ruleService.findRuleInfoByRuleId(ruleId);
        if(ruleId.isEmpty()){
            return JsonResponseUtil.badResult(BadResult.Rule_Is_Null.getCode(), BadResult.Rule_Is_Null.getmsg());
        }
        ruleService.deleteRuleByRuleId(ruleId);
        return JsonResponseUtil.ok();
    }

    /**
     *更新规则
     * @param ruleId 规则id
     * @param userId 用户id
     * @param deviceId 设备id
     * @param op    操作
     * @param date  操作发生的日期
     * @return
     */
    @PostMapping("update")
    public String ruleupdate(@RequestParam("ruleId") String ruleId,
                             @RequestParam("userId") String userId,
                             @RequestParam("deviceId")String deviceId,
                             @RequestParam("op")String op,
                             @RequestParam("date")String date){
        RuleInfo ruleInfo = ruleService.findRuleInfoByRuleId(ruleId);
        if(ruleId.isEmpty()){
            return JsonResponseUtil.badResult(BadResult.Rule_Is_Null.getCode(), BadResult.Rule_Is_Null.getmsg());
        }
        RuleInfo rule = new RuleInfo();
        rule.setUserId(userId);
        rule.setDeviceId(deviceId);
        rule.setOp(op);
        rule.setRuleId(ruleId);
        rule.setId(ruleInfo.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d = format.parse(date.replace('T',' '));
            java.sql.Timestamp time1 = new java.sql.Timestamp(d.getTime());
            rule.setDate(time1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ruleService.update(ruleId,rule);
        return JsonResponseUtil.ok();
    }

    @RequestMapping("loadBalance")
    public String loadBalance(){
        return JsonResponseUtil.ok(request.getLocalPort());
    }
}

