package com.neuedu.dao;

import com.neuedu.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbggenerated
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbggenerated
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbggenerated
     */
    List<Order> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Order record);

    Order findOrderByUseridAndrderNo(@Param("userId") Integer userId,
                                     @Param("orderNo") Long orderNo);
/*
* 通过订单号查询信息
* */
    Order findOrderAndrderNo(Long orderNo);
    /*
    * 根据USERID查询
    * */

    List<Order> findOrderByUserid(Integer userId);
    /*
    * 按照创建时间查询订单
    * */
    List<Order> findOrderByCreateTime(@Param("orderStatus") Integer orderStatus,
                                      @Param("time") String time);
}