package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.processor.error.WrongLiteralTypeError;
import org.jedis.storage.Memory;

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
        if (memoryRef.keyValueStorage().containsKey(key)) {
            try {
                StringBuffer strValue = (StringBuffer) memoryRef.keyValueStorage().get(key);
                memoryRef.keyValueStorage().put(key , strValue.append(value));
                return strValue.length();
            } catch (NumberFormatException e) {
                throw new WrongLiteralTypeError("Not a valid string type");
            }
        } else {
            memoryRef.keyValueStorage().put(key , new StringBuffer()); //According to redis documnentation need to set as empty string first
            memoryRef.keyValueStorage().put(key , ((StringBuffer) memoryRef.keyValueStorage().
                    get(key)).append(value));
            return value.length();

        }
    }
}




