package com.neuedu.dao;

import com.neuedu.pojo.Product;
import com.neuedu.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    List<Product> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Product record);


    /*
    *
    * 跟新商品
    * */

    int updateProductKeySelective(Product product);


    /*
    * 根据商品ID和名字查询商品
    * */

    List<Product> findProductByProductIdAnductName(@Param("productId") Integer productId,
                                                   @Param("productName") String productName);



/*
*
* 前台接口--搜索商品
* */
       List<Product> searchProudct(@Param("integerSet")Set<Integer> integerSet,
                                   @Param("keyword") String keyword
                                   );
}