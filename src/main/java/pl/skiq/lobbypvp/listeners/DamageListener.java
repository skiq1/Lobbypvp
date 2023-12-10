package pl.skiq.lobbypvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onPlayerDamageByPlayerEvent(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)){
            return;
        }
        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        if(!(damager.getInventory().getItemInMainHand().getType() == Material.WOODEN_SWORD &&
                victim.getInventory().getItemInMainHand().getType() == Material.WOODEN_SWORD)){
            event.setCancelled(true);
        }
    }
}
