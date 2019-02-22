package com.neuedu.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/*
* 日志服务 切面类
*
* */
/*@Component
@Aspect*/
public class LogAspect {

    //定义连接点，切入点
    @Pointcut("execution(public * com.neuedu.service.impl.IUserSerbiceImpl.*(..))")
    public void pointcut(){

    }
    //通知:前置，后置，环绕，异常，返回值
    //声明前置通知
    @Before("pointcut()")
    public void before(){
        System.out.println("==========before=====");
    }
    @After("pointcut()")
        public void after(){
        System.out.println("==========after=====");
    }
    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("==========afterThrowing=====");
    }
    @AfterReturning("pointcut()")
    public void AfterReturning(){
        System.out.println("==========AfterReturning=====");
    }

    @Around("pointcut()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint)
    {
        Object o =null;
        try {
            System.out.println("环绕前");
            //执行切入点匹配的方法
            o = proceedingJoinPoint.proceed();
            System.out.println("环绕后");
        } catch (Throwable throwable) {

            throwable.printStackTrace();
            System.out.println("throwing");
        }
        System.out.println("AFFRTRETURNING");
        return  o;
    }
}
