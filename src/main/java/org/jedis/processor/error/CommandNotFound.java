package org.jedis.processor.error;

public class CommandNotFound extends RuntimeException{
    public CommandNotFound(String message){
        super(STR."(UNK-CMD) \{message}");
    }

}
