package com.blackfiresoft.aiproject.blackList.pojo;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListModel extends BaseModel {
    private long id;
    private String ipaddress;
    private Timestamp modifytime;
}

