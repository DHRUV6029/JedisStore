package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;
import org.redis.storage.model.ExpiryData;
import org.redis.utilities.Helper;


public class Get extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"GET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'get' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Incorrect number of args");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        Object val = memoryRef.get(key);
        ExpiryData expiryMetaData = memoryRef.getKeyExpiryData().get(key);

        boolean itHasExpired = (expiryMetaData != null) && Helper.hasExpired(expiryMetaData);

        if (val == null) return null;
        else if (itHasExpired) {
            memoryRef.remove(key);
            memoryRef.getKeyExpiryData().remove(key);
            return null;
        }
        else if (val instanceof String) return val.toString();
        else if (val instanceof Integer) return Integer.parseInt(val.toString());
        else return null;
    }
}
