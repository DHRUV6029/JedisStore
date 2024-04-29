package org.jedis.utilities;
import org.jedis.processor.error.CommandNotFound;
import org.jedis.processor.error.RedisServerError;
import org.jedis.storage.Model.ExpiryData;
import static org.jedis.utilities.Constants.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;


public class Helper {
    public static final Predicate<String> IsNumber = (numStr) -> {
        try {
            Integer.parseInt(numStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    public static final Predicate<String> isExPx = (opt) -> {
        if ("EX".equalsIgnoreCase(opt) || "PX".equalsIgnoreCase(opt)) return true;
        if ("EXAT".equalsIgnoreCase(opt) || "PXAT".equalsIgnoreCase(opt))
            throw new RedisServerError("EXAT & PXAT are not supported yet");
        else return false;
    };

    public static final Predicate<String> isNxXx = (opt) -> "NX".equalsIgnoreCase(opt) || "XX".equalsIgnoreCase(opt);

    public static boolean hasExpired(ExpiryData expiryMetaData) {
        return new Date().getTime() - expiryMetaData.getSetAt() > expiryMetaData.getExpireAt();
    }
    public static boolean getCommandBulkMode(String commandName , Object response){
        Object mode = getBulkMode(commandName , response);
        return mode instanceof Boolean ? (Boolean) mode : false;
    }

    public static Object getBulkMode(String commandName , Object response){
        return switch (commandName.toUpperCase()){
            case EXIT, PING, MSET, FLUSHALL, SAVE, DELETE ,HSET , HDEL-> false;
            case ECHO -> true;
            case EXISTS , INCR ,INCRBY , DECR , DECRBY , APPEND, MGET, LPUSH, RPUSH, LRANGE, HMGET, HEXISTS -> null;
            case SET -> response == null;
            case GET, HGET ->{
                if(response == null){
                    yield true;
                } else if (response instanceof Integer) {
                    yield null;
                }
                else{
                    yield true;
                }
            }
            default ->
                throw new CommandNotFound("UNKNOWN_COMMAND_ERROR");
        };
    }

    public static Object[] createBulkResponse(Object response) {
        ArrayList<Object> resp = (ArrayList<Object>) response;
        Object[] arr = new Object[resp.size()];
        for(int i = 0 ; i < arr.length ; i++){
            arr[i] = resp.get(i);

        }
        return arr;
    }

}
