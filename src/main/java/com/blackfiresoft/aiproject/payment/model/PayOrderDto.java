package com.blackfiresoft.aiproject.payment.model;

import com.blackfiresoft.aiproject.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderDto extends BaseModel {

    private String openid;
    private String orderNo;//系统订单号
    private Timestamp createTime;//创建时间
    private BigDecimal amount;//总金额
    private String payWay;//支付方式
    private String payChannel;//支付渠道
    private String terminal;//终端
    private String descriptions;//描述
    private String orderStatus;//订单状态

}
