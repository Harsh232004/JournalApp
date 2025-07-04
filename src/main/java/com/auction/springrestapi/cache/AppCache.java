package com.auction.springrestapi.cache;

import com.auction.springrestapi.Repo.AppCacheRepo;
import com.auction.springrestapi.DTO.weatherCache; // Assuming your entity name
import com.auction.springrestapi.enums.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    private final AppCacheRepo appCacheRepo;

    @Autowired
    public AppCache(AppCacheRepo appCacheRepo){
        this.appCacheRepo = appCacheRepo;
    }

    private Map<String, String> appCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        List<weatherCache> allConfigs = appCacheRepo.findAll();
        for (weatherCache config : allConfigs) {
            appCache.put(config.getKey(), config.getValue());
        }
    }

    public String getValue(Keys key) {
        return appCache.get(key.getKey());
    }
}