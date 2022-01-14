package rezcom.arrakis.charm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.Main;

import java.util.logging.Level;

public class giveCharmCommand implements CommandExecutor {

    public giveCharmCommand(){

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
        Player p = (Player) sender;
        if (p.hasPermission("arrakis.charm.give")){
            if (charmFunctions.charmItemStack == null){
                Main.logger.log(Level.SEVERE, "The charm itemstack is null! It wasn't initialized correctly.");
            }
            p.getInventory().addItem(charmFunctions.charmItemStack);
            p.sendMessage("Muad'Dib Charm added to Inventory");
            return true;
        } else {
            p.sendMessage("You do not have access to that command.");
        }
        return false;
    }
}
