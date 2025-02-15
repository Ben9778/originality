package com.blackfiresoft.aiproject.blackList.pojo;

import lombok.Data;

@Data
public class UserIp{

    private String ip; //用户ip

    private Integer count; //访问次数

    private Long ft; //第一次访问时间

}
