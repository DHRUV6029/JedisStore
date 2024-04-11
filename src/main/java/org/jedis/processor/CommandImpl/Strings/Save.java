package org.jedis.processor.CommandImpl.Strings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jedis.processor.Command;
import org.jedis.processor.error.ValidationError;
import org.jedis.storage.Memory;

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
            memData = objectMapper.writeValueAsString(memoryRef.keyValueStorage());
            expMetaData = objectMapper.writeValueAsString(memoryRef.keyValueStoreTTLData());
            BufferedWriter writer = new BufferedWriter(new FileWriter("rediseDb.rdb"));
            writer.write(STR."\{memData}__SEPARATOR__\{expMetaData}");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }
}
