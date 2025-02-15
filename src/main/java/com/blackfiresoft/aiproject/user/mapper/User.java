package com.blackfiresoft.aiproject.user.mapper;

import com.blackfiresoft.aiproject.user.pojo.UserModel;
import com.blackfiresoft.aiproject.user.pojo.VipModel;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;


/**
 * 注册新用户
 */
public interface User {

     int register(@Param("openid")String openid,@Param("username")String username);

     UserModel checkUser(@Param("openid")String openid);

     VipModel getUserVipInfo(@Param("openid")String openid);

     void insertVip(@Param("openid")String openid,@Param("expireTime") Timestamp expireTime);

     void updateVipExpireTime(@Param("openid")String openid,@Param("expireTime")Timestamp expireTime);

     void deleteVip(@Param("openid")String openid);
}
