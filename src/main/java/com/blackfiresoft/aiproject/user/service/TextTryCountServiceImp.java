package com.blackfiresoft.aiproject.user.service;

import com.blackfiresoft.aiproject.user.mapper.TextTryCount;
import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("TextTryCountService")
public class TextTryCountServiceImp implements TextTryCountService {

    @Resource
    private TextTryCount textTryCount;


    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public void insertCount(String openid) {
        textTryCount.insertCount(openid);
    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public void updateGptCount(String openid) {
        textTryCount.updateGptCount(openid);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateTitleCount(String openid) {
        textTryCount.updateTitleCount(openid);
    }

    @Override
    public TextTryCountModel selectCount(String openid) {

        return textTryCount.selectCount(openid);
    }
}
