package com.spring.profiles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spring.profiles.entity.User;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory connectionFactory){
		 RedisTemplate<String, User> template = new RedisTemplate<>();
         template.setConnectionFactory(connectionFactory);
         
         template.setKeySerializer(new StringRedisSerializer());
         template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
         return template;
	}
}
