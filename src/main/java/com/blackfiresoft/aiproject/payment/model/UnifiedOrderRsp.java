package com.blackfiresoft.aiproject.payment.model;

import lombok.Data;

/**
 * 回调通知接收参数
 */
@Data
public class UnifiedOrderRsp {

    private String id;
    private String create_time;
    private String event_type;
    private String resource_type;
    private String summary;
    private TransactionResource resource;
}
