package com.blackfiresoft.aiproject.utils;

import com.blackfiresoft.aiproject.common.constant.JsapiConfig;
import com.wechat.pay.java.core.util.PemUtil;
import java.security.*;
import java.util.Base64;


/**
 * 小程序支付参数签名工具
 */
public class SignUtil {

    public static String getSignWithBase64(String appid, String time, String nonceStr, String prepayId) {

        String data = appid + "\n" + time + "\n" + nonceStr + "\n" + prepayId + "\n";

        //加载私钥
        try {
            PrivateKey privateKey = loadPrivateKey();
            // 创建一个SHA256WithRSA的签名对象
            Signature signature = Signature.getInstance("SHA256withRSA");
            // 用私钥初始化签名对象
            signature.initSign(privateKey);
            // 更新要签名的数据
            signature.update(data.getBytes());
            // 签名
            byte[] signatureBytes = signature.sign();
            // 将签名转换为Base64编码的字符串
            return Base64.getEncoder().encodeToString(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected static PrivateKey loadPrivateKey() {

        return PemUtil.loadPrivateKeyFromPath(JsapiConfig.privateKeyPath);
    }
}
