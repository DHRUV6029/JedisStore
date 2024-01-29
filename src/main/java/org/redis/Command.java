package org.redis;
import org.redis.processor.error.*;
import org.redis.storage.Memory;


public abstract class Command {
    private String command;
    private String[] commandArgs;
    public Command(){}
    public abstract void ValidationError() throws ValidationError;
    public abstract Object executeCommand(Memory memoryRef);

    public void process(Memory memory) throws ValidationError{
        this.ValidationError();
        this.executeCommand(memory);
    }

    public String getCommand(){
        return command;
    }

    public String[] getCommandArgs(){
        return commandArgs;
    }

    public final Command builder(Object[] deserializedArray){
        if(deserializedArray != null){
            this.command = String.valueOf(deserializedArray[0]);
            this.commandArgs = new String[deserializedArray.length-1];
            for(int i = 1 ; i < deserializedArray.length ; i++){
                this.commandArgs[i-1] = String.valueOf(deserializedArray[i]);
            }
        }
        return  this;
    }


}
