package com.blackfiresoft.aiproject.user.pojo;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextTryCountModel extends BaseModel {

    private int gptCount;
    private int titleCount;
}
