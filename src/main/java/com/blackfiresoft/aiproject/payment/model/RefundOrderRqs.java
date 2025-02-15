package com.blackfiresoft.aiproject.payment.model;


import lombok.Data;

@Data
public class RefundOrderRqs {

    private String outTradNo;
    private String outRefundNo;
    private Long refund;
    private Long total;
    private String currency;

}
