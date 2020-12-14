package com.wenance.challenge.configuration

import com.wenance.challenge.model.PriceStatistics
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class RedisConfig {

    @Bean
    fun jedisConnectionFactory():JedisConnectionFactory {
        return JedisConnectionFactory()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, PriceStatistics> {
        var template = RedisTemplate<String,PriceStatistics>();
        template .setConnectionFactory(jedisConnectionFactory())
        //template.keySerializer = StringRedisSerializer()
        return template;
    }
}