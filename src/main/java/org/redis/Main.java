package org.redis;
import ch.qos.logback.classic.Logger;
import org.redis.utilities.Constants;
import org.redis.server.RedisServer;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws IOException {
        //Entry point for redis running as single Thread
        System.out.println("Server Running");

        var redisServer = new RedisServer( Constants.serverIP, Constants.port , Constants.filePath);
        redisServer.start();

        System.out.println("Server Running");
        }
    }
