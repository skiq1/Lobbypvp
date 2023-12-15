package pl.skiq.lobbypvp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.skiq.lobbypvp.DatabaseUtil;

public class DeathListener implements Listener{
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if (killer == null)
            return;

        event.setDeathMessage(ChatColor.RED + player.getDisplayName() + " was killed by " + killer.getDisplayName() + "!");

        String killerUUID = killer.getUniqueId().toString();
        String playerUUID = player.getUniqueId().toString();

        //database
        //killer
        DatabaseUtil.addKill(killerUUID);
        DatabaseUtil.addKillstreak(killerUUID);
        if(DatabaseUtil.getHighestKillstreak(killerUUID) < DatabaseUtil.getCurrentKillstreak(killerUUID)){
            DatabaseUtil.setHighestKillstreak(killerUUID, DatabaseUtil.getCurrentKillstreak(killerUUID));
        }

        //victim
        DatabaseUtil.addDeath(playerUUID);
        DatabaseUtil.resetKillstreak(playerUUID);
    }
}
