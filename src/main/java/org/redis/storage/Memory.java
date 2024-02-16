package org.redis.storage;

import org.redis.storage.model.ExpiryData;

import java.util.Date;
import java.util.Map;

public class Memory {
    private KeyValueStore keyValueStore;
    public Memory(){}
    public Memory(KeyValueStore keyValueStore){
        this.keyValueStore = keyValueStore;
    }
    public boolean exists(String key) {
        return keyValueStore.getKeyValueStore().containsKey(key);
    }

    public Object get(String key) {
        return keyValueStore.getKeyValueStore().get(key);
    }

    public boolean remove(String key) {
        if (exists(key)) {
            keyValueStore.getKeyValueStore().remove(key);
            return true;
        }
        return false;
    }

    public void set(String key, Object value) {
        keyValueStore.getKeyValueStore().put(key , value);
    }

    public Map<String, ExpiryData> getKeyExpiryData(){
        return keyValueStore.getKeyExpiry();
    }

    public void setExpiryData(String key , long timeout){
        keyValueStore.getKeyExpiry().put(key , new ExpiryData(new Date().getTime() , timeout));

    }


}
