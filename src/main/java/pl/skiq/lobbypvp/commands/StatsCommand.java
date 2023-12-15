package pl.skiq.lobbypvp.commands;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.skiq.lobbypvp.DatabaseUtil;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player) && args.length < 1){
            commandSender.sendMessage(ChatColor.RED + "You must specify player");
            return true;
        }

        Player player;
        if(args.length < 1){
            player = (Player) commandSender;
        }else{
            player = Bukkit.getPlayer(args[0]);
            if(player == null){
                commandSender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
        }

        commandSender.sendMessage(ChatColor.RED + "--- Stats ---");
        commandSender.sendMessage(ChatColor.GOLD + "Player: " + ChatColor.GREEN + player.getName());
        Document dbPlayer = DatabaseUtil.getPlayer(player.getUniqueId().toString());

        dbPlayer.remove("_id");
        dbPlayer.forEach((key, value) -> {
            commandSender.sendMessage(ChatColor.GOLD + key + ": " + ChatColor.GREEN + value);
        });
        commandSender.sendMessage(ChatColor.RED + "--- End ---");

        return true;
    }
}
