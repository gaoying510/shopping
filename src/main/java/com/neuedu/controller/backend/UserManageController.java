package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
*
* 后台用户控制器
* */
@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {


    /*
    *
    * 管理员登录
    * */
    @Autowired
     IUserService iUserService;


    @RequestMapping(value = "/login.do")
    public ServerResponse login(HttpSession Session, String username, String password)
    {
        ServerResponse serverResponse = iUserService.longin(username, password);
        //登录成功
        if(serverResponse.isSuccess())
        {
            UserInfo userInfo =(UserInfo) serverResponse.getData();
            if(userInfo.getRole()==Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                    return ServerResponse.createServerRespnseByError("无权限登录");
            }
            Session.setAttribute(Const.CURRENTUSER ,userInfo);

        }
        return serverResponse;
    }
}
