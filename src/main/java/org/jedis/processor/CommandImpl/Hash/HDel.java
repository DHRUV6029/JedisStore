package org.jedis.processor.CommandImpl.Hash;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

public class HDel extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"HDEL".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'hdel' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Incorrect number of args");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String hashKeyValue = super.getCommandArgs()[0];
        int delCount = 0;
        if(memoryRef.hashValueStorage().containsKey(hashKeyValue)){

            var keyMap = memoryRef.hashValueStorage().get(hashKeyValue);
            String[] fields = super.getCommandArgs();

            for(int i = 1 ; i < fields.length ; i++){
                if(keyMap.containsKey(fields[i])){
                    keyMap.remove(fields[i]);
                    delCount++;

                }
            }
        }
        return delCount;
    }
}
