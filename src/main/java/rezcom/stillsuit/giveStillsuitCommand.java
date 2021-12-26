package rezcom.stillsuit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class giveStillsuitCommand implements CommandExecutor {

    private Main plugin;

    // These shouldn't change.

    private final Color stillsuitColor = Color.SILVER;

    // Name
    private final TextComponent stillsuitName =
            Component.text("Arrakeen Stillsuit").color(TextColor.color(0xff7962)).decoration(TextDecoration.ITALIC,true);
    // Lore
    private ArrayList<String> lore = new ArrayList<String>(Arrays.asList(
            "§cArrakeen Artifact - Stillsuit",
            "\n",
            "§eAn unusual artifact from a distant, harsh world.",
            "§eIts exact properties are somewhat unknown, but",
            "§eit is rumored to have been essential for its people's",
            "§esurvival. It should be handled with care, and moisture.",
            "\n",
            "§6§oLord, save us from that horrible land!",
            "§6§oSave us... oh-h-h-h, save us",
            "§6§oFrom the dry and thirsty land!"
    ));

    private ArrayList<Component> stillsuitLore = new ArrayList<Component>(Arrays.asList(
            Component.text("Arrakeen Artifact - Stillsuit").color(TextColor.color(0xff2121)).decoration(TextDecoration.UNDERLINED,true),
            Component.newline(),
            Component.text("An unusual artifact from a distant, harsh world.").color(TextColor.color(0xffd75e)),
            Component.text("It's unfit for combat, and its true properties are unknown,").color(TextColor.color(0xffd75e)),
            Component.text("but it is rumored to have been essential for its people's").color(TextColor.color(0xffd75e)),
            Component.text("survival. It should be handled with care.").color(TextColor.color(0xffd75e)),
            Component.newline(),
            Component.text("Recovers the user's moisture, allowing them to").color(TextColor.color(0x5effb5)),
            Component.text("survive longer without sustenance. Provides Fire Resistance,").color(TextColor.color(0x5effb5)),
            Component.text("but is rather fragile under such heat.").color(TextColor.color(0x5effb5)),
            Component.text("Drink water to regain moisture and replenish the suit.").color(TextColor.color(0x5effb5)),
            Component.newline(),
            Component.text("Lord, save us from that horrible land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
            Component.text("Save us... oh-h-h, save us").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
            Component.text("From the dry and thirsty land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true)
    ));

    public giveStillsuitCommand(Main plugin){
        this.plugin = plugin;
        plugin.getCommand("stillsuit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args){
        // This is for the /stillsuit command.
        String playerName = args[0];
        if (!(sender instanceof Player)){
            // Sent by the console.
            sender.sendMessage("This command can only be used by players.");
            return true; // Fix this later.
        }
        Player p = (Player) sender;
        if (p.hasPermission("stillsuit.give")){
            ItemStack itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemMeta itemMeta = itemStack.getItemMeta();

            // Set the color appropriately.
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
            leatherArmorMeta.setColor(this.stillsuitColor);
            itemStack.setItemMeta(leatherArmorMeta);


            itemMeta.displayName(stillsuitName);
            itemMeta.lore(stillsuitLore);
            itemStack.setItemMeta(itemMeta);


            return true;

        } else {
            p.sendMessage("You do not have access to that command.");
        }
        return false;
    }
}
