package com.bupt.account.service;

import com.bupt.account.dataobject.UserInfo;
import com.bupt.account.enums.LoginReturn;
import com.bupt.account.enums.RegisterReturn;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Description:  ---——require需求|ask问题|jira
 * Design :  ----the  design about train of thought 设计思路
 * User: yezuoyao
 * Date: 2019-03-01
 * Time: 15:52
 * Email:yezuoyao@huli.com
 *
 * @author yezuoyao
 * @since 1.0-SNAPSHOT
 */

public interface UserService {

   /**
    *登陆认证
    */
   LoginReturn loginAuthentication(String openid, String password,String uuid);

   /**
    * 创建用户
    * @param user
    */
   RegisterReturn create(UserInfo user);

   /**
    * 更新用户
    * @param user
    */
   void update(UserInfo user);

   /**
    * 删除用户
    * @param id
    */
   void delete(Long id);

   /**
    * 获取所有用户
    * @return List<User>
    */
   List<UserInfo> getAll();

   /**
    * 保存数据
    */
   void save();
}
