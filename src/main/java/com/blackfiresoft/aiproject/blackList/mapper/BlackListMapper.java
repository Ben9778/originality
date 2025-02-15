package com.blackfiresoft.aiproject.blackList.mapper;

import com.blackfiresoft.aiproject.blackList.pojo.BlackListModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlackListMapper {
     int insertIp(@Param("ipaddress")String ipaddress);
     BlackListModel selectIpOne(@Param("ipaddress")String ipaddress);
     List<BlackListModel> selectIpList();
     int deleteIp(@Param("ids")List<Integer>ids);
}
