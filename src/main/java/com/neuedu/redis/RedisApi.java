package com.neuedu.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisApi {
    @Autowired
    private JedisPool jedisPool;

    /*
    * set(key,value)
    * */
     public String set(String key,String value)
     {
         String result=null;
         Jedis resource=null;
         try {
             resource = jedisPool.getResource();
             result = resource.set(key, value);
         }catch (Exception e)
         {
         jedisPool.returnBrokenResource(resource);
         }finally {
            if (resource!=null)
            {
                jedisPool.returnResource(resource);
            }
         }
         return  result;
     }

    /*
     * setnx(key,second,value)
     * */
    public String setex(String key,int second,String value)
    {
        String result=null;
        Jedis resource=null;
        try {
            resource = jedisPool.getResource();
            result = resource.setex(key, second, value);
        }catch (Exception e)
        {
            jedisPool.returnBrokenResource(resource);
        }finally {
            if (resource!=null)
            {
                jedisPool.returnResource(resource);
            }
        }
        return  result;
    }

    /*
     * get(key)
     * */
    public String get(String key)
    {
        String result=null;
        Jedis resource=null;
        try {
            resource = jedisPool.getResource();
            result = resource.get(key);
        }catch (Exception e)
        {
            jedisPool.returnBrokenResource(resource);
        }finally {
            if (resource!=null)
            {
                jedisPool.returnResource(resource);
            }
        }
        return  result;
    }


    /*
     * del(key)
     * */
    public Long del(String key)
    {
        Long result=null;
        Jedis resource=null;
        try {
            resource = jedisPool.getResource();
            result = resource.del(key);
        }catch (Exception e)
        {
            jedisPool.returnBrokenResource(resource);
        }finally {
            if (resource!=null)
            {
                jedisPool.returnResource(resource);
            }
        }
        return  result;
    }

    /*
     *设置过期时间
     * */
    public Long set(String key,int second)
    {
        Long result=null;
        Jedis resource=null;
        try {
            resource = jedisPool.getResource();
            result = resource.expire(key,second);
        }catch (Exception e)
        {
            jedisPool.returnBrokenResource(resource);
        }finally {
            if (resource!=null)
            {
                jedisPool.returnResource(resource);
            }
        }
        return  result;
    }
}
