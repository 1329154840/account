package com.bupt.account.service.impl;

import com.bupt.account.Respository.RuleServiceRespository;
import com.bupt.account.dataobject.RuleInfo;
import com.bupt.account.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleServiceRespository ruleServiceRespository;
    @Override
    @Transactional
    public boolean create(RuleInfo ruleInfo) {
        ruleServiceRespository.save(ruleInfo);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteRuleByRuleId(String ruleId) {
        ruleServiceRespository.deleteByRuleId(ruleId);
        return true;
    }

    @Override
    @Transactional
    public boolean update(String ruleId, RuleInfo ruleInfo) {
//        RuleInfo oldRule =  ruleServiceRespository.findRuleInfoByRuleId(ruleId);
//        BeanUtils.copyProperties(ruleInfo,oldRule);
//        oldRule.setRuleId(ruleId);
        ruleServiceRespository.saveAndFlush(ruleInfo);
        return true;
    }

    @Override
    @Transactional
    public List<RuleInfo> findAllByUserId(String userId) {
        return ruleServiceRespository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public RuleInfo findRuleInfoByRuleId(String ruleId) {
        return ruleServiceRespository.findRuleInfoByRuleId(ruleId);
    }
}
