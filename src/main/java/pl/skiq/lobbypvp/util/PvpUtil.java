package pl.skiq.lobbypvp.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import pl.skiq.lobbypvp.Lobbypvp;

public class PvpUtil{
    private final int slot = 8;
    private final int delayInSec = 3;

    private ItemStack getSword(){
        ItemStack sword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cMiecz"));
        sword.setItemMeta(meta);

        return sword;
    }

    private ItemStack getPoppy(){
        ItemStack poppy = new ItemStack(Material.POPPY, 1);
        ItemMeta meta = poppy.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRoza"));
        poppy.setItemMeta(meta);

        return poppy;
    }

    private void wearArmor(Player player){
        PlayerInventory inv = player.getInventory();
        inv.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        inv.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
    }

    private void takeOffArmor(Player player){
        PlayerInventory inv = player.getInventory();
        inv.setHelmet(null);
        inv.setChestplate(null);
        inv.setLeggings(null);
        inv.setBoots(null);
    }

    public void disablePvp(Player player){
        takeOffArmor(player);

        player.getInventory().setItem(slot, getPoppy());
    }

    public void enablePvp(Lobbypvp instance, Player player){
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
            if(!player.isOnline())
                return;
            if(player.getInventory().getItemInMainHand().getType() != Material.POPPY)
                return;

            wearArmor(player);
            player.getInventory().setItem(slot, getSword());

        }, 20L * getDelayInSec());
    }

    public int getDelayInSec(){
        return delayInSec;
    }

    public int getSlot(){
        return slot;
    }
}
