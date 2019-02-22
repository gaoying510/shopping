package com.neuedu.Interceptor;

import com.google.gson.Gson;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println("------preHandle------");

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);


        if (userInfo==null)
        {
            Cookie[] cookies = request.getCookies();
            if(cookies!=null)
            {
                for (Cookie cookie:cookies) {
                    String name = cookie.getName();
                    if (name.equals(Const.AUTOLOGINTOKEN)){
                        String value = cookie.getValue();
                       //根据token查询用户信息
                        userInfo = userService.findUserInfoByToken(value);
                        if (userInfo!=null)
                        {
                            session.setAttribute(Const.CURRENTUSER,userInfo);
                            return true;
                        }
                        break;
                    }
                }

            }
            response.reset();
            response.setContentType("text/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            ServerResponse serverResponse = ServerResponse.createServerRespnseByError("用户未登录");
            Gson gson = new Gson();
            String s = gson.toJson(serverResponse);
            writer.flush();
            writer.write(s);
            writer.close();

            return false;
        }
        /*if (userInfo==null||userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode())
        {
            response.setCharacterEncoding("UTF-8");

            ServerResponse serverResponse = ServerResponse.createServerRespnseByError("没有权限登录");
            Gson gson = new Gson();
            String s = gson.toJson(serverResponse);
            response.reset();
            PrintWriter writer = response.getWriter();
            writer.flush();
            writer.write(s);
            writer.close();

            return false;
        }*/

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("------postHandle------");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("------afterCompletion------");

    }
}
