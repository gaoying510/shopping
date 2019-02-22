package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/*
* 登录
* */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "/login/username/{username}/password/{password}")
   public ServerResponse login(HttpSession Session,
                               @PathVariable("username") String username,
                               @PathVariable("password") String password)
   {
       ServerResponse serverResponse = iUserService.longin(username, password);
       //登录成功
       if(serverResponse.isSuccess())
       {
           UserInfo userInfo =(UserInfo) serverResponse.getData();
           Session.setAttribute(Const.CURRENTUSER ,userInfo);


       }
       return serverResponse;
   }
    /*
     * 注册
     * */
    @RequestMapping(value = "/register.do")
   public ServerResponse register(HttpSession session,UserInfo userInfo)
   {
       ServerResponse register = iUserService.register(userInfo);

       return register;
   }
    /*
     * 查询用户名查密保问题
     * */

    @RequestMapping(value = "/forget_get_question.do/username/{username}")
    public ServerResponse forget_get_question(@PathVariable("username") String username)
    {
        ServerResponse serverResponse = iUserService.forget_get_question(username);
        return serverResponse;
    }
    /*
     * 提交问题答案
     * */
    @RequestMapping(value = "/forget_check_answer.do/username/{username}/question/{question}/answer/{answer}")
    public ServerResponse forget_check_answer(@PathVariable("username") String username,
                                              @PathVariable("question")  String question,
                                              @PathVariable("answer")  String answer )
    {
        ServerResponse serverResponse = iUserService.forget_check_answer(username,question,answer);
        return serverResponse;
    }
    /*
     * 忘记密码重置密码
     * */
    @RequestMapping(value = "/forget_reset_password.do/username/{username}/passwordNew/{passwordNew}/forgetToken/{forgetToken}")
    public ServerResponse forget_reset_password(@PathVariable("username") String username,
                                                @PathVariable("passwordNew") String passwordNew,
                                                @PathVariable("forgetToken") String forgetToken )
    {
        ServerResponse serverResponse = iUserService.forget_reset_password(username,passwordNew,forgetToken);
        return serverResponse;
    }

    //检查用户名或邮箱是否存在

    @RequestMapping(value = "/check_valid.do/str/{str}/type/{type}")
    public ServerResponse check_valid( @PathVariable("str")String str,
                                        @PathVariable("type")String type )
    {
        ServerResponse serverResponse = iUserService.check_valid(str,type);
        return serverResponse;
    }
    /*
    * 获取登录用户信息
    * */
    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse get_user_info(HttpSession session)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }

        userInfo.setPassword("");
        userInfo.setRole(null);
        userInfo.setQuestion("");
        userInfo.setAnswer("");
        return ServerResponse.createServerRespnseBySucces(userInfo);
    }

    /*
    *
    * 登录状态重置密码
    * */
    @RequestMapping(value = "/reset_password.do/passwordOld/{passwordOld}/passwordNew/{passwordNew}")
    public ServerResponse reset_password(HttpSession session,
                                         @PathVariable("passwordOld")String passwordOld,
                                         @PathVariable("passwordNew")String passwordNew){
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }

        return iUserService.reset_password(userInfo.getUsername(),passwordOld,passwordNew);

    }

    /*
    *
    * 登录状态下跟新用户信息
    * */

    @RequestMapping(value = "/update_information.do")
    public ServerResponse update_information(HttpSession session,UserInfo user) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }
        //设置ID 根据用户ID更新
        user.setId(userInfo.getId());
        ServerResponse serverResponse = iUserService.update_information(user);
        if(serverResponse.isSuccess())
        {
            //跟新SESSION中的用户信息
            UserInfo userInfoByUserid = iUserService.findUserInfoByUserid(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,userInfoByUserid);
        }
        return serverResponse;
    }

    /*
     * 获取登录用户详细信息
     * */
    @RequestMapping(value = "/get_inforamtion.do")
    public ServerResponse get_inforamtion(HttpSession session)
    {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }

        userInfo.setPassword("");
        return ServerResponse.createServerRespnseBySucces(userInfo);
    }
    /*
    *
    * 退出登录
    * */
    @RequestMapping(value = "/logout.do")
    public ServerResponse logout(HttpSession session) {
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createServerRespnseBySucces();

    }

}
