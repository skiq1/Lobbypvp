package pl.skiq.lobbypvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.skiq.lobbypvp.Lobbypvp;
import pl.skiq.lobbypvp.PvpUtil;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Lobbypvp instance = Lobbypvp.instance();
        PvpUtil pvpUtil = instance.pvpUtil();

        pvpUtil.disablePvp(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Lobbypvp instance = Lobbypvp.instance();
        PvpUtil pvpUtil = instance.pvpUtil();

        event.getPlayer().getInventory().setItem(pvpUtil.getSlot(), null);
    }
}
