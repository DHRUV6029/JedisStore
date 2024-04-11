package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class Exists extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"EXISTS".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'exists' command!");
        if (super.getCommandArgs().length == 0) throw new ValidationError("Need to pass key(s)");
    }

    @Override
    public  Object executeCommand(Memory memoryRef) {
        int i = 0;
        for (String key: super.getCommandArgs()) {
            if (memoryRef.keyValueStorage().containsKey(key)) ++i;
        }
        return i;
    }
}
