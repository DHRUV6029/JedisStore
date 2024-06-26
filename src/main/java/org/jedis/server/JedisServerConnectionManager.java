package org.jedis.server;
import ch.qos.logback.classic.Logger;
import org.jedis.processor.CommandProcessor;
import org.jedis.seriliazers.RespDeserializer;
import org.jedis.seriliazers.RespSerializer;
import org.jedis.storage.Memory;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;



public class JedisServerConnectionManager {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JedisServerConnectionManager.class);
    private Memory memory;
    private Socket socket;

    public JedisServerConnectionManager(Socket socket , Memory memory){
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
            logger.info("Server Disconnected!");
            socket.close();
        } catch (IOException e) {

        }
    }
}
