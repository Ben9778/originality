package com.blackfiresoft.aiproject.blackList.service;

import com.blackfiresoft.aiproject.blackList.mapper.BlackListMapper;
import com.blackfiresoft.aiproject.blackList.pojo.BlackListModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("BlackListService")
public class BlackListServiceImp implements BlackListService{

    @Resource
    private BlackListMapper blackListMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertIp(String ipaddress) {
        return blackListMapper.insertIp(ipaddress);
    }

    @Override
    public BlackListModel selectIpOne(String ipaddress) {
        return blackListMapper.selectIpOne(ipaddress);
    }

    @Override
    public List<BlackListModel> selectIpList() {
        return blackListMapper.selectIpList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteIp(List<Integer> ids) {
        return blackListMapper.deleteIp(ids);
    }
}
