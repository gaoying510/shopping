package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.redis.RedisApi;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Service
public class IUserSerbiceImpl implements IUserService{
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    RedisApi redisApi;

    @Override
    public ServerResponse longin(String username,String password) {


        //参数非空校验，
        if(username==null||username.equals(""))
        {
            return ServerResponse.createServerRespnseByError("用户名不能为空");

        }
        if(password==null||password.equals(""))
        {
            return ServerResponse.createServerRespnseByError("密码不能为空");
        }
        //检查用户名是否存在
        int i = userInfoMapper.checkUsername(username);
        if (i==0)
        {
            return ServerResponse.createServerRespnseByError("用户名不存在");
        }
        //根据用户名密码查询用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
        if(userInfo==null)
        {
            return  ServerResponse.createServerRespnseByError("密码错误");
        }
        //返回结果
        //密码制空
        userInfo.setPassword("");
        return ServerResponse.createServerRespnseBySucces(userInfo);
    }
    /*
     * 注册
     * */
    @Override

    public ServerResponse register(UserInfo userInfo) {
        //参数校验
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户不能为空");
        }

        //检查用户名是否存在
        int i = userInfoMapper.checkUsername(userInfo.getUsername());
        if(i>0)
        {
            return ServerResponse.createServerRespnseByError("用户名已存在");
        }

        //检查邮箱是否存在
        int checkemail = userInfoMapper.checkemail(userInfo.getEmail());
        if(checkemail>0)
        {
            return ServerResponse.createServerRespnseByError("邮箱已存在");
        }
        //注册
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int insert = userInfoMapper.insert(userInfo);
        if(insert>0)
        {
            return ServerResponse.createServerRespnseBySucces("注册成功");
        }
        //返回结果
        return ServerResponse.createServerRespnseByError("注册失败");
    }
    /*
     * 查询用户名查密保问题
     * */
    @Override
    public ServerResponse forget_get_question(String username) {
        //参数非空校验forget_reset_password
     if(username==null||username.equals(""))
     {
         return  ServerResponse.createServerRespnseByError("用户名或密码不能为空");
     }
        //校验用户名
        int checkemail = userInfoMapper.checkUsername(username);
         if(checkemail==0)
         {
             return ServerResponse.createServerRespnseByError("用户名不存在,请重新输入");

         }

        //查询密保问题

        String s = userInfoMapper.selectQuestionByUsername(username);
         if(s==null||s.equals(""))
         {
             return ServerResponse.createServerRespnseByError("密保空");
         }
        return ServerResponse.createServerRespnseBySucces(s);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //非空校验
        if(username==null||username.equals(""))
        {
            return ServerResponse.createServerRespnseByError("用户名不能为空");
        }
        if(question==null||question.equals(""))
        {
            return ServerResponse.createServerRespnseByError("密保问题不能为空");
        }
        if(answer==null||answer.equals(""))
        {
            return ServerResponse.createServerRespnseByError("密保答案不能为空");
        }
        //根据用户名，问题，答案查询信息

        int i = userInfoMapper.selectByUsernameAndQuestionAndAswer(username, question, answer);
        if(i==0)
        {
            return ServerResponse.createServerRespnseByError("密码错误");
        }

        //从服务端生成一个临时的Token返回给客户端
        String forgetToken = UUID.randomUUID().toString();
        //缓存

        //TokenCache.set(username,forgetToken);
        redisApi.set(username,forgetToken);


        return ServerResponse.createServerRespnseBySucces(forgetToken);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
       //非空校验
        if(username==null||username.equals(""))
        {
            return ServerResponse.createServerRespnseByError("用户名不能为空");
        }
        if(passwordNew==null||passwordNew.equals(""))
        {
            return ServerResponse.createServerRespnseByError("密码不能为空");
        }
        if(forgetToken==null||forgetToken.equals(""))
        {
            return ServerResponse.createServerRespnseByError("token不能为空");
        }

        //校验token
        //String s = TokenCache.get(username);
        String s = redisApi.get(username);

        if(s==null)
        {
            return ServerResponse.createServerRespnseByError("token已失效");
        }
        if(!s.equals(forgetToken))
        {
            return ServerResponse.createServerRespnseByError("token错误");
        }


        //修改密码
        int i = userInfoMapper.updateUserPassword(username, MD5Utils.getMD5Code(passwordNew));
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }


        return ServerResponse.createServerRespnseByError("修改密码失败");
    }

    @Override
    public ServerResponse check_valid(String str, String type) {

        //非空校验
         if(str==null||str.equals(""))
         {
             return ServerResponse.createServerRespnseByError("用户名或密码不能为空");
         }
        if(type==null||type.equals(""))
        {
            return ServerResponse.createServerRespnseByError("校验类型参数不能为空");
        }
        //如果TYpe等于 Username=校验用户名str
        //如果TYpe等于 email=校验邮箱str
        if(type.equals("username")){
            int i = userInfoMapper.checkUsername(str);
            //校验用户名已存在
            if(i>0)
            {
                return ServerResponse.createServerRespnseByError("用户已存在");
            }else
            {
                return ServerResponse.createServerRespnseBySucces();
            }
        }else
            //校验邮箱已存在
            if(type.equals("email"))
        {
            int checkemail = userInfoMapper.checkemail(str);
            if(checkemail>0)
            {
                return ServerResponse.createServerRespnseByError("邮箱已存在");
            }else
            {
                return ServerResponse.createServerRespnseBySucces();
            }
        }else {
                return ServerResponse.createServerRespnseByError("参数类型错误");
            }

        //返回结果

    }


    /*
     *
     * 登录状态下重置密码
     * */
    @Override
    public ServerResponse reset_password(String username, String passwordOld, String passwordNew) {

        //参数非空判断
        if (username == null || username.equals("")) {
            return ServerResponse.createServerRespnseByError("用户名不能为空");
        }
        if (passwordOld == null || passwordOld.equals("")) {
            return ServerResponse.createServerRespnseByError("旧密码不能为空");
        }
        if (passwordNew == null || passwordNew.equals("")) {
            return ServerResponse.createServerRespnseByError("新密码不能为空");
        }

        //根据用户名查和旧密码查询用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsernameAndPassword(username, MD5Utils.getMD5Code(passwordOld));
        if (userInfo == null)
        {
            return ServerResponse.createServerRespnseByError("旧密码错误");
        }

        //修改新密码
        userInfo.setPassword(MD5Utils.getMD5Code(passwordNew));
        int i = userInfoMapper.updateByPrimaryKey(userInfo);
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        return ServerResponse.createServerRespnseByError("密码修改失败");
    }
//登录状态下修改个人信息
    @Override
    public ServerResponse update_information(UserInfo user) {
        //非空校验
        if(user==null)
        {
            return ServerResponse.createServerRespnseByError("用户信息不能为空");
        }
        //跟新用户信息
        int i = userInfoMapper.updateUserBySelectActive(user);
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        return ServerResponse.createServerRespnseByError("跟新个人信息失败");
    }

    @Override
    public UserInfo findUserInfoByUserid(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);

    }

    @Override
    public int updateTokenByUserId(Integer userId, String token) {


        return userInfoMapper.updateTokenByUserId(userId,token);
    }
    /*
     * 根据token查询用户信息
     * */

    @Override
    public UserInfo findUserInfoByToken(String token) {
        if (token==null&&token.equals(""))
        {
            return null;
        }

        return userInfoMapper.findUserInfoByToken(token);
    }


}
