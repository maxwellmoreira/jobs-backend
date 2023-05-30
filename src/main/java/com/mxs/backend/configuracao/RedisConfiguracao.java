package com.mxs.backend.configuracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

@Configuration
public class RedisConfiguracao {

    @Value("#{${jedis.pool.config}}")
    private Map<String, String> jedisPoolConfigMap;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration(jedisPoolConfigMap.get("IP"), Integer.parseInt(jedisPoolConfigMap.get("PORT")));
        redisStandaloneConfiguration.setPassword(jedisPoolConfigMap.get("PASSWORD"));
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jedisConnectionFactory.getPoolConfig().setMaxTotal(Integer.parseInt(jedisPoolConfigMap.get("MAX_TOTAL")));
        jedisConnectionFactory.getPoolConfig().setMaxIdle(Integer.parseInt(jedisPoolConfigMap.get("MAX_IDLE")));
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
