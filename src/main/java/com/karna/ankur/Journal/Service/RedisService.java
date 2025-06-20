package com.karna.ankur.Journal.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null && entityClass.isInstance(value)) {
                return entityClass.cast(value);
            }
            return null;
        } catch (Exception e) {
            log.error("Exception in Redis get operation", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            redisTemplate.opsForValue().set(key, o, ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception in Redis set operation", e);
        }
    }


//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//
//    public <T> T get(String key, Class<T> entityClass) {
//        try {
//            Object o = redisTemplate.opsForValue().get(key);
//            ObjectMapper mapper = new ObjectMapper();
////            assert o != null;
//            return mapper.readValue(o.toString(), entityClass);
//        } catch (Exception e) {
//            log.error("Exception ", e);
//            return null;
//        }
//    }
//
//    public void set(String key, Object o, Long ttl) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String value = mapper.writeValueAsString(o);
//            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
//
//        } catch (Exception e) {
//            log.error("Exception ", e);
//
//        }
//    }
}
