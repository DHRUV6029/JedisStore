## Redis Server (Java based implementation)


This is "Redis"-like server, with support for RESP v2 protocol for serialization and deserialization, and a subset of Redis commands: PING, ECHO, GET, SET (with NX, XX, EX and PX options), DEL, EXISTS, INCR, DECR, LPUSH, RPUSH, LRANGE, FLUSHALL and SAVE. It seamlessly works with the Redis CLI, as well as the Jedis (Java) Client for Redis.

## Run Instructions
1. Run `git clone <URL>` 
2. Open powershell (or terminal), `cd` into the projects base directory, if you are not already there.
3. Run `mvn clean package`, does as above, but also runs the tests.
4. To run the jar, execute `java --enable-preview -jar .\target\RedisServer-1.0-SNAPSHOT.jar` from the projects base directory

## Note
1. You will need Java version 21 with --enable-preview flag to use String Template
Which are used in serializer and deserializer.

2. If you want to configure Aws cloud Logging then use start scripts in /scripts folder.

## Performance BenchMark

## Set and Get Functions
1. RedisServer (StandAlone)
![Alt text](<benchmarks/Screenshot (7).png>)

2. RedisServer (Java Implementation)
![Alt text](<benchmarks/Screenshot (7).png>)

## Set and LPush Functions
1. RedisServer (StandAlone)
![Alt text](<benchmarks/Screenshot (11).png>)

2. RedisServer (Java Implementation)
![Alt text](<benchmarks/Screenshot (12).png>)

## Latency 
1. RedisServer (StandAlone)
![Alt text](<benchmarks/Screenshot (10).png>)

2. RedisServer (Java Implementation)
![Alt text](<benchmarks/Screenshot (9).png>)







