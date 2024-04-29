package org.jedis.processor;

import org.jedis.processor.CommandImpl.Hash.*;
import org.jedis.processor.CommandImpl.Strings.*;
import org.jedis.processor.error.CommandNotFound;

import java.net.SocketException;


import static org.jedis.utilities.Constants.*;

public class CommandFactory {
    private final String commandName;
    public CommandFactory(String commandName){
        this.commandName = commandName;
    }
    public Command getCommand() throws SocketException {
         return (Command) generateCommand(this.commandName);
    }

    public Object generateCommand(String CommandName) throws SocketException {
        return switch (CommandName.toUpperCase()) {
            case EXIT -> throw new SocketException(CLOSE_CONNECTION);
            case PING -> new Ping();
            case ECHO -> new Echo();
            case EXISTS -> new Exists();
            case INCR -> new Increment();
            case INCRBY -> new IncrementBy();
            case DECR -> new Decrement();
            case DECRBY -> new DecrementBy();
            case APPEND -> new Append();
            case SET -> new Set();
            case MSET -> new MSet();
            case GET -> new Get();
            case MGET -> new MGet();
            case LPUSH -> new LPush();
            case RPUSH -> new RPush();
            case FLUSHALL -> new FlushAll();
            case LRANGE -> new LRange();
            case SAVE -> new Save();
            case DELETE -> new Delete();
            case HSET -> new HSet();
            case HGET -> new HGet();
            case HMGET -> new HMGet();
            case HEXISTS -> new HExists();
            case HDEL -> new HDel();
            default -> throw new CommandNotFound(UNKNOWN_COMMAND_ERROR);
        };
    }
}
