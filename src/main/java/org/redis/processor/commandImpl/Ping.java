package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;


public class Ping extends Command {
    @Override
    public void ValidationError() {
        if (!"PING".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'ping' command!");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        return "PONG";
    }

}

