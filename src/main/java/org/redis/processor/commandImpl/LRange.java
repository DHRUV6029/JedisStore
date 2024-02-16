package org.redis.processor.commandImpl;

import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.processor.error.WrongLiteralTypeError;
import org.redis.storage.Memory;

import java.util.ArrayList;
import java.util.List;

public class LRange extends Command {
    private int startIdx = 0;
    private int endIdx = 0;

    @Override
    public void validation() throws ValidationError {
        if (!"LRANGE".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'lrange' command!");
        if (super.getCommandArgs().length != 3) throw new ValidationError("Wrong number of arguments for 'lrange' command");
        try {
            this.startIdx = Integer.parseInt(super.getCommandArgs()[1]);
            this.endIdx = Integer.parseInt(super.getCommandArgs()[2]);
        } catch (NumberFormatException e) {
            throw new ValidationError("'lrange' works on a startIdx and endIdx which are numbers");
        }
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];

        if (memoryRef.exists(key)) {
            Object obj = memoryRef.get(key);
            if (obj instanceof ArrayList) {
                List<String> list = ((ArrayList<?>) obj).stream().map(String::valueOf).toList();
                ArrayList<String> aList = new ArrayList<>(list);
                if (this.startIdx > aList.size() || this.startIdx > this.endIdx) this.startIdx = 0;
                if (this.endIdx < this.startIdx || this.endIdx > aList.size()) this.endIdx = aList.size();
                return new ArrayList<>(aList.subList(this.startIdx, this.endIdx));
            }
            return new WrongLiteralTypeError("Key is not mapped to a list");
        } else {
            return new ArrayList<>();
        }
    }

}
