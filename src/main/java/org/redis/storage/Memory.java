package org.redis.storage;
import org.redis.storage.HashValue.HashValueStore;
import org.redis.storage.model.ExpiryData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memory {
    private org.redis.storage.KeyValueStore keyValueStore;
    private HashValueStore hashValueStore;
    public Memory(){}
    public Memory(org.redis.storage.KeyValueStore keyValueStore,
                  HashValueStore hashValueStore){
        this.keyValueStore = keyValueStore;
        this.hashValueStore = hashValueStore;
    }


   public void restoreKeyValueStoreFromDisk(Map<String, Object> keyValues){
        keyValueStore.setKeyValueStore(keyValues);
   }
    public void restoreKeyValueStoreTTLFromDisk(Map<String, ExpiryData> keyValues){
        keyValueStore.setKeyExpiry(keyValues);
    }
    public Map<String, Object> keyValueStorage(){
        return keyValueStore.getKeyValueStore();
    }

    public Map<String, ExpiryData> keyValueStoreTTLData(){
        return keyValueStore.getKeyExpiry();
    }

    public Map<String , ConcurrentHashMap<String , Object>> hashValueStorage(){
        return hashValueStore.getHashValueStore();
    }

}
