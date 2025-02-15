package com.blackfiresoft.aiproject.feedback.service;

import com.blackfiresoft.aiproject.feedback.mapper.FeedInfo;
import com.blackfiresoft.aiproject.feedback.pojo.FeedInfoModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("FeedInfoService")
@Transactional(rollbackFor = RuntimeException.class)
public class FeedInfoServiceImp implements FeedInfoService{

    @Resource
    private FeedInfo feedInfo;
    @Override
    public int insertInfo(FeedInfoModel feedInfoMode) {
        return feedInfo.insertInfo(feedInfoMode);
    }
}
