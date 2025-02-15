package com.blackfiresoft.aiproject.feedback.pojo;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedInfoModel extends BaseModel {
    private String openid;
    private String message;
    private String telNumber;
}
