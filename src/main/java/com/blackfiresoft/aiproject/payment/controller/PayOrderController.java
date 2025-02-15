package com.blackfiresoft.aiproject.payment.controller;

import com.blackfiresoft.aiproject.common.result.Result;
import com.blackfiresoft.aiproject.common.result.ResultCode;
import com.blackfiresoft.aiproject.payment.model.PayOrderDto;
import com.blackfiresoft.aiproject.payment.model.PayOrderVo;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import com.blackfiresoft.aiproject.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单处理接口
 */
@Slf4j
@RestController
@RequestMapping("/PayOrder")
public class PayOrderController {

    @Resource
    private PayOrderService payOrderService;

    @GetMapping("/AllOrder")
    public Result selectAllOrder(){
        List<PayOrderDto> orderList=payOrderService.selectAllOrder();
        return ResultCode.success(orderList);
    }

    @GetMapping("/orderByNo/{orderNo}")
    public Result selectOrderByNo(@PathVariable String orderNo){
        PayOrderDto payOrder=payOrderService.selectOrderByNo(orderNo);
        return ResultCode.success(payOrder);
    }

    @GetMapping("/oderByDate")
    public Result selectOrderByDate(@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate){
        List<PayOrderDto> orderList=payOrderService.selectOrderByDate(TimeUtil.stringConvertTimeStamp(startDate),TimeUtil.stringConvertTimeStamp(endDate));
        return ResultCode.success(orderList);
    }

    @GetMapping("/orderByStatus/{orderStatus}")
    public Result selectOrderByStatus(@PathVariable String orderStatus){
        List<PayOrderDto> orderList=payOrderService.selectOrderByStatus(orderStatus);
        return ResultCode.success(orderList);
    }

    @PostMapping("/selectOrders")
    public Result selectOrderByOpenid(@RequestParam("openid") String openid){
        List<PayOrderVo> orderList=payOrderService.selectOrderByOpenid(openid);
        return ResultCode.success(orderList);
    }

}
