package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

public class Increment extends Command {

    @Override
    public void ValidationError() throws ValidationError{
        if (!"INCR".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'incr' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Need to pass exactly one key");
    }


    @Override
    public Object executeCommand(Memory memoryRef){
        int incVal = 0;
        String key = super.getCommandArgs()[0];

        if(memoryRef.exists(key)){
            try {
                incVal = Integer.parseInt(memoryRef.get(key).toString());
                memoryRef.set(key, ++incVal);
                return incVal;
            }catch (NumberFormatException e){
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        }else{
            // According to this command's documentation, the key is set to 0 first, then increased by 1
            memoryRef.set(key, incVal);
            memoryRef.set(key, ++incVal);
            return incVal;
        }

    }

}
