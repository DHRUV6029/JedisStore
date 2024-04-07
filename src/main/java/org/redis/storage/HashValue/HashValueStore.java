package org.redis.storage.HashValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashValueStore {
    private Map<String , ConcurrentHashMap<String , Object>> hashSet;

    public HashValueStore(){
        this.hashSet = new ConcurrentHashMap<>();
    }

    public Map<String , ConcurrentHashMap<String , Object>> getHashValueStore(){
        return hashSet;
    }

    public void setHashValueStore( Map<String , ConcurrentHashMap<String , Object>> hashValueStore){
        this.hashSet = hashValueStore;
    }

}
