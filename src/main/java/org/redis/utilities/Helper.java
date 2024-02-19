package org.redis.utilities;
import org.redis.processor.error.RedisServerError;


import java.util.function.Predicate;


public class Helper {
    public static final Predicate<String> IsNumber = (numStr) -> {
        try {
            Integer.parseInt(numStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    public static final Predicate<String> isExPx = (opt) -> {
        if ("EX".equalsIgnoreCase(opt) || "PX".equalsIgnoreCase(opt)) return true;
        if ("EXAT".equalsIgnoreCase(opt) || "PXAT".equalsIgnoreCase(opt))
            throw new RedisServerError("EXAT & PXAT are not supported yet");
        else return false;
    };

    public static final Predicate<String> isNxXx = (opt) -> "NX".equalsIgnoreCase(opt) || "XX".equalsIgnoreCase(opt);


}
