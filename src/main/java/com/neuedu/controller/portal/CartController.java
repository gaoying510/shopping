package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    ICartService cartService;
    /*
    * 购物车添加商品
    * */

 @RequestMapping(value = "/add.do/productId/{productId}/count/{count}")
    public ServerResponse add(HttpSession session,
                              @PathVariable("productId") Integer productId,
                              @PathVariable("count")Integer count)
 {
          //用户是否校验
     UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
   if(userInfo==null)
   {
       return ServerResponse.createServerRespnseByError("用户需要登录");
   }
     return cartService.add(userInfo.getId(),productId,count);
    }
    /*
     * 购物车列表
     * */

    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.list(userInfo.getId());
    }
    /*
     * 跟新购物车数量
     * */

    @RequestMapping(value = "/update.do/productId/{productId}/count/{count}")
    public ServerResponse update(HttpSession session,
                                 @PathVariable("productId") Integer productId,
                                 @PathVariable("count") Integer count)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.update(userInfo.getId(),productId,count);
    }
    /*
     * 移除购物车某个产品
     * */

    @RequestMapping(value = "/delete_product.do/productIds/{productIds}")
    public ServerResponse delete_product(HttpSession session,
                                         @PathVariable("productIds") String productIds)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.delete_product(userInfo.getId(),productIds);
    }
    /*
    * 购物车中选中某个商品
    * */
    @RequestMapping(value = "/select.do/productId/{productId}")
    public ServerResponse select(HttpSession session,
                                 @PathVariable("productId") Integer productId)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.select(userInfo.getId(),productId,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }
    /*
     * 购物车中取消选中某个商品
     * */
    @RequestMapping(value = "/un_select.do/productId/{productId}")
    public ServerResponse un_select(HttpSession session,
                                    @PathVariable("productId") Integer productId)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.select(userInfo.getId(),productId,Const.CartCheckedEnum.PRODUCT_UNCHECKED.getCode());
    }
    /*
     * 购物车全选某个商品
     * */
    @RequestMapping(value = "/select_all.do")
    public ServerResponse select_all(HttpSession session)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.select(userInfo.getId(),null ,Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
    }
    /*
     * 购物车取消全选某个商品
     * */
    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.select(userInfo.getId(),null ,Const.CartCheckedEnum.PRODUCT_UNCHECKED.getCode());
    }
    /*
     * 查询购物车中商品数量
     * */
    @RequestMapping(value = "/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session)
    {
        //用户是否校验
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户需要登录");
        }
        return cartService.get_cart_product_count(userInfo.getId());
    }
}

