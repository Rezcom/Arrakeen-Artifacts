package rezcom.arrakis;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.charm.charmFunctions;
import rezcom.arrakis.stillsuit.stillsuitFunctions;
import rezcom.arrakis.stylus.stylusFunctions;

import java.util.logging.Level;


public class giveArtifactCommand implements CommandExecutor {

    public static boolean debugGiveCommand = false;

    public giveArtifactCommand(){

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        // This is for the /stillsuit command.

        if (args.length != 1){
            sender.sendMessage("Enter name of artifact you want given.");
            Main.sendDebugMessage("Arg length was not 1",debugGiveCommand);
            return false;
        }
        if (!(sender instanceof Player)){
            // Sent by the console.
            sender.sendMessage("This command can only be used by players.");
            return false;
        }
        Main.sendDebugMessage("Arg[0]: "+args[0],debugGiveCommand);
        Player p = (Player) sender;
        if (p.hasPermission("arrakis.giveartifact")){

            switch (args[0].toLowerCase()) {
                case "stillsuit":

                    if (stillsuitFunctions.stillsuitItemStack == null) {
                        sender.sendMessage("The stillsuit item stack is null! It wasn't initialized correctly.");
                        Main.logger.log(Level.SEVERE, "The stillsuit item stack is null! It wasn't initialized correctly.");
                        return false;
                    }
                    p.getInventory().addItem(stillsuitFunctions.stillsuitItemStack);
                    p.sendMessage("Stillsuit added to Inventory");
                    return true;

                case "charm":

                    if (charmFunctions.charmItemStack == null) {
                        sender.sendMessage("The MuadDib Charm stack is null! It wasn't initialized correctly.");
                        Main.logger.log(Level.SEVERE, "The MuadDib Charm stack is null! It wasn't initialized correctly.");
                        return false;
                    }
                    p.getInventory().addItem(charmFunctions.charmItemStack);
                    p.sendMessage("Muad'Dib Charm added to Inventory");
                    return true;

                case "stylus":

                    if (stylusFunctions.stylusItemStack == null) {
                        sender.sendMessage("The Bene Gesserit Stylus stack is null! It wasn't initialized correctly");
                        Main.logger.log(Level.SEVERE, "The Bene Gesserit Stylus stack is null! It wasn't initialized correctly");
                    }
                    p.getInventory().addItem(stylusFunctions.stylusItemStack);
                    p.sendMessage("Bene Gesserit Stylus added to Inventory");
                    return true;

            }

        } else {
            p.sendMessage("You do not have access to that command.");
        }
        return false;
    }
}
