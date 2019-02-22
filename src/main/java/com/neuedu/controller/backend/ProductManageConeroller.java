package com.neuedu.controller.backend;


import com.neuedu.pojo.Product;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product")
public class ProductManageConeroller {


    @Autowired
    IProductService iProductService;


    /*
    *
    * 新增或跟新商品
    * */
    @RequestMapping(value = "/save.do")
    public ServerResponse saveOrUpdate(HttpSession session, Product product)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return iProductService.saveOrUpdate(product);
    }
/*
*
* 商品上下架
* */
    @RequestMapping(value = "/set_sale_status.do/productId/{productId}/status/{status}")
    public ServerResponse set_sale_status(HttpSession session,
                                          @PathVariable("productId") Integer productId,
                                          @PathVariable("status") Integer status)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return iProductService.set_sale_status(productId,status);
    }

    /*
    * 查看商品详情
    * */
    @RequestMapping(value = "/detail.do/productId/{productId}")
    public ServerResponse detail(HttpSession session,
                                 @PathVariable("productId") Integer productId)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return iProductService.detail(productId);
    }
    /*
    * 查看商品列表，分页
    * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum
    ,@RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
    {

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iProductService.list(pageNum,pageSize);
    }
    /*
     * 商品搜索
     * */
    @RequestMapping(value = "/search.do/productId/{productId}/{pageNum}/{pageSize}")
    public ServerResponse searchCategoryId(HttpSession session, @PathVariable("categoryId") Integer productId
                                                        , @PathVariable("pageNum") Integer pageNum
                                                        , @PathVariable("pageSize") Integer pageSize)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return iProductService.search(productId,null,pageNum,pageSize);
    }

    @RequestMapping(value = "/search.do/productName/{productName}/{pageNum}/{pageSize}")
    public ServerResponse searchProductName(HttpSession session, @PathVariable("productName") String productName
            , @PathVariable("pageNum") Integer pageNum
            , @PathVariable("pageSize") Integer pageSize)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);

        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return iProductService.search(null,productName,pageNum,pageSize);
    }
}
