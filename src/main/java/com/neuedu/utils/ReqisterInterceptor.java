package com.neuedu.utils;

import com.neuedu.Interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


@SpringBootConfiguration
public class ReqisterInterceptor implements WebMvcConfigurer {

    @Autowired
    AutoLoginInterceptor autoLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       List<String> excludeList = new ArrayList<>();
       excludeList.add("/manage/user/login/**");
       excludeList.add("/user/register.do");
       excludeList.add("/user/logout");
       excludeList.add("/product/**");
       excludeList.add("/manage/product/**");
       excludeList.add("/user/login/**");
       excludeList.add("/portal/json");
       excludeList.add("/portal/jsonPretty/**");
       excludeList.add("/order/alipay_callback.do");
       registry.addInterceptor(autoLoginInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);

    }
}
