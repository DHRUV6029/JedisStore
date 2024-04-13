package org.jedis;
import org.jedis.utilities.Constants;
import org.jedis.server.JedisServer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(STR."Jedis server running started at : \{new SimpleDateFormat("MM.dd.HH.yyyy.mm.ss").format(new Date())}");
        var jedisServer = new JedisServer( Constants.serverIP, Constants.port , Constants.filePath);
        jedisServer.start();
        System.out.println(STR."JedisServer Stopped at : \{new SimpleDateFormat("MM.dd.HH.yyyy.mm.ss").format(new Date())}");
        }
    }
