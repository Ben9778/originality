package com.blackfiresoft.aiproject.user.pojo;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipModel extends BaseModel {

    private long vid;
    private String openid;
    private Timestamp expireTime;//会员过期时间
    private Timestamp create_time;
    private Short flag;
}
