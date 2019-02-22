package com.neuedu.aspect;


import com.neuedu.common.ServerResponse;
import com.neuedu.json.ObjectMapperApi;
import com.neuedu.redis.RedisApi;
import com.neuedu.utils.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RedisAspect {

    @Pointcut("execution(* com.neuedu.service.impl.IProductServiceImpl.detail_portal(..))")
    public void pointcut(){

    }
    @Autowired
    RedisApi redisApi;
    @Autowired
    ObjectMapperApi objectMapperApi;
        //   表示切入点表达式织入到那个方法中，会把织入点信息封装到joinPoint对象中

    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint joinPoint)
    {
        Object o=null;
        try {
            //keyMD5(全类名+方法名+参数)
            StringBuffer stringBuffer = new StringBuffer();
            //获取全类名   获取的目标的类
            String Classname = joinPoint.getTarget().getClass().getName();
            System.out.println("=====全类名"+Classname);
            //目标方法名
            String name = joinPoint.getSignature().getName();
            System.out.println("======目标方法"+name);
            //方法中的参数
            Object[] args = joinPoint.getArgs();
            if (args!=null)
            {
                for (Object o1:args) {
                    System.out.println("====arg"+o1);
                    stringBuffer.append(o1);
                }
            }

            //读缓存
            String key = MD5Utils.getMD5Code(stringBuffer.toString());
            String json = redisApi.get(key);
            if (json!=null && !json.equals(""))
            {
                System.out.println("=======读取到了缓存=====");
                return objectMapperApi.str2Obj(json, ServerResponse.class);
            }

            //执行目标方法
           o= joinPoint.proceed();
            System.out.println("=======读取到了数据库=====");
            //转成JSon字符串，存到数据库里
            if (o!=null)
            {
                String jsoncache = objectMapperApi.obj2str(o);
                redisApi.set(key,jsoncache);
                System.out.println("=======数据库写入缓存=====");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;
    }
}
