package org.jedis.processor.CommandImpl.Hash;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class HExists extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"HEXISTS".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'hexists' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Incorrect number of args for required 2");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String hashKeyValue = super.getCommandArgs()[0];
        String field = super.getCommandArgs()[1];
        Object value = 0;
        if(memoryRef.hashValueStorage().containsKey(hashKeyValue)){
            var keyMap = memoryRef.hashValueStorage().get(hashKeyValue);

            if (keyMap.containsKey(field)) {
                value = 1;
            }
        }
        return value;
    }

}
