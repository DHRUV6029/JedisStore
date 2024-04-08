package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;
import org.jedis.storage.model.ExpiryData;
import org.jedis.utilities.Helper;


public class Get extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"GET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'get' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Incorrect number of args");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        Object val = memoryRef.keyValueStorage().get(key);
        ExpiryData expiryMetaData = memoryRef.keyValueStoreTTLData().get(key);

        boolean itHasExpired = (expiryMetaData != null) && Helper.hasExpired(expiryMetaData);

        if (val == null) return null;
        else if (itHasExpired) {
            memoryRef.keyValueStorage().remove(key);
            memoryRef.keyValueStoreTTLData().remove(key);
            return null;
        }
        else if (val instanceof String) return val.toString();
        else if (val instanceof Integer) return Integer.parseInt(val.toString());
        else return null;
    }
}
