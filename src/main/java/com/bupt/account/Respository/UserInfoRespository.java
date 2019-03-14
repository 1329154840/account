package com.bupt.account.Respository;

import com.bupt.account.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserInfoRespository extends JpaRepository<UserInfo,BigDecimal> {
    /**
     * 通过id查询用户信息
     * @param Id
     * @return
     */
    UserInfo  findUserInfoById(BigDecimal Id);

    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    UserInfo findUserInfoByOpenid(String openid);

    /**
     * 查询所有数据
     * @return
     */
    List<UserInfo> findAll();

}
