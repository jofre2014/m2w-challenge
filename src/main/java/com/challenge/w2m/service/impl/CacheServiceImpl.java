package com.challenge.w2m.service.impl;

import com.challenge.w2m.config.cache.CacheName;
import com.challenge.w2m.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Override
    @CacheEvict(value = CacheName.FIND_ALL_CACHE, key = "#id")
    public void cleanFinaAllCacheByKey(Long id) {
        log.info("Clean cache for ID: {}", id);
    }
}
