package pl.skiq.lobbypvp.util;

import java.sql.Timestamp;

public class RedisLog {
    private final String victim;
    private final String killer;
    private final Timestamp datetime;

    public RedisLog(String value){

        String[] logParts = value.split(":");
        victim = logParts[0];
        killer = logParts[1];
        datetime = new Timestamp(Long.parseLong(logParts[2]));
    }

    public String getVictim(){
        return this.victim;
    }

    public String getKiller(){
        return this.killer;
    }

    public Timestamp getDatetime(){
        return this.datetime;
    }
}
