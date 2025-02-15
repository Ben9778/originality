package com.blackfiresoft.aiproject.payment.model;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 支付下单参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedOrderRqs extends BaseModel {

    private Integer total;//金额
    private String outTradeNo;//商户订单号
    private String description;//商品描述
    private String openid;//用户openid
    private String attach;//自定义参数
}
