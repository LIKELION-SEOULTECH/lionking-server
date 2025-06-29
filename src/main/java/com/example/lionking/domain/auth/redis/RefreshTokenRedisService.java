package com.example.lionking.domain.auth.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RedisTemplate<String, String> redisTemplate;

    private String getKey(Long userId) {
        return "refresh_token:" + userId;
    }

    // 저장 또는 갱신
    public void save(Long userId, String token, long ttlSeconds) {
        redisTemplate.opsForValue().set(getKey(userId), token, Duration.ofSeconds(ttlSeconds));
    }

    // 조회
    public Optional<String> getToken(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getKey(userId)));
    }

    // 삭제
    public void delete(Long userId) {
        redisTemplate.delete(getKey(userId));
    }

}