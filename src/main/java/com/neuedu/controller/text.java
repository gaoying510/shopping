package com.neuedu.controller;

import com.google.common.collect.Lists;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.json.ObjectMapperApi;
import com.neuedu.pojo.UserInfo;
import com.neuedu.redis.RedisApi;
import com.neuedu.redis.RedisProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/portal")
public class text {
    @Autowired
    RedisProperties redisProperties;

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisApi redisApi;
    @Autowired
    ObjectMapperApi objectMapperApi;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/redis")
    public String getJedis(){
        Jedis resource = jedisPool.getResource();
        String root = resource.set("root", "123");
        jedisPool.returnResource(resource);

        return root;
    }

    @RequestMapping(value = "/key/{key}")
    public String getkey(@PathVariable("key") String key){
        String s = redisApi.get(key);
        return s;
    }

    @RequestMapping(value = "/json1/{Id}")
    public ServerResponse<UserInfo> finduserByJson(@PathVariable Integer Id)
    {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Id);
        String s = objectMapperApi.obj2str(userInfo);
        System.out.println("uuuuuuuuu"+s);
        return ServerResponse.createServerRespnseBySucces(s);
    }
    @RequestMapping(value = "/jsonPretty/{Id}")
    public ServerResponse<UserInfo> finduserByJsonPretty(@PathVariable Integer Id)
    {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Id);
        List<UserInfo> lists = Lists.newArrayList(userInfo);
        String s = objectMapperApi.obj2strPretty(lists);
        List list = objectMapperApi.str2Obj(s, new TypeReference<List<UserInfo>>(){

        });
        System.out.println("ppppppppp"+list);
        return ServerResponse.createServerRespnseBySucces(s);
    }


    }

