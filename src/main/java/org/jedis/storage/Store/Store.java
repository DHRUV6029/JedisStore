package org.jedis.storage.Store;

public interface Store<T> {
    void setDataStore(T o);
    T getDataStore();
}
