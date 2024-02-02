package org.redis.server;
import org.redis.processor.CommandProcessor;
import org.redis.seriliazers.RespDeserializer;
import org.redis.seriliazers.RespSerializer;
import org.redis.storage.Memory;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class RedisServerConnectionManager {

    private Memory memory;
    private Socket socket;

    public RedisServerConnectionManager(Socket socket , Memory memory){
        this.memory = memory;
        this.socket = socket;
    }
    public void connect() throws IOException{

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8))) {

            char[] incoming = new char[1024];
            int nosOfBytesRead;

            while((nosOfBytesRead = reader.read(incoming)) > 0) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < nosOfBytesRead; i ++) {
                    builder.append(incoming[i]);
                }

                Object response = new CommandProcessor(
                        new RespSerializer(),
                        new RespDeserializer(),
                        memory
                ).execute(builder.toString());

                writer.write(response.toString());
                writer.flush();
            }
        } catch (SocketException e) {

            socket.close();
        } catch (IOException e) {

        }
    }
}
