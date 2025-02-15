package com.blackfiresoft.aiproject.blackList.service;

import com.blackfiresoft.aiproject.blackList.pojo.BlackListModel;

import java.util.List;

public interface BlackListService {
    int insertIp(String ipaddress);
    BlackListModel selectIpOne(String ipaddress);
    List<BlackListModel> selectIpList();
    int deleteIp(List<Integer>ids);
}
