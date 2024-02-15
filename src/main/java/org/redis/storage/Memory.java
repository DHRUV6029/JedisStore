package org.redis.storage;

import org.redis.storage.model.ExpiryData;

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


}
