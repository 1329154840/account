package com.bupt.account.Respository;

import com.bupt.account.dataobject.RuleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RuleServiceRespository extends JpaRepository<RuleInfo, BigDecimal> {
    /**
     * 通过设备id查询规则
     * @param deviceId
     * @return
     */
    List<RuleInfo> findAllByDeviceId(String deviceId);

    /**
     * 通过userid查询规则
     * @param userId
     * @return
     */
    List<RuleInfo> findAllByUserId(String userId);

    /**
     * 通过规则id查询
     * @param RuleId
     * @return
     */
    RuleInfo findRuleInfoByRuleId(String RuleId);

    /**
     * 通过规则id删除
     * @param RuleId
     */
    void deleteByRuleId(String RuleId);

}
