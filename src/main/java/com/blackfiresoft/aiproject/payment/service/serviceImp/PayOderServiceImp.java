package com.blackfiresoft.aiproject.payment.service.serviceImp;

import com.blackfiresoft.aiproject.payment.mapper.PayOrderMapper;
import com.blackfiresoft.aiproject.payment.model.PayOrderDto;
import com.blackfiresoft.aiproject.payment.model.PayOrderVo;
import com.blackfiresoft.aiproject.payment.service.PayOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service("PayOrderService")
public class PayOderServiceImp implements PayOrderService {
    @Resource
    private PayOrderMapper payOrderMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int insertOrder( String openid, String orderNo, Timestamp createTime, BigDecimal amount, String payWay, String payChannel,
                           String terminal, String descriptions, String orderStatus) {
        return payOrderMapper.insertOrder(openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateOrderStatus(String orderStatus, String orderNo) {
        payOrderMapper.updateOrderStatus(orderStatus, orderNo);
    }

    @Override
    public PayOrderDto selectOrderByNo(String orderNo) {
        return payOrderMapper.selectOrderByNo(orderNo);
    }

    @Override
    public List<PayOrderDto> selectAllOrder() {
        return payOrderMapper.selectAllOrder();
    }

    @Override
    public List<PayOrderDto> selectOrderByDate(Timestamp startDate, Timestamp endDate) {
        return payOrderMapper.selectOrderByDate(startDate,endDate);
    }

    @Override
    public List<PayOrderDto> selectOrderByStatus(String orderStatus) {
        return payOrderMapper.selectOrderByStatus(orderStatus);
    }

    @Override
    public String selectOpenIdByNo(String orderNo){
        return payOrderMapper.selectOpenIdByNo(orderNo);
    }

    @Override
    public List<PayOrderVo>selectOrderByOpenid(String openid){
        return payOrderMapper.selectOrderByOpenid(openid);
    };
}
