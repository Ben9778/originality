package com.blackfiresoft.aiproject.payment.util;

import com.blackfiresoft.aiproject.common.constant.JsapiConfig;
import com.blackfiresoft.aiproject.payment.model.RefundOrderRqs;
import com.blackfiresoft.aiproject.payment.model.UnifiedOrderRqs;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;

public class JsapiServiceUtil {

    public static JsapiService service;
    public static RefundService refundService;

    // 初始化商户配置
    public JsapiServiceUtil() {
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(JsapiConfig.merchantId)
                //从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
                .privateKeyFromPath(JsapiConfig.privateKeyPath)
                .merchantSerialNumber(JsapiConfig.merchantSerialNumber)
                .apiV3Key(JsapiConfig.apiV3Key)
                .build();
        // 初始化服务
        service = new JsapiService.Builder().config(config).build();
        refundService=new RefundService.Builder().config(config).build();
    }

    /**
     * 关闭订单
     */
    public void closeOrder(String outTradNo) {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(JsapiConfig.merchantId);
        request.setOutTradeNo(outTradNo);
        service.closeOrder(request);
    }

    /**
     * JSAPI支付下单
     */
    public PrepayResponse prepay(UnifiedOrderRqs rqs) {
        PrepayRequest request = new PrepayRequest();
        Payer payer=new Payer();
        Amount amount=new Amount();
        payer.setOpenid(rqs.getOpenid());
        amount.setTotal(rqs.getTotal());
        request.setAppid(JsapiConfig.appid);
        request.setMchid(JsapiConfig.merchantId);
        request.setNotifyUrl(JsapiConfig.notifyUrl);
        request.setOutTradeNo(rqs.getOutTradeNo());
        request.setDescription(rqs.getDescription());
        request.setAmount(amount);
        request.setPayer(payer);
        request.setAttach(rqs.getAttach());
        // 调用接口
        return service.prepay(request);
    }

    /**
     * 微信支付订单号查询订单
     */
    public Transaction queryOrderById() {

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        // 调用接口
        return service.queryOrderById(request);
    }

    /**
     * 商户订单号查询订单
     */
    public Transaction queryOrderByOutTradeNo(String outTradNo) {

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(JsapiConfig.merchantId);
        request.setOutTradeNo(outTradNo);
        return service.queryOrderByOutTradeNo(request);
    }

    /**
     * 退款
     */
    public Refund create(RefundOrderRqs rqs){
        CreateRequest request=new CreateRequest();
        AmountReq req=new AmountReq();
        req.setCurrency("CNY");
        req.setRefund(rqs.getRefund());
        req.setTotal(rqs.getTotal());
        request.setOutTradeNo(rqs.getOutTradNo());
        request.setOutRefundNo(rqs.getOutRefundNo());
        request.setNotifyUrl("https://www.blackfiresoft.com/refundNotify/api");//退款回调地址
        request.setAmount(req);
        return refundService.create(request);
    }

    /**
     * 查询单笔退款（通过商户退款单号）
     */
    public Refund queryRefund(String OutRefundNo){
        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(OutRefundNo);
        return refundService.queryByOutRefundNo(request);
    }

}
