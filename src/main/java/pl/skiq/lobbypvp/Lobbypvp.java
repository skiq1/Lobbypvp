package pl.skiq.lobbypvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.skiq.lobbypvp.listeners.DamageListener;
import pl.skiq.lobbypvp.listeners.DeathListener;
import pl.skiq.lobbypvp.listeners.InventoryListener;
import pl.skiq.lobbypvp.listeners.PlayerJoinListener;

public final class Lobbypvp extends JavaPlugin {

    private static Lobbypvp instance;
    private PvpUtil pvpUtil;

    private DatabaseUtil database;

    public static Lobbypvp instance() {
        return instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("X");
        database.disconnect();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        database();

        Bukkit.getLogger().info("X");
        instance = this;
        pvpUtil = new PvpUtil();

        registerListeners();
    }

    private void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new DeathListener(), this);
    }

    public PvpUtil pvpUtil(){
        return pvpUtil;
    }

    public void database(){
        String databaseURI = getConfig().getString("mongo-client-uri");
        String databaseName = getConfig().getString("mongo-database");

        if(databaseURI == null || databaseName == null){
            Bukkit.getLogger().warning("The database URI or database name is not set in the config.yml");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        database = new DatabaseUtil(databaseURI, databaseName);
    }
}
