package com.neuedu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IAddressSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IAddressSeriveImpl implements IAddressSerive{


    @Autowired
    ShippingMapper shippingMapper;

    //添加购物车地址
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        //非空判断
        if(shipping==null)
        {
            return ServerResponse.createServerRespnseByError("参数错误");
        }
        // 添加
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);
        //返回结果
        Map<String,Integer> map=Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerRespnseBySucces(map);
    }
    /*
    *
    * 删除地址
    * */
    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {

        //参数非空校验
        if(shippingId==null)
        {
            return ServerResponse.createServerRespnseByError("参数错误");
        }
        //删除
        int i = shippingMapper.deleteByUserAndShippingId(userId, shippingId);

        //返回结果
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        return ServerResponse.createServerRespnseByError("删除失败");
    }
    /*
    *
    * 登录状态下更新
    * */

    @Override
    public ServerResponse update(Shipping shipping) {
        //非空判断
        if(shipping==null)
        {
            return ServerResponse.createServerRespnseByError("参数错误");
        }
        //跟新
        int i = shippingMapper.updateBySelectiveKey(shipping);

        //返回结果
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        return ServerResponse.createServerRespnseByError("更新失败");
    }
/*
* 查看
* */
    @Override
    public ServerResponse select(Integer shippingId) {
        //非空判断
        if(shippingId==null)
        {
            return ServerResponse.createServerRespnseByError("参数错误");
        }
        //查询
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        //返回
        return ServerResponse.createServerRespnseBySucces(shipping);
    }
/*
* 选中查看具体地址（分页）
* */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippings = shippingMapper.selectAll();
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.createServerRespnseBySucces(pageInfo);
    }
}
