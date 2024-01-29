package org.redis.processor.error;

public class ValidationError extends  RuntimeException{
    public ValidationError(String message){
        super(STR."VAL-ERROR\{message}");
    }
}
