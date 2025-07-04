package com.auction.springrestapi.Scheduler;

import com.auction.springrestapi.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
