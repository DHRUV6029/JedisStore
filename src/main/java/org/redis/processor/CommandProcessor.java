package org.redis.processor;

import org.redis.processor.commandImpl.Decrement;
import org.redis.processor.commandImpl.Exists;
import org.redis.processor.commandImpl.Increment;
import org.redis.processor.error.CommandNotFound;
import org.redis.seriliazers.RespDeserializer;
import org.redis.seriliazers.RespSerializer;
import org.redis.storage.Memory;
import java.net.SocketException;
import static org.redis.utilities.Constants.*;

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
                return switch (deserializedArray[0].toString().toUpperCase()){
                    case EXIT -> throw new SocketException(CLOSE_CONNECTION);

                    case EXISTS ->  serializer.serialize((int) new Exists().builder(deserializedArray).
                            executeCommand(memoryRef));

                    case INCR -> serializer.serialize((int) new Increment().builder(deserializedArray).
                            executeCommand(memoryRef));

                    case DECR -> serializer.serialize((int) new Decrement().builder(deserializedArray).
                            executeCommand(memoryRef));

                    default ->
                            throw new CommandNotFound(UNKNOWN_COMMAND_ERROR);
                };
            }catch (RuntimeException e){
                return serializer.serialize(e);
            }
        }


    }
}
