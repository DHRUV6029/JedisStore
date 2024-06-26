package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;
import org.jedis.utilities.Constants;

import java.util.ArrayList;


public class MGet extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"MGET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'mget' command!");
        if (super.getCommandArgs().length < 1) throw new ValidationError("Incorrect no of arguments");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String[] keys = super.getCommandArgs();
        ArrayList<Object> values =  new ArrayList<>();
        for (String key : keys) {
            var value = new Get().builder(new Object[]{Constants.GET , key}).
                    executeCommand(memoryRef);
            values.add(value);
        }
        return values;
    }
}
