package pl.skiq.lobbypvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.skiq.lobbypvp.util.RedisLog;
import pl.skiq.lobbypvp.util.RedisLogUtil;

import java.util.List;

public class LogCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        List<RedisLog> logs;
        if(args.length < 1){
            logs =  RedisLogUtil.getLogs();
        }else{
            logs =  RedisLogUtil.getLogs(Integer.parseInt(args[0]));
        }
        commandSender.sendMessage(ChatColor.RED + "--- Logs ---");
        logs.forEach(log -> {
            commandSender.sendMessage(ChatColor.GOLD + "[" + log.getDatetime() + "] " + ChatColor.RED + log.getKiller() + ChatColor.GOLD + " killed " + ChatColor.GREEN + log.getVictim());
        });
        commandSender.sendMessage(ChatColor.RED + "--- End ---");

        return true;
    }
}
