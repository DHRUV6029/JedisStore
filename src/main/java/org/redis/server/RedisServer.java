package org.redis.server;

import org.redis.processor.CommandProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RedisServer {
    private final String ip;
    private final int port;
    private final String persistenceFilePath;
    private final CommandProcessor commandProcessor;
    private final Object commandProcessorLock;
    private final ExecutorService executorService;

    public RedisServer(String ip, int port, String filePath) {
        this.ip = ip;
        this.port = port;
        this.persistenceFilePath = filePath;
        this.commandProcessor = new CommandProcessor();
        this.commandProcessorLock = new Object();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port, 0, InetAddress.getByName(ip))) {
            System.out.println("Redis Lite server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setTcpNoDelay(true);
                System.out.println("Client connected.");

                CompletableFuture.runAsync(() -> handleClient(clientSocket), executorService);
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }


    private void handleClient(Socket clientSocket) {
        try (Socket socket = clientSocket;
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            byte[] buffer = new byte[1024];

            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    break;
                }

                String request = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);

                String response = null;
                synchronized (commandProcessorLock) {
                    //response = commandProcessor.processCommand(request);
                }

                byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
                System.out.println(response);
                outputStream.write(responseBytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            System.out.println("Client disconnected.");
        }
    }

    public void loadDatabaseState() {
        if (Files.exists(Paths.get(persistenceFilePath))) {
            //commandProcessor.processLoadCommand(persistenceFilePath);
        }
    }

}
