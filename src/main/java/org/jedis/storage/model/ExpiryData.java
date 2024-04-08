package org.jedis.storage.model;

public class ExpiryData {
    long setAt;
    long expireAt;

    public ExpiryData(){}

    public ExpiryData(long setAt, long expireAt){
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
