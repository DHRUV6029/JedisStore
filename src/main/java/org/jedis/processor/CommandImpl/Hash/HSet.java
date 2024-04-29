package org.jedis.processor.CommandImpl.Hash;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

import java.util.concurrent.ConcurrentHashMap;

public class HSet extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"HSET".equalsIgnoreCase(super.getCommand()))
            throw new ValidationError("Not correct use of 'hset' command!");
        if(super.getCommandArgs().length <=2){
            throw new ValidationError("Incorrect number of arguments for hset");
        }else{
            if(super.getCommandArgs().length % 2 == 0){
                throw new ValidationError("Incorrect number of arguments for hset");
            }
        }

    }
    @Override
    public Object executeCommand(Memory memoryRef) {
        String hashKeyValue = super.getCommandArgs()[0];
        String[] fieldValues = super.getCommandArgs();
        int cnt = 0;
        //check if Hash is present or not
        if (!memoryRef.hashValueStorage().containsKey(hashKeyValue)) {
            memoryRef.hashValueStorage().put(hashKeyValue, new ConcurrentHashMap<>());
        }

        for (int i = 1; i < fieldValues.length; i += 2) {
            String field = fieldValues[i];
            String value = fieldValues[i + 1];

            memoryRef.hashValueStorage().get(hashKeyValue).put(field, value);
            cnt += 1;
        }
        return cnt;
    }
}



