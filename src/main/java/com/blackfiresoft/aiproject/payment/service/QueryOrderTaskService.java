package com.blackfiresoft.aiproject.payment.service;

import com.blackfiresoft.aiproject.common.constant.OrderStatus;
import com.blackfiresoft.aiproject.payment.model.PayOrderDto;
import com.blackfiresoft.aiproject.payment.util.JsapiServiceUtil;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时查单
 */
@Slf4j
@Component
public class QueryOrderTaskService {

    @Resource
    private PayOrderService payOrderService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void queryTask(){
        log.info("------开始执行查询未支付订单任务------");
        List<PayOrderDto>orderList=payOrderService.selectOrderByStatus(OrderStatus.WAIT);
        log.info("共有{}个订单",orderList.size());
        for (PayOrderDto list: orderList) {
            Transaction transaction=new JsapiServiceUtil().queryOrderByOutTradeNo(list.getOrderNo());
            if(Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState())){
                payOrderService.updateOrderStatus(OrderStatus.SUCCESS, list.getOrderNo());
                log.info("更新订单:{}状态为已支付成功",list.getOrderNo());
            }else if(Transaction.TradeStateEnum.CLOSED.equals(transaction.getTradeState())){
                payOrderService.updateOrderStatus(OrderStatus.CLOSE,list.getOrderNo());
                log.info("更新订单:{}状态为已关闭",list.getOrderNo());
            }
        }
    }

}
