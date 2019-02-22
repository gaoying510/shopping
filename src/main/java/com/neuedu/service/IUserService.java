package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

public interface IUserService {

    /*
    * 登录
    * */
    ServerResponse longin(String username,String password);

    /*
     * 注册
     * */
    ServerResponse register(UserInfo userInfo);

    /*
     * 根据用户名查询密保问题
     * */
    ServerResponse forget_get_question(String username);
    /*
     * 提交密保问题答案
     * */
    ServerResponse forget_check_answer(String username,String question,String answer);

    /*
    *
    * 忘记密码重置密码
    * */

    ServerResponse forget_reset_password(String username,String passwordNew,String forgetToken);

    /*
    * 检查用户名或邮箱是否存在
    * */

    ServerResponse check_valid(String str,String type);
    /*
    *
    * 登录状态下重置密码
    * */

    ServerResponse reset_password(String username,String passwordOld, String passwordNew);
    /*
    *
    * 登录状态下修改个人信息
    * */
    ServerResponse update_information(UserInfo user);

    /**
     *
     * 根据用户ID查询用户信息
     *
     */
      UserInfo findUserInfoByUserid(Integer userId);

        /*
        * 保存用户TOKEN信息
        * */
        int updateTokenByUserId(Integer userId,String token);
    /*
     * 根据token查询用户信息
     * */
    UserInfo findUserInfoByToken(String token);
}
