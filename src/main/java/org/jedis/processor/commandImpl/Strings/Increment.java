package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.processor.error.WrongLiteralTypeError;
import org.jedis.storage.Memory;

public class Increment extends Command {

    @Override
    public void validation() throws ValidationError{
        if (!"INCR".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'incr' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Need to pass exactly one key");
    }


    @Override
    public Object executeCommand(Memory memoryRef){
        int incVal = 0;
        String key = super.getCommandArgs()[0];

        if(memoryRef.keyValueStorage().containsKey(key)){
            try {
                incVal = Integer.parseInt(memoryRef.keyValueStorage().get(key).toString());
                memoryRef.keyValueStorage().put(key, ++incVal);
                return incVal;
            }catch (NumberFormatException e){
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        }else{
            // According to this command's documentation, the key is set to 0 first, then increased by 1
            memoryRef.keyValueStorage().put(key, incVal);
            memoryRef.keyValueStorage().put(key, ++incVal);
            return incVal;
        }

    }

}
