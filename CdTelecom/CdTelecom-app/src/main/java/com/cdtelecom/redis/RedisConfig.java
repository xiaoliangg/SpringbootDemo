package com.cdtelecom.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private String port;
//    @Value("${redis.password}")
//    private String password;
//    @Value("${redis.timeout}")
//    private int timeout;
//    @Value("${redis.maxIdle}")
//    private int maxIdle;
//    @Value("${redis.maxTotal}")
//    private int maxTotal;
//    @Value("${redis.maxWaitMillis}")
//    private int maxWaitMillis;

    //自动注入redis配置属性文件
    @Autowired
    private RedisProperties properties;
    /**
     * 生成key的策略
     * @return
     */
    @Bean
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
        config.setMaxTotal(properties.getJedis().getPool().getMaxActive());
        config.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
        logger.info("连接redis");
        return config;
    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setDatabase(properties.getDatabase());
        config.setPassword(properties.getPassword());

        JedisConnectionFactory factory = new JedisConnectionFactory(config);
        factory.setPoolConfig(getRedisConfig());
        logger.info("连接redis读取配置文件生成工厂");
        return factory;
    }
    //redisTemplate
    @Bean
    public RedisTemplate<?,?> getRedisTemplate(){
        JedisConnectionFactory factory = getConnectionFactory();
        RedisTemplate<?, ?> template = new StringRedisTemplate(factory);

        return template;
    }

}



