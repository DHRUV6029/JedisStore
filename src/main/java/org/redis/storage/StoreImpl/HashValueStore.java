package org.redis.storage.StoreImpl;

import org.redis.storage.Store.Store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashValueStore implements Store<Map<String , ConcurrentHashMap<String , Object>>> {
    private Map<String , ConcurrentHashMap<String , Object>> hashSet;

    public HashValueStore(){
        this.hashSet = new ConcurrentHashMap<>();
    }
    @Override
    public void setDataStore(Map<String , ConcurrentHashMap<String , Object>> hashSet){
        this.hashSet = hashSet;
    }

    @Override
    public Map<String , ConcurrentHashMap<String , Object>> getDataStore(){
        return this.hashSet;
    }

}
