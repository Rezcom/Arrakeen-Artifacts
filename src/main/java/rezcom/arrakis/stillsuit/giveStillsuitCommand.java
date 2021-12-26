package rezcom.arrakis.stillsuit;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.Main;

import java.util.logging.Level;


public class giveStillsuitCommand implements CommandExecutor {

    public giveStillsuitCommand(){

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        // This is for the /stillsuit command.
        if (!(sender instanceof Player)){
            // Sent by the console.
            sender.sendMessage("This command can only be used by players.");
            return true; // Fix this later.
        }
        Player p = (Player) sender;
        if (p.hasPermission("stillsuit.give")){
            if (stillsuitFunctions.stillsuitItemStack == null){
                Main.logger.log(Level.SEVERE,"The stillsuit item stack is null! It wasn't initialized correctly.");
                return false;
            }
            p.getInventory().addItem(stillsuitFunctions.stillsuitItemStack);
            p.sendMessage("Stillsuit added to Inventory");
            return true;

        } else {
            p.sendMessage("You do not have access to that command.");
        }
        return false;
    }
}
