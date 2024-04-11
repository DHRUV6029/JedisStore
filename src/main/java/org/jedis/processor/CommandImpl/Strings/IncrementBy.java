package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.processor.error.WrongLiteralTypeError;
import org.jedis.storage.Memory;

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

        if(memoryRef.keyValueStorage().containsKey(key)){
            try {
                incVal = Integer.parseInt(memoryRef.keyValueStorage().get(key).toString());
                memoryRef.keyValueStorage().put(key, (incVal + incBy));
                return (incVal + incBy);
            }catch (NumberFormatException e){
                throw new WrongLiteralTypeError("Not a valid number type");
            }
        }else{
            // According to this command's documentation, the key is set to 0 first, then increased by 1
            memoryRef.keyValueStorage().put(key, incVal);
            memoryRef.keyValueStorage().put(key, incBy);
            return incBy;
        }

    }



}
