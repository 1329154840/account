package com.bupt.account.controller;

import com.bupt.account.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.netflix.ribbon.proxy.annotation.Http.HttpMethod.GET;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/findAll")
    public ResponseEntity<String> findAll(HttpServletRequest request){
        Cookie cookie = CookieUtil.get(request,"token");

        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );

        HttpEntity entity = new HttpEntity(null, headers);
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange("http://DEVICES-ACCESS/admin/findAll",HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        return reponse;
    }

    @GetMapping("/insert")
    public ResponseEntity<String> insert(@RequestParam("name") String name,
                                         @RequestParam("model") String model,
                                         @RequestParam(value = "nickname") String nickname,
                                         @RequestParam(value = "status") String status,
                                         HttpServletRequest request){
        Cookie cookie = CookieUtil.get(request,"token");

        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );

        HttpEntity entity = new HttpEntity(null, headers);
        String url = String.format("http://DEVICES-ACCESS/admin/insert?name=%s&model=%s&nickname=%s&status=%s",name,model,nickname,status);
        log.info(url);
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        return reponse;
    }

    @GetMapping("/update")
    public ResponseEntity<String> update(@RequestParam("id") String id,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "model", required = false) String model,
                                         @RequestParam(value = "nickname", required = false) String nickname,
                                         @RequestParam(value = "status", required = false) String status,
                                         @RequestParam(value = "openId", required = false) String openId,
                                         HttpServletRequest request){
        Cookie cookie = CookieUtil.get(request,"token");

        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );

        HttpEntity entity = new HttpEntity(null, headers);
        String url = String.format("http://DEVICES-ACCESS/admin/updateById?id=%s&name=%s&model=%s&nickname=%s&status=%s&openId=%s",id,name,model,nickname,status,openId);
        log.info(url);
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        return reponse;
    }

    @GetMapping("/deleteById")
    public ResponseEntity<String> deleteById(@RequestParam("id") String id,
                                         HttpServletRequest request){
        Cookie cookie = CookieUtil.get(request,"token");

        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );

        HttpEntity entity = new HttpEntity(null, headers);
        String url = String.format("http://DEVICES-ACCESS/admin/deleteById?id=%s",id);
        log.info(url);
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        return reponse;
    }

    @GetMapping("/findByOpenId")
    public ResponseEntity<String> findByOpenId(@RequestParam("id") String id,
                                             HttpServletRequest request){
        Cookie cookie = CookieUtil.get(request,"token");

        HttpHeaders headers = new HttpHeaders();
        List<String> mycookies = new ArrayList<>();
        mycookies.add("token=" + cookie.getValue());
        headers.put(HttpHeaders.COOKIE, mycookies );

        HttpEntity entity = new HttpEntity(null, headers);
        String url = String.format("http://DEVICES-ACCESS/admin/findByOpenId?openId=%s",id);
        log.info(url);
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = restTemplate.exchange(url,HttpMethod.GET,entity,String.class);
        log.info("response={}",reponse);
        return reponse;
    }
}
