package com.neuedu.redis;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class RedisProperties {

    //
    @Value("${redis.max.total}")
    private int moxTotal;
    //最小空闲数
    @Value("${redis.max.idle}")
    private int maxIdle;
    //最大空闲数
    @Value("${redis.min.idle}")
    private int minIdle;
    //IP
    @Value("${redis.ip}")
    private String redisIP;
    //端口
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.test.borrow}")
    private boolean testBorrow;
    @Value("${redis.test.return}")
    private boolean testReturn;
    @Value("${redis.password}")
    private String redisPassword;
}
