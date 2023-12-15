package pl.skiq.lobbypvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.skiq.lobbypvp.commands.LogCommand;
import pl.skiq.lobbypvp.commands.StatsCommand;
import pl.skiq.lobbypvp.listeners.DamageListener;
import pl.skiq.lobbypvp.listeners.DeathListener;
import pl.skiq.lobbypvp.listeners.InventoryListener;
import pl.skiq.lobbypvp.listeners.PlayerJoinListener;

public final class Lobbypvp extends JavaPlugin {

    private static Lobbypvp instance;
    private PvpUtil pvpUtil;

    private DatabaseUtil database;
    private RedisLogUtil redisLogger;

    public static Lobbypvp instance() {
        return instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("X");
        database.disconnect();
        redisLogger.close();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        database();
        redisLog();

        Bukkit.getLogger().info("X");
        instance = this;
        pvpUtil = new PvpUtil();

        registerListeners();
        registerCommands();
    }

    private void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new DeathListener(), this);
    }

    private void registerCommands(){
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("log").setExecutor(new LogCommand());
    }

    public PvpUtil pvpUtil(){
        return pvpUtil;
    }

    private void database(){
        String databaseURI = getConfig().getString("mongo-client-uri");
        String databaseName = getConfig().getString("mongo-database");

        if(databaseURI == null || databaseName == null){
            Bukkit.getLogger().warning("The database URI or database name is not set in the config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        database = new DatabaseUtil(databaseURI, databaseName);
    }

    private void redisLog(){
        String redisHost = getConfig().getString("redis-host");
        String redisPort = getConfig().getString("redis-port");
        String redisUser = getConfig().getString("redis-username");
        String redisPassword = getConfig().getString("redis-password");

        if(redisHost == null || redisPort == null){
            Bukkit.getLogger().warning("The redis host or port is not set in the config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        redisLogger = new RedisLogUtil(redisHost, Integer.parseInt(redisPort), redisUser, redisPassword);
    }
}
