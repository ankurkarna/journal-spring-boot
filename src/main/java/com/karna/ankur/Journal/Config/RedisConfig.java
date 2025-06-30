package com.karna.ankur.Journal.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Redis configuration class that sets up a RedisTemplate bean.
 * RedisTemplate is the main interface to interact with Redis in Spring.
 */
@Configuration // Marks this class as a configuration source for Spring Boot
public class RedisConfig {

    /**
     * Creates and configures a RedisTemplate bean.
     * This template allows storing and retrieving objects from Redis in JSON format.
     *
     * @param factory The RedisConnectionFactory that provides connections to Redis
     * @return Configured RedisTemplate instance
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory); // Injects connection factory (from Spring Boot config)

        // Serializer to convert Java objects into JSON for Redis
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // ObjectMapper with default typing enabled, required for polymorphic types
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                ObjectMapper.DefaultTyping.EVERYTHING
        );
        serializer.setObjectMapper(mapper);

        // Set key and value serializers for Redis (string keys, JSON values)
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
