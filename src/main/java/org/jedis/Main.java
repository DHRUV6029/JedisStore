package org.jedis;
import org.jedis.utilities.Constants;
import org.jedis.server.RedisServer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //Entry point for redis running as single Thread
        System.out.println(STR."Jedis server running started at : \{new SimpleDateFormat("MM.dd.HH.yyyy.mm.ss").format(new Date())}");
        var redisServer = new RedisServer( Constants.serverIP, Constants.port , Constants.filePath);
        redisServer.start();
        System.out.println(STR."JedisServer Stopped at : \{new SimpleDateFormat("MM.dd.HH.yyyy.mm.ss").format(new Date())}");
        }
    }
