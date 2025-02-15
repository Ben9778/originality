package com.blackfiresoft.aiproject.payment.service;

import com.blackfiresoft.aiproject.payment.model.PayOrderDto;
import com.blackfiresoft.aiproject.payment.model.PayOrderVo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface PayOrderService {
    int insertOrder(String openid, String orderNo, Timestamp createTime, BigDecimal amount, String payWay, String payChannel,
                    String terminal, String descriptions, String orderStatus);

    void updateOrderStatus(String orderStatus, String orderNo);

    PayOrderDto selectOrderByNo(String orderNo);

    List<PayOrderDto> selectAllOrder();

    List<PayOrderDto> selectOrderByDate(Timestamp startDate, Timestamp endDate);

    List<PayOrderDto> selectOrderByStatus(String orderStatus);

    String selectOpenIdByNo(String orderNo);

    List<PayOrderVo> selectOrderByOpenid(String openid);

}
