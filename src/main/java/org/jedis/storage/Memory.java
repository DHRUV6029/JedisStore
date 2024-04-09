package org.jedis.storage;

import org.jedis.storage.StoreImpl.HashValueStore;
import org.jedis.storage.StoreImpl.KeyValueStore;
import org.jedis.storage.Model.ExpiryData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memory {
    private KeyValueStore keyValueStore;
    private HashValueStore hashValueStore;
    public Memory(){}
    public Memory(KeyValueStore keyValueStore,
                  HashValueStore hashValueStore){
        this.keyValueStore = keyValueStore;
        this.hashValueStore = hashValueStore;
    }


   public void restoreKeyValueStoreFromDisk(Map<String, Object> keyValues){
        keyValueStore.setDataStore(keyValues);
   }
    public void restoreKeyValueStoreTTLFromDisk(Map<String, ExpiryData> keyValues){
        keyValueStore.setDataStore(keyValues);
    }
    public Map<String, Object> keyValueStorage(){
        return keyValueStore.getDataStore();
    }

    public Map<String, ExpiryData> keyValueStoreTTLData(){
        return keyValueStore.getExpiryTtlValues();
    }

    public Map<String , ConcurrentHashMap<String , Object>> hashValueStorage(){
        return hashValueStore.getDataStore();
    }

}
