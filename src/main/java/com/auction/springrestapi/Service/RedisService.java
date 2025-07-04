package com.auction.springrestapi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper(); // initialized once
    }

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o == null) {
                return null;
            }

            if (o instanceof String) {
                return mapper.readValue((String) o, entityClass);
            } else {
                // already a deserialized object
                return entityClass.cast(o);
            }
        } catch (Exception e) {
            log.error("Exception occurred during Redis GET: {}", e.getMessage(), e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttlInSeconds) {
        try {
            String jsonValue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttlInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception occurred during Redis SET: {}", e.getMessage(), e);
        }
    }
}
