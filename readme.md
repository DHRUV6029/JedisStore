## Redis Server (Java based implementation)


This is "Redis"-like server, with support for RESP v2 protocol for serialization and deserialization, and a subset of Redis commands: PING, ECHO, GET, SET (with NX, XX, EX and PX options), DEL, EXISTS, INCR, DECR, LPUSH, RPUSH, LRANGE, FLUSHALL and SAVE. It seamlessly works with the Redis CLI, as well as the Jedis (Java) Client for Redis.

## Run Instructions
1. Run `git clone <URL>` 
2. Open powershell (or terminal), `cd` into the projects base directory, if you are not already there.
3. Run `mvn clean package`, does as above, but also runs the tests.
4. To run the jar, execute `java --enable-preview -jar .\target\RedisServer-1.0-SNAPSHOT.jar` from the projects base directory
## OR
1. You can also use Docker image by executing `docker build -t <my-application> .`
2. Then Run `docker run -p 8080:8080 <my-application>`.

## Note
1. You will need Java version 21 with --enable-preview flag to use String Template
Which are used in serializer and deserializer.

2. If you want to configure Aws cloud Logging then use start scripts in /scripts folder.

## Performance BenchMark

## RedisServer(Java Based) v/s RedisServer

## Set and Get Functions
1. RedisServer (StandAlone)
   
![Alt text](<benchmarks/Screenshot (7).png>)

2. RedisServer (Java Implementation)
   
![Alt text](<benchmarks/Screenshot (8).png>)

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


## RedisServer(Java Based) vs ClauDb(Another Java Based implementation of Redis)

## Set and Get

1. ClauDb
![Alt text](<benchmarks/Screenshot (13).png>) 

2. RedisServer(Java Based)
![Alt text](<benchmarks/Screenshot (14).png>) 

## Set and Lpush

1. ClauDb
![Alt text](<benchmarks/Screenshot (16).png>) 

2. RedisServer(Java Based)
![Alt text](<benchmarks/Screenshot (15).png>) 



## Comments
This redis implementation does not surpass the official Redis implementation, due to
obvious reason , as official redis is in C which is much closer to bare metal.

However, on comparion with other open source java based redis server like ClauDb,the performnace of this implementation performs on average 20% better (set, get, lpush)





