package org.jedis.processor.error;

public class WrongLiteralTypeError extends RuntimeException{
    public WrongLiteralTypeError(String message){
        super(STR."(Wrong-Type Error) \{message}");
    }
}
