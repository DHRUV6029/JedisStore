package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

public class Decrement extends Command {
    @Override
    public void ValidationError() throws ValidationError {
        if (!"DECR".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'decr' command!");
        if (super.getCommandArgs().length != 1) throw new ValidationError("Need to pass exactly one key");
    }


    @Override
    public Object executeCommand(Memory memoryRef){
        int decVal = 0;
        String key = super.getCommandArgs()[0];

        if(memoryRef.exists(key)){
            try {
                decVal = Integer.parseInt(memoryRef.get(key).toString());
                memoryRef.set(key, --decVal);
                return decVal;
            }catch (NumberFormatException e){
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        }else{
            // According to this command's documentation, the key is set to 0 first, then decreased by 1
            memoryRef.set(key, decVal);
            memoryRef.set(key, --decVal);
            return decVal;
        }

    }

}
