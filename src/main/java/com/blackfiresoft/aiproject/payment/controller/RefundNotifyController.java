package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.constant.OrderStatus;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import com.blackfiresoft.aiproject.payment.util.NotifyUtil;
import com.blackfiresoft.aiproject.user.service.UserService;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import com.wechat.pay.java.service.refund.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退款通知
 */
@Slf4j
@Controller
@RequestMapping("/refundNotify")
public class RefundNotifyController {
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private UserService userService;

    @PostMapping(value = "/api")
    public void AsyRefundNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("-------收到退款异步通知-------");
        RequestParam requestParam= NotifyUtil.requestParamConfig(request);
        try {
            // 验签、解密并转换成 Transaction
            RefundNotification refundNotification = NotifyUtil.notificationParserConfig().parse(requestParam, RefundNotification.class);
            if(Status.SUCCESS.equals(refundNotification.getRefundStatus())){
                log.info("订单号{}退款成功",refundNotification.getOutTradeNo());
                //修改订单状态为退款成功
                payOrderService.updateOrderStatus(OrderStatus.REFUNDED,refundNotification.getOutTradeNo());
                log.info("订单号{}修改状态为:退款成功",refundNotification.getOutTradeNo());
                //删除会员信息
                String openid=payOrderService.selectOpenIdByNo(refundNotification.getOutTradeNo());
                userService.deleteVip(openid);
                log.info("openid{}会员删除成功",openid);
            }
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            log.error("sign verification failed");
            response.setStatus(401);
        }
        response.setStatus(200);
    }


}
