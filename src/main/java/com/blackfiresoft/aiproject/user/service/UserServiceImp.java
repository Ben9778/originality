package com.blackfiresoft.aiproject.user.service;

import com.blackfiresoft.aiproject.user.mapper.User;
import com.blackfiresoft.aiproject.user.pojo.UserModel;
import com.blackfiresoft.aiproject.user.pojo.VipModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

@Service("UserService")
public class UserServiceImp implements UserService {

    @Resource
    private User user;
    @Transactional(rollbackFor=RuntimeException.class)
    @Override
    public int register(String openid,String username) {

        return user.register(openid,username);
    }
    @Override
    public UserModel checkUser(String openid) {

        return user.checkUser(openid);
    }
    @Override
    public VipModel getUserVipInfo(String openid){
        return user.getUserVipInfo(openid);
    }

    @Transactional(rollbackFor=RuntimeException.class)
    @Override
    public void insertVip(String openid, Timestamp expireTime){
         user.insertVip(openid,expireTime);
    }

    @Transactional(rollbackFor=RuntimeException.class)
    @Override
    public void updateVipExpireTime(String openid, Timestamp expireTime){
        user.updateVipExpireTime(openid,expireTime);
    }

    @Transactional(rollbackFor=RuntimeException.class)
    @Override
    public void deleteVip(String openid){
        user.deleteVip(openid);
    }
}
