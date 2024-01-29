package org.redis.utilities;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Helper {
    public static LocalDateTime unixTimeStampToDateTime(double unixTimeStamp) {
        Instant instant = Instant.ofEpochSecond((long) unixTimeStamp);
        return LocalDateTime.ofEpochSecond(instant.getEpochSecond(), 0, ZoneOffset.UTC);
    }


}
