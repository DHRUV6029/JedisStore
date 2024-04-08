package org.redis.server;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redis.storage.*;
import org.redis.storage.StoreImpl.HashValueStore;
import org.redis.storage.StoreImpl.KeyValueStore;
import org.redis.storage.model.ExpiryData;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;



public class RedisServer {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(RedisServer.class);
    private final String ip;
    private final int port;
    private final ExecutorService executorService;
    private ServerSocket serverSocket;
    private boolean openConnection = true;
    private List<Socket> activeConnectedSockets = new ArrayList<>();
    private Memory memory;

    public RedisServer(String ip, int port, String filePath) {
        this.ip = ip;
        this.port = port;
        this.memory = new Memory(new KeyValueStore(),
                new HashValueStore()); //Allocating new memory for each connection
        this.restoreDb();
        ThreadFactory threadFactory =Thread.ofVirtual().name("client-handler" , 1).factory();
        this.executorService = Executors.newThreadPerTaskExecutor(threadFactory);
    }

    public void start() throws  IOException{

        logger.info("This is Test");
        registerShutdownHook();
        this.serverSocket = new ServerSocket(this.port);
        try{
            while(openConnection){
                Socket clientSocket = this.serverSocket.accept();
                this.activeConnectedSockets.add(clientSocket);
                executorService.submit(()->
                {
                    try {
                            new RedisServerConnectionManager(clientSocket , this.memory).connect();
                    }catch (IOException e){}
                });
            }

        }catch (IOException e){

        }
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            try {

                for(Socket connectedClient: this.activeConnectedSockets) {
                    connectedClient.close();
                }

                this.executorService.shutdown();
                this.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public void stop() throws IOException {
        openConnection = false;
        serverSocket.close();

    }

    private void restoreDb() {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("redis.rdb"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String[] data = builder.toString().split("__SEPARATOR__");
            ObjectMapper mapper = new ObjectMapper();


            this.memory.restoreKeyValueStoreFromDisk(mapper.readValue(data[0], new TypeReference<ConcurrentHashMap>() {}));
            this.memory.restoreKeyValueStoreTTLFromDisk(mapper.readValue(data[1] ,new TypeReference<ConcurrentHashMap<String , ExpiryData>>() {}));

            if (!this.memory.keyValueStorage().isEmpty()| !this.memory.keyValueStoreTTLData().isEmpty()) {
                logger.info("Data restored");
            } else {
                logger.info("rdb file found, but nothing to restore");
            }
        } catch (FileNotFoundException e) {
            logger.info("Nothing to restore");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
