package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category")
public class CategoryMangeController {


    @Autowired
    ICategoryService iCategoryService;
    /*
    *
    * 获取品类子节点
    * */

    @RequestMapping(value = "/get_category.do/categoryId/{categoryId}")
    public ServerResponse get_category(HttpSession session,
                                       @PathVariable("categoryId") Integer categoryId)
    {

        //从SESSION中拿到用户
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        //判断用户是否登录
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NEED_LOGIN.getCode(),Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if(userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iCategoryService.get_category(categoryId);
    }

    /*
    * 增加节点
    *
    * */
    @RequestMapping(value = "/add_category.do")
    public ServerResponse add_category(HttpSession session,@RequestParam(required = false,defaultValue = "0") Integer parentId,String categoryName){

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null)
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NEED_LOGIN.getCode(),Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()==Const.ReponseCodeEnum.NEED_LOGIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iCategoryService.add_category(parentId,categoryName);
    }

    /*
    *
    * 修改节点
    * */
    @RequestMapping(value = "/set_category_name.do/categoryId/{categoryId}/categoryName/{categoryName}")
    public ServerResponse set_category_name(HttpSession session,
                                            @PathVariable("categoryId") Integer categoryId,
                                            @PathVariable("categoryName") String categoryName){

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null)
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NEED_LOGIN.getCode(),Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()==Const.ReponseCodeEnum.NEED_LOGIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iCategoryService.set_category_name(categoryId,categoryName);
    }

    /*
    *获取当前分类id及递归子节点categoryId
    *
    * */
    @RequestMapping(value = "/get_deep_category.do/categoryId/{categoryId}")
    public ServerResponse get_deep_category(HttpSession session,
                                            @PathVariable("categoryId")Integer categoryId){

        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null)
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NEED_LOGIN.getCode(),Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()==Const.ReponseCodeEnum.NEED_LOGIN.getCode())
        {
            return ServerResponse.createServerRespnseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getCode(),Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return iCategoryService.get_deep_category(categoryId);
    }
}
