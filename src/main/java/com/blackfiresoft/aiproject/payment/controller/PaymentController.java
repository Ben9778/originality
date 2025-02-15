package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.constant.JsapiConfig;
import com.blackfiresoft.aiproject.common.constant.OrderStatus;
import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.common.result.ResultEnum;
import com.blackfiresoft.aiproject.payment.model.UnifiedOrderRqs;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import com.blackfiresoft.aiproject.payment.util.JsapiServiceUtil;
import com.blackfiresoft.aiproject.utils.OutTradNoUtil;
import com.blackfiresoft.aiproject.utils.SignUtil;
import com.blackfiresoft.aiproject.utils.TimeUtil;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 支付接口类
 */
@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Resource
    private PayOrderService payOrderService;

    /**
     * 统一创建订单
     */
    @PostMapping("/unifiedOrder")
    public Result createOrder(@RequestParam("token") String token, @RequestParam(value = "duration", required = false) int duration, @RequestParam("total") int total,
                              @RequestParam("payWay") String payWay, @RequestParam("payChannel") String payChannel,
                              @RequestParam("terminal") String terminal, @RequestParam("descriptions") String descriptions
                              ) {
        log.info("------开始接收到openid:{}的用户下单请求------", token);
        UnifiedOrderRqs rqs = new UnifiedOrderRqs();
        JsapiServiceUtil jsapiServiceUtil = new JsapiServiceUtil();
        BigDecimal amount = new BigDecimal(total);//金额转成BigDecimal
        String payOrderNo = OutTradNoUtil.generateOutTradNo();//生成系统唯一订单号
        //订单入库
        int result = payOrderService.insertOrder(token, payOrderNo, TimeUtil.getCurrentTime(), amount, payWay, payChannel,
                terminal, descriptions, OrderStatus.WAIT);
        //支付入参
        rqs.setDescription(descriptions);
        rqs.setOpenid(token);
        rqs.setOutTradeNo(payOrderNo);
        rqs.setTotal(amount.intValue());
        rqs.setAttach(String.valueOf(duration));
        log.info("------开始支付下单------");
        //支付下单
        PrepayResponse prepayResponse = jsapiServiceUtil.prepay(rqs);
        if (result > 0) {
            log.info("openid:{}的用户生成新订单号为:{},成功入库", token, payOrderNo);
            String appId = JsapiConfig.appid;
            String getTime = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
            String prepayId = "prepay_id=" + prepayResponse.getPrepayId();//package
            String nonceStr = UUID.randomUUID().toString().trim().replaceAll("-", "");//随机字符串nonceStr
            //小程序支付参数签名
            String paySign = SignUtil.getSignWithBase64(appId, getTime, nonceStr, prepayId);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("timeStamp", getTime);
            resultMap.put("nonceStr", nonceStr);
            resultMap.put("prepayId", prepayId);
            resultMap.put("payOrderNo", payOrderNo);
            resultMap.put("paySign", paySign);//签名后的值
            return ResultCode.success(resultMap);
        } else {
            log.error("openid:{}的用户生成新订单号为:{},入库失败", token, payOrderNo);
            return ResultCode.error(ResultEnum.INSERT_ERROR);
        }

    }
}
