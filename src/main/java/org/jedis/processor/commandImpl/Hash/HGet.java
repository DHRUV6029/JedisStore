package org.jedis.processor.commandImpl.Hash;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class HGet extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"HGET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'hget' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Incorrect number of args");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String hashKeyValue = super.getCommandArgs()[0];
        String field = super.getCommandArgs()[1];
        Object value = null;

        if(memoryRef.hashValueStorage().containsKey(hashKeyValue)){
            var keyMap = memoryRef.hashValueStorage().get(hashKeyValue);
            value = keyMap.getOrDefault(field , null);
        }else{
            return null;
        }

        if(value instanceof String){
            return value.toString();
        }
        else if(value instanceof Integer){
            return Integer.parseInt(value.toString());
        }else{
            return  null;
        }
    }

}
