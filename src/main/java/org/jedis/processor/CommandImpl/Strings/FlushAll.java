package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class FlushAll extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"FLUSHALL".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'flushall' command!");
        if (super.getCommandArgs().length != 0) throw new ValidationError("Doesn't need additional args, only run 'flushall'");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        try{
            memoryRef.keyValueStorage().clear();
            memoryRef.keyValueStoreTTLData().clear();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
        }

        return "OK (Note, this doesn't clear any rdb files. After 'flushall', run " +
                "'save' to save current memory state";
    }
}
