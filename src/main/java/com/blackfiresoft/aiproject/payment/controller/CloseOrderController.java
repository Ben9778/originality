package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.payment.util.JsapiServiceUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 关闭订单
 */
@RestController
@RequestMapping("/closeOrder")
public class CloseOrderController {

    @GetMapping("/api/{outTradNo}")
    public Result close(@PathVariable("outTradNo")String outTradNo){
        JsapiServiceUtil jsapiServiceUtil=new JsapiServiceUtil();
        jsapiServiceUtil.closeOrder(outTradNo);
        return ResultCode.success();
    }
}
