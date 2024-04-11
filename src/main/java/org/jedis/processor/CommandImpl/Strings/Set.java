package org.jedis.processor.CommandImpl.Strings;

import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;
import org.jedis.storage.Model.ExpiryData;
import org.jedis.utilities.Helper;

import java.util.Date;

public class Set extends Command {
    public static final long REALLY_BIG_TIME_VAL = 100_000_000_000L;
    private String optNxXx = "NONE";
    private String optExPx = "NONE";
    private long optTimeVal = REALLY_BIG_TIME_VAL;



    @Override
    public  void validation() throws ValidationError {
        if (!"SET".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'set' command!");
        int len = super.getCommandArgs().length;
        if (len < 2) throw new ValidationError("Too few arguments, example: set name john [nx|xx] [ex|px] timeVal"); // set name john
        if (len > 5)
            throw new ValidationError("Too many arguments, example: set name john [nx|xx] [ex|px] timeVal"); // set name john [nx|xx] [ex|px] timeVal

        // 0 is key
        // 1 is val
        // hence starting from 2
        for (int i = 2; i < super.getCommandArgs().length; i++) {
            if (Helper.isNxXx.test(super.getCommandArgs()[i]) || Helper.isExPx.test(super.getCommandArgs()[i])) {
                if (Helper.isNxXx.test(super.getCommandArgs()[i])) {
                    this.optNxXx = super.getCommandArgs()[i];
                }
                if (Helper.isExPx.test(super.getCommandArgs()[i])) {
                    this.optExPx = super.getCommandArgs()[i];

                    try {
                        this.optTimeVal = Long.parseLong(super.getCommandArgs()[i + 1]);
                        if (this.optTimeVal <= 0) {
                            throw new ValidationError(STR."Time is in past, can't use with \{super.getCommandArgs()[i]}");
                        }
                    } catch (ArrayIndexOutOfBoundsException a) {
                        throw new ValidationError("Hmm.. seems like a syntax error");
                    } catch (NumberFormatException n) {
                        throw new ValidationError("Time value is not a number");
                    }
                }
            } else if (Helper.IsNumber.test(super.getCommandArgs()[i])) {
                if (!Helper.isExPx.test(super.getCommandArgs()[i - 1])) { // because a number can only be preceded by ex, px
                    throw new ValidationError("Misplaced number value");
                }
            } else {
                throw new ValidationError("Hmm.. seems like a syntax error");
            }
        }
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String key = super.getCommandArgs()[0];
        String val = super.getCommandArgs()[1];

        long timeout = switch (this.optExPx) {
            case "EX", "ex" -> this.optTimeVal * 1000;
            case "PX", "px" -> this.optTimeVal;
            default -> REALLY_BIG_TIME_VAL;
        };

        switch (this.optNxXx) {
            case "XX", "xx" -> {
                if (memoryRef.keyValueStorage().containsKey(key)) {
                    memoryRef.keyValueStorage().put(key, val);
                    memoryRef.keyValueStoreTTLData().put(key ,
                            new ExpiryData(new Date().getTime() , timeout));
                } else {
                    return null;
                }
            }
            case "NX", "nx" -> {
                if (!memoryRef.keyValueStorage().containsKey(key)){
                    memoryRef.keyValueStorage().put(key, val);
                    memoryRef.keyValueStoreTTLData().put(key ,
                            new ExpiryData(new Date().getTime() , timeout));
                } else {
                    return null;
                }
            }
            default -> {
                memoryRef.keyValueStorage().put(key, val);
                memoryRef.keyValueStoreTTLData().put(key ,
                        new ExpiryData(new Date().getTime() , timeout));
            }
        }
        return "OK";
    }
}




