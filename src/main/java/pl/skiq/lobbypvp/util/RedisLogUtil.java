package pl.skiq.lobbypvp.util;

import redis.clients.jedis.JedisPooled;

import java.util.ArrayList;
import java.util.List;

public class RedisLogUtil {
    private static JedisPooled jedis;
    public RedisLogUtil(String host, int port, String user, String password){
        jedis = new JedisPooled(host, port, user, password);
    }

    public static void log(String victim, String killer){
        String datetime = String.valueOf(System.currentTimeMillis());
        String value = victim + ":" + killer + ":" + datetime;
        jedis.rpush("lobbypvp:logs", value);
    }

    public static List<RedisLog> getLogs(int limit){
        List<String> logs = jedis.lrange("lobbypvp:logs", 0, limit);
        List<RedisLog> convertedLogs = new ArrayList<>();
        for(String log : logs){
            convertedLogs.add(new RedisLog(log));
        }
        return convertedLogs;
    }

    public static List<RedisLog> getLogs(){
        return getLogs(-1);
    }

    public void close(){
        jedis.close();
    }


}
