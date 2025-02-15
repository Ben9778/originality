package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.constant.OrderStatus;
import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.payment.model.RefundOrderRqs;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import com.blackfiresoft.aiproject.payment.util.JsapiServiceUtil;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 退款
 */
@RestController
@Slf4j
@RequestMapping("/refund")
public class RefundController {
    @Resource
    private PayOrderService payOrderService;
    @PostMapping("/api")
    public Result refund(@RequestBody RefundOrderRqs rqs) {
        log.info("------收到订单号{}的退款请求------",rqs.getOutTradNo());
        JsapiServiceUtil jsapiServiceUtil = new JsapiServiceUtil();
        Refund refund=jsapiServiceUtil.create(rqs);
        if(refund!=null) {
            payOrderService.updateOrderStatus(OrderStatus.REFUND, rqs.getOutTradNo());
            log.info("订单号{}转入退款状态成功",rqs.getOutTradNo());
        }else {
            log.error("订单{}退款失败",rqs.getOutTradNo());
        }

        return ResultCode.success();
    }
}
