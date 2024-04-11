package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.processor.error.WrongLiteralTypeError;
import org.jedis.storage.Memory;

public class DecrementBy extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"DECRBY".equalsIgnoreCase(super.getCommand()))
            throw new ValidationError("Not correct use of 'decrby' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Need to pass exactly two key");
    }


    @Override
    public Object executeCommand(Memory memoryRef) {
        int decVal = 0;
        int decBy = 0;
        String key = super.getCommandArgs()[0];
        try {
            decBy = Integer.parseInt(super.getCommandArgs()[1]);
        } catch (NumberFormatException e) {
            throw new WrongLiteralTypeError("Not a valid number type");
        }
        if (memoryRef.keyValueStorage().containsKey(key)) {
            try {
                decVal = Integer.parseInt(memoryRef.keyValueStorage().get(key).toString());
                memoryRef.keyValueStorage().put(key, (decVal - decBy));
                return decVal - decBy;
            } catch (NumberFormatException e) {
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        } else {
            //According to this command's documentation, the key is set to 0 first, then decreased by 1
            memoryRef.keyValueStorage().put(key, decVal);
            memoryRef.keyValueStorage().put(key, decBy);
            return decBy;
        }

    }
}