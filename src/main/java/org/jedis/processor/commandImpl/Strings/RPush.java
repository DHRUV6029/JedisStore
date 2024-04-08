package org.jedis.processor.commandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.processor.error.WrongLiteralTypeError;
import org.jedis.storage.Memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RPush extends Command {

    @Override
    public void validation() throws ValidationError {
        if (!"RPUSH".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'rpush' command!");
        if (super.getCommandArgs().length < 2) throw new ValidationError("Wrong number of arguments for 'rpush' command");
    }


    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        String[] args = new String[super.getCommandArgs().length-1];
        for (int i = 1; i < super.getCommandArgs().length; i++) {
            args[i-1] = super.getCommandArgs()[i];
        }
        ArrayList<String> newList;
        if (memoryRef.keyValueStorage().containsKey(key)) {
            Object obj = memoryRef.keyValueStorage().get(key);
            if (obj instanceof ArrayList) {
                List<String> list = ((ArrayList<?>) obj).stream().map(String::valueOf).toList();
                newList = new ArrayList<>(list);
                newList.addAll(Arrays.asList(args));
                memoryRef.keyValueStorage().put(key, newList);
            } else {
                throw new WrongLiteralTypeError("Operation against a key holding the wrong kind of value");
            }
        } else {
            newList = new ArrayList<>(Arrays.asList(args));
            memoryRef.keyValueStorage().put(key, newList);
        }
        return newList.size();
    }
}
