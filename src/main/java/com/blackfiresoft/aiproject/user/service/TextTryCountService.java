package com.blackfiresoft.aiproject.user.service;


import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;

public interface TextTryCountService {

    void insertCount(String openid);//初始化新用户试用次数

    void updateGptCount(String openid);//更新用户提问试用次数

    void updateTitleCount(String openid);//更新用户标题生成试用次数

    TextTryCountModel selectCount(String openid);//查询试用次数

}
