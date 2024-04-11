package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;


public class Ping extends Command {
    @Override
    public void validation() {
        if (!"PING".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'ping' command!");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        return "PONG";
    }

}

