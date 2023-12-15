package pl.skiq.lobbypvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import pl.skiq.lobbypvp.Lobbypvp;
import pl.skiq.lobbypvp.util.PvpUtil;

public class InventoryListener implements Listener {
    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        Lobbypvp instance = Lobbypvp.instance();
        PvpUtil pvpUtil = Lobbypvp.instance().pvpUtil();

        Player player = event.getPlayer();
        ItemStack currentItem = player.getInventory().getItem(event.getNewSlot());
        ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());

        if(previousItem != null && previousItem.getType() == Material.DIAMOND_SWORD){
            pvpUtil.disablePvp(player);
            return;
        }

        if(currentItem != null && currentItem.getType() == Material.POPPY){
            pvpUtil.enablePvp(instance, player);
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        event.setCancelled(true);
    }
}
