package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;
import org.redis.storage.model.ExpiryData;
import org.redis.utilities.Helper;
import java.util.ArrayList;


public class MGet extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"MGET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'mget' command!");
        if (super.getCommandArgs().length < 1) throw new ValidationError("Incorrect no of arguments");
    }


    @Override   //Todo add expiration time check .//This operation nvere
    public Object executeCommand(Memory memoryRef) {
        String[] keys = super.getCommandArgs();
        ArrayList<Object> values =  new ArrayList<>();

        for (String key : keys) {
            if (memoryRef.exists(key)) {
                var keyValue = memoryRef.get(key);
                ExpiryData expiryMetaData = memoryRef.getKeyExpiryData().get(key);
                boolean itHasExpired = (expiryMetaData != null) && Helper.hasExpired(expiryMetaData);
                if (itHasExpired) {
                    memoryRef.remove(key);
                    memoryRef.getKeyExpiryData().remove(key);
                    values.add(null);
                } else if((keyValue instanceof String)) {

                    values.add(keyValue);
                }else{
                    values.add(null);
                }
            } else {
                values.add(null);
            }
        }
        return values;
    }


}
