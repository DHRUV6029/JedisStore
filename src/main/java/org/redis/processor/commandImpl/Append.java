package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

public class Append extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"APPEND".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'append' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Need to pass exactly two key");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        String value = super.getCommandArgs()[1];
        if (memoryRef.exists(key)) {
            try {
                StringBuffer strValue = (StringBuffer) memoryRef.get(key);
                memoryRef.set(key , strValue.append(value));
                return strValue.length();
            } catch (NumberFormatException e) {
                throw new WrongLiteralTypeError("Not a valid string type");
            }
        } else {
            memoryRef.set(key , new StringBuffer()); //According to redis documnentation need to set as empty string first
            memoryRef.set(key , ((StringBuffer) memoryRef.get(key)).append(value));
            return value.length();

        }
    }
}




