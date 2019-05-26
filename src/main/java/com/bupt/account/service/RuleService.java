package com.bupt.account.service;

import com.bupt.account.dataobject.RuleInfo;

import java.util.List;

public interface RuleService {
    /**
     * 创建规则
     * @param ruleInfo
     * @return
     */
    boolean create(RuleInfo ruleInfo);

    /**
     * 删除规则
     * @param ruleId
     * @return
     */
    boolean deleteRuleByRuleId(String ruleId);

    /**
     * 更新规则
     * @return
     */
    boolean update(String ruleId,RuleInfo ruleInfo);

    /**
     * 通过用户id查询所有规则
     * @param userId
     * @return
     */
    List<RuleInfo> findAllByUserId(String userId);

    /**
     * 通过规则id查询
     * @param ruleId
     * @return
     */
    RuleInfo findRuleInfoByRuleId(String ruleId);

    /**
     * 查询所有规则
     * @return
     */
    List<RuleInfo> findAll();

}
