package org.redis.storage.model;

public class ExpiryData {
    long setAt;
    long expireAt;

    ExpiryData(){}

    ExpiryData(long setAt , long expireAt){
        this.setAt = setAt;
        this.expireAt = expireAt;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public long getSetAt() {
        return setAt;
    }
}
