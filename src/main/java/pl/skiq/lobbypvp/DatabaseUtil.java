package pl.skiq.lobbypvp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseUtil {
    private final MongoClient dbClient;
    private final MongoDatabase db;
    private static MongoCollection<Document> players;
    public DatabaseUtil(String databaseURI, String databaseName){
        dbClient = MongoClients.create(databaseURI);
        db = dbClient.getDatabase(databaseName);
        players = db.getCollection("players");
    }

    private static void createPlayer(String uuid){
        Document player = new Document("uuid", uuid)
                .append("kills", 0)
                .append("damageDealt", 0.0)
                .append("deaths", 0)
                .append("killstreak", 0)
                .append("highestKillstreak", 0);
        players.insertOne(player);
    }

    public static void addKill(String uuid){
        increment(uuid, "kills", 1);
    }

    public static void addDeath(String uuid){
        increment(uuid, "deaths", 1);
    }

    public static void addDamageDealt(String uuid, int damage){
        increment(uuid, "damageDealt", damage);
    }

    public static void addKillstreak(String uuid){
        increment(uuid, "killstreak", 1);
    }

    public static void resetKillstreak(String uuid){
        set(uuid, "killstreak", 0);
    }

    public static void setHighestKillstreak(String uuid, int killstreak){
        set(uuid, "highestKillstreak", killstreak);
    }

    public static int getHighestKillstreak(String uuid){
        Document player = getPlayer(uuid);
        return player.getInteger("highestKillstreak");
    }

    public static int getCurrentKillstreak(String uuid){
        Document player = getPlayer(uuid);
        return player.getInteger("killstreak");
    }

    public static void increment(String uuid, String field, Integer value){
        if(value == null)
            value = 1;
        Document player = getPlayer(uuid);
        players.updateOne(player, new Document("$inc", new Document(field, value)));
    }

    public static void set(String uuid, String field, int value){
        Document player = getPlayer(uuid);
        players.updateOne(player, new Document("$set", new Document(field, value)));
    }

    public static Document getPlayer(String uuid){
        Document player = players.find(new Document("uuid", uuid)).first();
        if(player == null){
            createPlayer(uuid);
            return getPlayer(uuid);
        }

        return player;
    }

    public void disconnect(){
        dbClient.close();
    }

    public boolean isConnected(){
        return dbClient != null;
    }

}
