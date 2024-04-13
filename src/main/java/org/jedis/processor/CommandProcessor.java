package org.jedis.processor;

import org.jedis.seriliazers.RespDeserializer;
import org.jedis.seriliazers.RespSerializer;
import org.jedis.storage.Memory;
import org.jedis.utilities.Helper;
import java.net.SocketException;
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
        if (firstByte != ARRAY) {
            return serializer.serialize(new RuntimeException(INVALID_COMMAND_ERROR));
        }
        try {
            Object[] deserializedArray = deserializer.deserializeArray(command);
            String commandName = deserializedArray[0].toString().toUpperCase();
            CommandFactory commandFactory = new CommandFactory(commandName);
            Command cmd = commandFactory.getCommand();

            Object response = cmd.builder(deserializedArray).process(memoryRef);
            boolean isBulk = Helper.getCommandBulkMode(commandName, response);

            return switch (response) {
                case List<?> _ -> serializer.serialize(Helper.createBulkResponse(response));
                case null -> serializer.serialize(null, isBulk);
                case Integer _ -> serializer.serialize((int) response);
                default -> serializer.serialize(String.valueOf(response), isBulk);
            };

        } catch (RuntimeException e) {
            return serializer.serialize(e);
        }
    }
}
