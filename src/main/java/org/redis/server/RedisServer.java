package org.redis.server;

import org.redis.processor.CommandProcessor;
import org.redis.storage.KeyValueStore;
import org.redis.storage.Memory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class RedisServer {
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
        //register a thread for this name
        this.memory = new Memory(new KeyValueStore()); //Allocating new memory for each connection
        ThreadFactory threadFactory =Thread.ofVirtual().name("client-handler" , 1).factory();
        this.executorService = Executors.newThreadPerTaskExecutor(threadFactory);
    }

    public void start() throws  IOException{
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

}
