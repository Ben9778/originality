package com.blackfiresoft.aiproject.user.mapper;

import com.blackfiresoft.aiproject.user.pojo.TextTryCountModel;
import org.apache.ibatis.annotations.Param;

/**
 * 初始化试用次数统计表
 */
public interface TextTryCount {

     void insertCount(@Param("openid")String openid);//初始化新用户试用次数

     void updateGptCount(@Param("openid")String openid);//更新用户提问试用次数

     void updateTitleCount(@Param("openid")String openid);//更新用户标题生成试用次数

     TextTryCountModel selectCount(@Param("openid")String openid);//查询试用次数
}
