package org.redis.utilities;


public class Constants {
    public static String serverIP = "127.0.0.1";
    public static int port = 6379;
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

    // commands
    public static final String PING = "PING";
    public static final String ECHO = "ECHO";
    public static final String SET = "SET";
    public static final String GET = "GET";
    public static final String EXISTS = "EXISTS";
    public static final String INCR = "INCR";
    public static final String DECR = "DECR";
    public static final String EXIT = "EXIT";


    //Exception Messages
    public static final String INVALID_COMMAND_ERROR = "Command should be an array of bulk str";
    public static final String UNKNOWN_COMMAND_ERROR = "Command should be an array of bulk str";

    public static final String CLOSE_CONNECTION = "Disconnecting Client";
}
