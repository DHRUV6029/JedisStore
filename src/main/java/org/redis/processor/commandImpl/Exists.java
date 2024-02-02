package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;

public class Exists extends Command {
    @Override
    public void ValidationError() throws ValidationError {
        if (!"EXISTS".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'exists' command!");
        if (super.getCommandArgs().length == 0) throw new ValidationError("Need to pass key(s)");
    }

    @Override
    public  Object executeCommand(Memory memoryRef) {
        int i = 0;
        for (String key: super.getCommandArgs()) {
            if (memoryRef.exists(key)) ++i;
        }
        return i;
    }
}
