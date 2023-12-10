package pl.skiq.lobbypvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.skiq.lobbypvp.listeners.DamageListener;
import pl.skiq.lobbypvp.listeners.InventoryListener;
import pl.skiq.lobbypvp.listeners.PlayerJoinListener;
import pl.skiq.lobbypvp.util.PvpUtil;

public final class Lobbypvp extends JavaPlugin {

    private static Lobbypvp instance;
    private PvpUtil pvpUtil;

    public static Lobbypvp instance() {
        return instance;
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("X");
    }

    @Override
    public void onEnable() {
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
    }

    public PvpUtil pvpUtil(){
        return pvpUtil;
    }
}
