package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICartService {
/*
* 购物车添加
* */
    public ServerResponse add(Integer userId,Integer productId,Integer count);
/*
* 查询列表
* */
    ServerResponse list(Integer userId);
    /**
     *
     *跟新数量
     */


    ServerResponse update(Integer userId,Integer productId,Integer count);

    /*
    * 移除购物车某个商品
    * */

    ServerResponse delete_product(Integer userId,String productIds);

    /*
    * 购物车选中某个商品
    * */

    ServerResponse select(Integer userId,Integer productId,Integer check);

    /*购物车中产品的数量
    *
    * */
    ServerResponse get_cart_product_count(Integer userId);
}
