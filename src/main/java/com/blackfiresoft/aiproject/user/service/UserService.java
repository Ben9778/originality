package com.blackfiresoft.aiproject.user.service;


import com.blackfiresoft.aiproject.user.pojo.UserModel;
import com.blackfiresoft.aiproject.user.pojo.VipModel;

import java.sql.Timestamp;

public interface UserService {
    int register(String openid, String username);
    UserModel checkUser(String openid);
    VipModel getUserVipInfo(String openid);
    void insertVip(String openid, Timestamp expireTime);
    void updateVipExpireTime(String openid,Timestamp expireTime);
    void deleteVip(String openid);
}
