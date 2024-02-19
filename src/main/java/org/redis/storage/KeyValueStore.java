package org.redis.storage;
import org.redis.storage.model.ExpiryData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {
    private Map<String, Object> keyData;
    private Map<String, ExpiryData> keyExpiry;

    public KeyValueStore() {
        this.keyData = new ConcurrentHashMap<>();
        this.keyExpiry = new ConcurrentHashMap<>();
    }

    public KeyValueStore(Map<String, Object> keyData, Map<String, ExpiryData> keyExpiry) {
        this.keyData = keyData;
        this.keyExpiry = keyExpiry;
    }

    public Map<String , Object> getKeyValueStore(){
        return keyData;
    }

    public void setKeyValueStore(Map<String , Object> keyData){
        this.keyData = keyData;
    }

    public Map<String, ExpiryData> getKeyExpiry() {
        return keyExpiry;
    }

    public void setKeyExpiry(Map<String, ExpiryData> keyExpiry) {
        this.keyExpiry = keyExpiry;
    }
}
