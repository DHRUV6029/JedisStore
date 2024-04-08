package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class Delete extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"DEL".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'del' command!");
        if (super.getCommandArgs().length == 0) throw new ValidationError("Need to pass key(s to delete)");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        int i = 0;
        for (String key: super.getCommandArgs()) {
            Object obj = memoryRef.keyValueStorage().remove(key);
            if (obj != null) ++i;
        }
        return i; //returns the number of keys delete
    }
}