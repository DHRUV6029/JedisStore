package org.jedis.processor.error;

import static java.lang.StringTemplate.STR;

public class ValidationError extends  RuntimeException{
    public ValidationError(String message){
        super(STR." \{message}");
    }
}
