package org.redis.server;

import org.redis.storage.Memory;

import java.io.IOException;
import java.net.Socket;

public class RedisConnectionManager {

    private Memory memory;
    private Socket socket;

    public RedisConnectionManager(Socket socket , Memory memory){
        this.memory = memory;
        this.socket = socket;
    }
    public void connect() throws IOException{

    }
}
