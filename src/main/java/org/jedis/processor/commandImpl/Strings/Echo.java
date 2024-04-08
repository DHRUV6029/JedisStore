package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class Echo extends Command {
    @Override
    public void validation() {
        if (!"ECHO".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'echo' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Too many arguments, for multiple tokens wrap them in double quotes");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        return super.getCommandArgs()[0];
    }
}
