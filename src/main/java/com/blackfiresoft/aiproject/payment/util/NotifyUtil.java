package com.blackfiresoft.aiproject.payment.util;

import com.blackfiresoft.aiproject.common.constant.JsapiConfig;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;

import javax.servlet.http.HttpServletRequest;

public class NotifyUtil {

    public static NotificationParser notificationParserConfig(){
        NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(JsapiConfig.merchantId)
                .privateKeyFromPath(JsapiConfig.privateKeyPath)
                .merchantSerialNumber(JsapiConfig.merchantSerialNumber)
                .apiV3Key(JsapiConfig.apiV3Key)
                .build();
        return new NotificationParser(config);
    }

    public static RequestParam requestParamConfig(HttpServletRequest request){
        String wechatPaySerial=request.getHeader("Wechatpay-Serial");
        String wechatpayNonce=request.getHeader("Wechatpay-Nonce");
        String wechatSignature=request.getHeader("Wechatpay-Signature");
        String wechatTimestamp=request.getHeader("Wechatpay-Timestamp");
        String body= HttpUtils.readData(request);

        return new RequestParam.Builder()
                .serialNumber(wechatPaySerial)
                .nonce(wechatpayNonce)
                .signature(wechatSignature)
                .timestamp(wechatTimestamp)
                .body(body)
                .build();
    }
}
