package org.jedis.utilities;


public class Constants {
    public static String serverIP = "127.0.0.1";
    public static int port = 6380;
    public static String filePath = "data.bin";

    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final String CRLF = STR."\{CR}\{LF}";

    // first bytes of different data types
    public static final char SIMPLE_STR = '+';
    public static final char SIMPLE_ERR = '-';
    public static final char INTEGER = ':';
    public static final char BULK_STR = '$';
    public static final char ARRAY = '*';

    // commands  Strings
    public static final String PING = "PING";
    public static final String  DELETE = "DELETE";
    public static final String ECHO = "ECHO";
    public static final String SET = "SET";
    public static final String GET = "GET";
    public static final String EXISTS = "EXISTS";
    public static final String INCR = "INCR";
    public static final String INCRBY = "INCRBY";
    public static final String DECR = "DECR";
    public static final String DECRBY = "DECRBY";
    public static final String LPUSH = "LPUSH";
    public static final String RPUSH = "RPUSH";
    public static final String LRANGE = "LRANGE";
    public static final String FLUSHALL = "FLUSHALL";
    public static final String EXIT = "EXIT";
    public static final String SAVE = "SAVE";
    public static final String APPEND = "APPEND";
    public static final String MGET = "MGET";
    public static final String MSET = "MSET";

    // commands  Hash
    public static final String HSET = "HSET";




    //Exception Messages
    public static final String INVALID_COMMAND_ERROR = "Command should be an array of bulk str";
    public static final String UNKNOWN_COMMAND_ERROR = "Command should be an array of bulk str";
    public static final String CLOSE_CONNECTION = "Disconnecting Client";


}
