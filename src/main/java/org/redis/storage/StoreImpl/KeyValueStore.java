package org.redis.storage.StoreImpl;

import org.redis.storage.Store.Store;
import org.redis.storage.model.ExpiryData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore implements Store<Map<String , ?>> {
    private Map<String, Object> keyData;
    private Map<String, ExpiryData> keyExpiry;


    public KeyValueStore() {
        this.keyData = new ConcurrentHashMap<>();
        this.keyExpiry = new ConcurrentHashMap<>();
    }

    @Override
    public void setDataStore(Map<String , ?> keyData) {

        if (keyData instanceof Map) {
            if (keyData.values().stream().allMatch(v -> v instanceof Object)) {
                this.keyData = (Map<String, Object>) keyData;
            } else if (keyData.values().stream().allMatch(v -> v instanceof ExpiryData)) {
                this.keyExpiry = (Map<String, ExpiryData>) keyData;
            } else {

                throw new IllegalArgumentException("Invalid data type");
            }

        }
    }

    @Override
    public Map<String , Object> getDataStore(){
        return this.keyData;
    }

    public Map<String , ExpiryData> getExpiryTtlValues(){
        return this.keyExpiry;
    }


}
