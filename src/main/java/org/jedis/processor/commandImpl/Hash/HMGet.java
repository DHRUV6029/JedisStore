package org.jedis.processor.commandImpl.Hash;

import org.jedis.processor.Command;
import org.jedis.processor.commandImpl.Strings.Get;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;
import org.jedis.utilities.Constants;

import java.util.ArrayList;

public class HMGet extends Command {
    public void validation() throws ValidationError {
        if (!"HMGET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'hmget' command!");
        if (super.getCommandArgs().length < 2) throw new ValidationError("Incorrect number of args");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String hashKeyValue = super.getCommandArgs()[0];
        String[] fields = super.getCommandArgs();
        ArrayList<Object> values =  new ArrayList<>();
        for (int i = 1 ; i < fields.length ; i++) {
            var value = new HGet().builder(new Object[]{Constants.HMGET , hashKeyValue , fields[i]}).
                    executeCommand(memoryRef);
            values.add(value);
        }
        return values;
    }

}
