package com.neuedu.json;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

@Component
public class ObjectMapperApi {


    @Autowired
    ObjectMapper objectMapper;

    /*
     * 将对象转成字符串，
     * */
    public <T> String obj2str(T t)
    {
        if (t==null)
        {
            return null;
        }

        try {
            //判断我传进来的t是不是字符串类型，如果不是字符串类型，强转
            return  t instanceof String?(String)t: objectMapper.writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
     * 将对象转成字符串，
     * */
    public <T> String obj2strPretty(T t)
    {
        if (t==null)
        {
            return null;
        }

        try {
            //判断我传进来的t是不是字符串类型，如果不是字符串类型，强转
            return  t instanceof String?(String)t: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

        /*
         * 字符串转对象
         * */
    }
    public <T> T str2Obj(String str,Class<T> clazz)
    //调用方法判断是否为空
    {
        if (StringUtils.isEmpty(str)||clazz==null)
        {
            return null;
        }
        try {
            //判断类的类型          如果不是字符串强转//字符串转成对象
            return clazz.equals(String.class)?(T) str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T>T str2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str)||typeReference==null)
        {
            return null;
        }
        try {
           if (typeReference.getType().equals(String.class)){
               return (T)str;
           }
           return objectMapper.readValue(str,typeReference);

           /*return typeReference.getType().equals(String.class) ? (T)str : objectMapper.readValue(str,typeReference);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
