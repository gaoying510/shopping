package com.neuedu.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping(value ="/order")
public class OrderController {
 @Autowired
    IOrderService orderService;
    /*
    * 创建订单
    * */
    @RequestMapping(value = "/create.do/shippingId/{shippingId}")
    public ServerResponse create(HttpSession session,
                                 @PathVariable("shippingId") Integer shippingId)
    {
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }

        return orderService.create(userInfo.getId(),shippingId) ;
    }
    /*
     * 取消订单
     * */
    @RequestMapping(value = "/cancel.do/orderNo/{orderNo}")
    public ServerResponse cancel(HttpSession session,
                                 @PathVariable("orderNo") long orderNo)
    {
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }

        return orderService.cancel(userInfo.getId(),orderNo) ;
    }

    /*
    * 获取购物车商品信息
    *
    * */
    @RequestMapping(value = "/get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session)
    {
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }

        return orderService.get_order_cart_product(userInfo.getId()) ;
    }

    /*
    * 订单列表
    * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize
                               )
    {
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }

        return orderService.list(userInfo.getId(),pageNum,pageSize) ;
    }
    /*
     * 订单详情
     * */
    @RequestMapping(value = "/detail.do/orderNo/{orderNo}")
    public ServerResponse detail(HttpSession session,
                                 @PathVariable("orderNo") Long orderNo)
    {
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }

        return orderService.detail(orderNo) ;
    }

    /*
    * 支付接口
    * */
    @RequestMapping(value = "/pay.do/orderNo/{orderNo}")
    public ServerResponse pay(HttpSession session,
                              @PathVariable("orderNo") Long orderNo,HttpServletRequest request){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }
        String path=request.getSession().getServletContext().getRealPath("upload");
        return orderService.pay(userInfo.getId(),orderNo,path);
    }
    /*
    * 支付宝服务器回调应用服务器接口
    * */
    @RequestMapping(value = "/alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){

        System.out.println("=====支付宝服务器回调应用服务器接口===");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String,String> requestparams= Maps.newHashMap();
        Iterator<String> iterator = parameterMap.keySet().iterator();
        while (iterator.hasNext())
        {
            String key=iterator.next();
            String[] strArr = parameterMap.get(key);
            String value="";
            for (int i=0;i<strArr.length;i++)
            {
              value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparams.put(key,value);
        }
        //支付宝验证签名
        try {
            requestparams.remove("sign_type");
            boolean result=AlipaySignature.rsaCheckV2(requestparams, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
           if (!result)
           {
               return ServerResponse.createServerRespnseByError("非法请求，验证不通过");
           }
           //处理业务逻辑

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderService.alipay_callback(requestparams);

    }

    /*
    * 查询订单支付状态
    * */
    @RequestMapping(value = "/query_order_pay_status.do/orderNo/{orderNo}")
    public ServerResponse query_order_pay_status(HttpSession session,
                                                 @PathVariable("orderNo")Long orderNo){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");

        }
        return orderService.query_order_pay_status(orderNo);
    }
}
