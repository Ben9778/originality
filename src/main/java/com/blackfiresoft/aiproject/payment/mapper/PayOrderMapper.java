package com.blackfiresoft.aiproject.payment.mapper;

import com.blackfiresoft.aiproject.payment.model.PayOrderDto;
import com.blackfiresoft.aiproject.payment.model.PayOrderVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 订单接口
 */
public interface PayOrderMapper {
    /**
     * 创建订单
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Insert("insert into orders(openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus) values(#{openid},#{orderNo},#{createTime},#{amount},#{payWay},#{payChannel},#{terminal},#{descriptions},#{orderStatus})")
    int insertOrder(@Param("openid")String openid, @Param("orderNo")String orderNo, @Param("createTime")Timestamp createTime,
                    @Param("amount") BigDecimal amount,@Param("payWay")String payWay,@Param("payChannel")String payChannel,
                    @Param("terminal")String terminal,@Param("descriptions")String descriptions,@Param("orderStatus")String orderStatus);

    /**
     * 更新订单状态
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Update("update orders set orderStatus=#{orderStatus} where orderNo=#{orderNo}")
    void updateOrderStatus(@Param("orderStatus")String orderStatus,@Param("orderNo")String orderNo);

    /**
     * 根据订单号查单
     */
    @Select("select openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus from orders where orderNo=#{orderNo}")
    PayOrderDto selectOrderByNo(@Param("orderNo")String orderNo);

    /**
     * 查询所有订单
     */
    @Select("select openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus from orders")
    List<PayOrderDto> selectAllOrder();

    /**
     * 按日期范围查订单
     */
    @Select("select openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus from orders" +
            " where createTime between #{startDate} and #{endDate}")
    List<PayOrderDto> selectOrderByDate(@Param("startDate")Timestamp startDate,@Param("endDate")Timestamp endDate);

    /**
     * 按状态查订单
     */
    @Select("select openid,orderNo,createTime,amount,payWay,payChannel,terminal,descriptions,orderStatus from orders" +
            " where orderStatus=#{orderStatus}")
    List<PayOrderDto> selectOrderByStatus(@Param("orderStatus")String orderStatus);

    /**
     * 根据订单号查用户openid
     */
    @Select("select openid from orders where orderNo=#{orderNo}")
    String selectOpenIdByNo(@Param("orderNo")String orderNo);

    /**
     * 根据用户openid查询订单
     */
    @Select("select orderNo,createTime,amount,orderStatus from orders where openid=#{openid}")
    List<PayOrderVo>selectOrderByOpenid(@Param("openid")String openid);

}
