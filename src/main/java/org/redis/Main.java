package org.redis;
import org.redis.utilities.Constants;
import org.redis.server.RedisServer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Entry point for redis running as single Thread
        var redisServer = new RedisServer( Constants.serverIP, Constants.port , Constants.filePath);
        redisServer.loadDatabaseState();
        redisServer.start();

        System.out.println("Server Running");
        }
    }
