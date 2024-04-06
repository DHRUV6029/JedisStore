package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;
import org.redis.utilities.Constants;
import org.redis.utilities.Helper;

import java.util.ArrayList;
import java.util.Arrays;

public class MSet extends Command {
    @Override
    public  void validation() throws ValidationError {
        if (!"MSET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'mset' command!");
        int len = super.getCommandArgs().length;
        if (len < 2) throw new ValidationError("Too few arguments, example: mset name john [nx|xx] [ex|px] timeVal name mary [nx|xx] [ex|px] timeVal");
        if(len >2 && len % 2 == 1) throw new ValidationError("wrong number of arguments for 'mset' command");

    }

    @Override
    public Object executeCommand(Memory memoryRef){
        String[] commandValues = super.getCommandArgs();
        for(int i = 0 ; i < commandValues.length ; i+=2){
            new Set().builder(new Object[]{Constants.SET , commandValues[i],
            commandValues[i+1]}).executeCommand(memoryRef);
        }
        return "OK";
    }
}
