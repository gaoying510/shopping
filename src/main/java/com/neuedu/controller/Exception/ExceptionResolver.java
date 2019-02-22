package com.neuedu.controller.Exception;

import com.neuedu.common.ResponseCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
* 全局异常处理类
* */

@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {


        //为了调试方便打印堆栈信息，等上线之后在去掉
       // e.printStackTrace();
        //转成JSon格式
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        //封装成前端高复用对象，
        modelAndView.addObject("status", ResponseCode.ERROR);
        modelAndView.addObject("msg","接口调用出错");
        //错误信息打印
        modelAndView.addObject("data",e.toString());

        return modelAndView;
    }
}
