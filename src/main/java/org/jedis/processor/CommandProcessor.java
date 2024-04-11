package org.jedis.processor;

import org.jedis.processor.CommandImpl.Hash.*;
import org.jedis.processor.CommandImpl.Strings.*;
import org.jedis.processor.error.CommandNotFound;
import org.jedis.seriliazers.RespDeserializer;
import org.jedis.seriliazers.RespSerializer;
import org.jedis.storage.Memory;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static org.jedis.utilities.Constants.*;



public class CommandProcessor {
    private final RespSerializer serializer;
    private  final RespDeserializer deserializer;
    private final Memory memoryRef;
    public CommandProcessor(RespSerializer respSerializer , RespDeserializer respDeserializer ,
                            Memory memory){
        this.serializer = respSerializer;
        this.deserializer = respDeserializer;
        this.memoryRef = memory;

    }
    public String execute(String command) throws SocketException {
        char firstByte = command.charAt(0);
        if(firstByte != ARRAY){
            return serializer.serialize(new RuntimeException(INVALID_COMMAND_ERROR));
        }else{
            try{
                Object[] deserializedArray = deserializer.deserializeArray(command);
                return switch (deserializedArray[0].toString().toUpperCase()) {
                    case EXIT -> throw new SocketException(CLOSE_CONNECTION);

                    case PING -> serializer.serialize(String.valueOf(new Ping().builder(deserializedArray).
                            process(null)), false);

                    case ECHO -> serializer.serialize(String.valueOf(new Echo().builder(deserializedArray).
                            process(null)), true);

                    case EXISTS -> serializer.serialize((int) new Exists().builder(deserializedArray).
                            process(memoryRef));

                    case INCR -> serializer.serialize((int) new Increment().builder(deserializedArray).
                            process(memoryRef));

                    case INCRBY -> serializer.serialize((int) new IncrementBy().builder(deserializedArray).
                            process(memoryRef));

                    case DECR -> serializer.serialize((int) new Decrement().builder(deserializedArray).
                            process(memoryRef));

                    case DECRBY -> serializer.serialize((int) new DecrementBy().builder(deserializedArray).
                            process(memoryRef));

                    case APPEND -> serializer.serialize((int) new Append().builder(deserializedArray).
                            process(memoryRef));

                    case SET -> {
                        Object resp = new Set().builder(deserializedArray).process(memoryRef);
                        if (resp == null) yield serializer.serialize(null, true);
                        else yield serializer.serialize(String.valueOf(resp), false);
                    }

                    case MSET -> {
                        Object resp = new MSet().builder(deserializedArray).process(memoryRef);
                        yield serializer.serialize(String.valueOf(resp), false);

                    }

                    case GET -> {
                        Object resp = new Get().builder(deserializedArray).process(memoryRef);
                        if (resp == null) yield serializer.serialize(null, true);
                        if (resp instanceof Integer) yield serializer.serialize((int) resp);
                        else yield serializer.serialize(String.valueOf(resp), true);
                    }

                    case MGET -> {

                        ArrayList<Object> obj = (ArrayList<Object>) new MGet().builder(deserializedArray).process(memoryRef);
                        Object[] arr = new Object[obj.size()];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = obj.get(i);
                        }
                        yield serializer.serialize(arr);
                    }

                    case LPUSH -> serializer.serialize((int) new LPush().builder(deserializedArray).
                            process(memoryRef));

                    case RPUSH -> serializer.serialize((int) new RPush().builder(deserializedArray).
                            process(memoryRef));

                    case FLUSHALL -> serializer.serialize(String.valueOf(new FlushAll().builder(deserializedArray).
                            process(memoryRef)), false);

                    case LRANGE -> {
                        Object obj = new LRange().builder(deserializedArray).process(memoryRef);
                        List<String> list = ((ArrayList<?>) obj).stream().map(String::valueOf).toList();
                        String[] arr = new String[list.size()];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = list.get(i);
                        }
                        yield serializer.serialize(arr);
                    }

                    case SAVE ->
                            serializer.serialize(String.valueOf(new Save().builder(deserializedArray).process(memoryRef)), false);
                    case DELETE ->
                            serializer.serialize(String.valueOf(new Delete().builder(deserializedArray).process(memoryRef)), false);

                    //Hash value command
                    case HSET ->
                            serializer.serialize(String.valueOf(new HSet().builder(deserializedArray).process(memoryRef)), false);
                    case HGET -> {
                        Object resp = new HGet().builder(deserializedArray).process(memoryRef);
                        if (resp == null) yield serializer.serialize(null, true);
                        if (resp instanceof Integer) yield serializer.serialize((int) resp);
                        else yield serializer.serialize(String.valueOf(resp), true);
                    }

                    case HMGET -> {
                        ArrayList<Object> obj = (ArrayList<Object>) new HMGet().builder(deserializedArray).process(memoryRef);
                        Object[] arr = new Object[obj.size()];
                        for (int i = 0; i < arr.length; i++) {
                            arr[i] = obj.get(i);
                        }
                        yield serializer.serialize(arr);
                    }

                    case HEXISTS -> serializer.serialize((int) new HExists().builder(deserializedArray).
                            process(memoryRef));



                    default ->
                            throw new CommandNotFound(UNKNOWN_COMMAND_ERROR);
                };
            }catch (RuntimeException e){
                return serializer.serialize(e);
            }
        }

    }
}
