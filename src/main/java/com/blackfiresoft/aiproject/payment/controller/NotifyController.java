package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.constant.OrderStatus;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import com.blackfiresoft.aiproject.payment.util.NotifyUtil;
import com.blackfiresoft.aiproject.user.pojo.VipModel;
import com.blackfiresoft.aiproject.user.service.UserService;
import com.blackfiresoft.aiproject.utils.TimeUtil;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.wechat.pay.java.core.notification.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 支付通知
 */
@Slf4j
@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private UserService userService;

    @PostMapping(value = "/api")
    public void AsyNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("-------收到微信支付异步通知-------");
        RequestParam requestParam=NotifyUtil.requestParamConfig(request);
        try {
            // 验签、解密并转换成 Transaction
            Transaction transaction = NotifyUtil.notificationParserConfig().parse(requestParam, Transaction.class);
            if(Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState())){
                log.info("订单号{}支付成功",transaction.getOutTradeNo());
                //修改订单状态为支付成功
                payOrderService.updateOrderStatus(OrderStatus.SUCCESS,transaction.getOutTradeNo());
                log.info("订单号{}修改状态为:支付成功",transaction.getOutTradeNo());
                //会员信息更新
                String openid=payOrderService.selectOpenIdByNo(transaction.getOutTradeNo());
                VipModel vipInfo=userService.getUserVipInfo(openid);
                if(vipInfo!=null){
                    if(TimeUtil.getCurrentTime().getTime() < vipInfo.getExpireTime().getTime()){
                        userService.updateVipExpireTime(openid,TimeUtil.TimeStampAndPlus(vipInfo.getExpireTime(),Integer.parseInt(transaction.getAttach())));
                        log.info("openid{}修改会员过期时间成功",openid);
                    }else {
                        userService.updateVipExpireTime(openid,TimeUtil.DateFormatAndPlus(transaction.getSuccessTime(),Integer.parseInt(transaction.getAttach())));
                        log.info("openid{}修改会员过期时间成功",openid);
                    }
                }else {
                    userService.insertVip(openid, TimeUtil.DateFormatAndPlus(transaction.getSuccessTime(),Integer.parseInt(transaction.getAttach())));
                    log.info("openid{}新增会员成功",openid);
                }
            }
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("sign verification failed");
            response.setStatus(401);
        }
        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().println("success");
    }
}
