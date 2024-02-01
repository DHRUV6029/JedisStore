package org.redis.server;

import org.redis.processor.CommandProcessor;
import org.redis.storage.Memory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class RedisServer {
    private final String ip;
    private final int port;
    private final String persistenceFilePath;
    private final CommandProcessor commandProcessor;
    private final ExecutorService executorService;
    private ServerSocket serverSocket;
    private boolean openConnection = true;
    private List<Socket> activeConnectedSockets = new ArrayList<>();
    private Memory memory;

    public RedisServer(String ip, int port, String filePath) {
        this.ip = ip;
        this.port = port;
        this.persistenceFilePath = filePath;
        this.commandProcessor = new CommandProcessor();
        //register a thread for this name
        this.memory = new Memory(); //Allocating new memory for each connection
        ThreadFactory threadFactory =Thread.ofVirtual().name("client-handler" , 1).factory();
        this.executorService = Executors.newThreadPerTaskExecutor(threadFactory);
    }

    public void start() throws  IOException{
        this.serverSocket = new ServerSocket(this.port);
        try{
            while(openConnection){
                Socket clientSocket = this.serverSocket.accept();
                this.activeConnectedSockets.add(clientSocket);
                executorService.submit(()->
                {
                    try {
                            new RedisConnectionManager(clientSocket , this.memory).connect();
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
