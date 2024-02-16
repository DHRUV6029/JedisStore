package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LPush extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"LPUSH".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'lpush' command!");
        if (super.getCommandArgs().length < 2) throw new ValidationError("Wrong number of arguments for 'lpush' command");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        String[] args = new String[super.getCommandArgs().length-1];
        for (int i = 1; i < super.getCommandArgs().length; i++) {
            args[i-1] = super.getCommandArgs()[i];
        }

        ArrayList<String> newList;
        if (memoryRef.exists(key)) {
            Object obj = memoryRef.get(key);
            if (obj instanceof ArrayList) {
                List<String> list = ((ArrayList<?>) obj).stream().map(String::valueOf).toList();
                newList = new ArrayList<>(Arrays.asList(args).reversed());
                newList.addAll(list);
                memoryRef.set(key, newList);
            } else {
                throw new WrongLiteralTypeError("Operation against a key holding the wrong kind of value");
            }
        } else {
            newList = new ArrayList<>(Arrays.asList(args).reversed());
            memoryRef.set(key, newList);
        }
        return newList.size();
    }


}
