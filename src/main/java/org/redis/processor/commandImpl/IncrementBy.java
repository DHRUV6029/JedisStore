package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

public class IncrementBy extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"INCRBY".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'incrby' command!");
        if (super.getCommandArgs().length != 2) throw new ValidationError("Need to pass exactly two key");
    }

    @Override
    public Object executeCommand(Memory memoryRef){
        int incVal = 0;
        int incBy = 0;
        String key = super.getCommandArgs()[0];
        try {
            incBy = Integer.parseInt(super.getCommandArgs()[1]);
        }catch (NumberFormatException e){throw  new WrongLiteralTypeError("Not a valid number type");}

        if(memoryRef.exists(key)){
            try {
                incVal = Integer.parseInt(memoryRef.get(key).toString());
                memoryRef.set(key, (incVal + incBy));
                return (incVal + incBy);
            }catch (NumberFormatException e){
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        }else{
            // According to this command's documentation, the key is set to 0 first, then increased by 1
            memoryRef.set(key, incVal);
            memoryRef.set(key, incBy);
            return incBy;
        }

    }



}
