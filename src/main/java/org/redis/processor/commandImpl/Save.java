package org.redis.processor.commandImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redis.processor.Command;
import org.redis.processor.error.ValidationError;
import org.redis.storage.Memory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Save extends Command {
    @Override
    public void validation() throws ValidationError {
        if (!"SAVE".equalsIgnoreCase(super.getCommand())) throw new ValidationError("Not correct use of 'save' command!");
        if (super.getCommandArgs().length != 0) throw new ValidationError("Doesn't need additional args, only run 'save'");
    }

    @Override
    public Object executeCommand(Memory memoryRef) {
        String memData;
        String expMetaData;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            memData = objectMapper.writeValueAsString(memoryRef.getKeyValueStore());
            expMetaData = objectMapper.writeValueAsString(memoryRef.getKeyExpiryData());
            BufferedWriter writer = new BufferedWriter(new FileWriter("rediseDb.rdb"));
            writer.write(STR."\{memData}__SEPARATOR__\{expMetaData}");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }
}
