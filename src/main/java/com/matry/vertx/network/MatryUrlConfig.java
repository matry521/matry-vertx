package com.matry.vertx.network;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatryUrlConfig {
    private ConcurrentHashMap<String, String> eventCache = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Class> eventTypeCache = new ConcurrentHashMap<>();

    public void putEventName(String path, String eventName) {
        eventCache.put(path, eventName);
    }

    public void putEvent(String path, Class type) {
        eventTypeCache.put(path, type);
    }


    public String getEventName(String path) {
        return eventCache.get(path);
    }

    public Class getEvent(String path) {
        return eventTypeCache.get(path);
    }
}
