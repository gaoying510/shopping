package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.IPUtil;
import com.neuedu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.net.UnknownHostException;

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
    public ServerResponse login(HttpServletRequest request, HttpServletResponse response,HttpSession Session, String username, String password)
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
            //生产自动登录TOKEN
            String ip = IPUtil.getRemoteAddress(request);
            try {
                String mac = IPUtil.getMACAddress(ip);
                String token = MD5Utils.getMD5Code(mac);
                //将token保存到数据库
            iUserService.updateTokenByUserId(userInfo.getId(),token);
            //token作为cookie响应到客户端
                Cookie cookie = new Cookie(Const.AUTOLOGINTOKEN, token);
                cookie.setPath("/");
                //设置cookie有效期
                cookie.setMaxAge(60*60*24*7);
             response.addCookie(cookie);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return serverResponse;
    }
}
