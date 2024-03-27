package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

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
        if (memoryRef.exists(key)) {
            try {
                decVal = Integer.parseInt(memoryRef.get(key).toString());
                memoryRef.set(key, (decVal - decBy));
                return decVal - decBy;
            } catch (NumberFormatException e) {
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        } else {
            // According to this command's documentation, the key is set to 0 first, then decreased by 1
            memoryRef.set(key, decVal);
            memoryRef.set(key, decBy);
            return decBy;
        }

    }
}