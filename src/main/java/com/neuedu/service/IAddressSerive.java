package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IAddressSerive {

    /*
    * 添加收货地址
    * */

    ServerResponse add(Integer userId, Shipping shipping);

    /*
    删除收货地址
    * */

    ServerResponse del(Integer userId,Integer shippingId);

    /*
    * 登录状态更新地址
    * */
    ServerResponse update(Shipping shipping);
/*
* 查看
* */
    ServerResponse select(Integer shippingId );
    /*
    * 选中查看具体地址分页
    * */

    ServerResponse list(Integer pageNum,Integer pageSize);
}
