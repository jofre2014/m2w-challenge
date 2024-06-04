package com.challenge.w2m.listener;

import com.challenge.w2m.record.SpacecraftEventRecord;
import com.challenge.w2m.service.CacheService;
import com.challenge.w2m.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpacecraftEventListener {

    private final CacheService cacheService;

    public SpacecraftEventListener(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    @KafkaListener(topics = "spacecraft-topic")
    public void handleSpacecraftListener(String message){
        log.info("Receive spacecraft event: {}", message);
        SpacecraftEventRecord spacecraftEventRecord = JsonUtils.fromJson(message, SpacecraftEventRecord.class);
        cacheService.cleanFinaAllCacheByKey(spacecraftEventRecord.id());

    }


}
