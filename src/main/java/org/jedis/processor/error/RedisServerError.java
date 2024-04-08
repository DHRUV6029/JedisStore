package org.jedis.processor.error;

public class RedisServerError extends  RuntimeException{
    public RedisServerError(String message){
        super(STR."(REDIS-ERROR) \{message}");
    }

}
