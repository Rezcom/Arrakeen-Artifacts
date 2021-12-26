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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class giveStillsuitCommand implements CommandExecutor {

    private Main plugin;

    // These shouldn't change.

    private final Color stillsuitColor = Color.fromRGB(0x7cc1f4);

    // Name
    private final TextComponent stillsuitName =
            Component.text("Arrakeen Stillsuit").color(TextColor.color(0xee2727)).decoration(TextDecoration.ITALIC,true).decoration(TextDecoration.BOLD,true);

    // Lore
    private ArrayList<Component> stillsuitLore = new ArrayList<Component>(Arrays.asList(
            Component.text("Arrakeen Artifact - Stillsuit").color(TextColor.color(0xdd4d34)).decoration(TextDecoration.UNDERLINED,true).decoration(TextDecoration.ITALIC, false),
            Component.text(" "),
            Component.text("An unusual artifact from a distant, harsh world.").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("It's unfit for combat, and its true properties are unknown,").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("but it is rumored to have been essential for its people's").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("survival. It should be handled with care.").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text(" "),
            Component.text("Recovers the user's moisture, allowing them to").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text("survive longer without sustenance. Provides Fire Resistance,").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text("but is rather fragile under great heat.").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text("Consume fluids to replenish the suit.").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text(" "),
            Component.text("Lord, save us from that horrible land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
            Component.text("Save us... oh-h-h, save us").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
            Component.text("From the dry and thirsty land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true)
    ));

    public giveStillsuitCommand(){

    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args){
        // This is for the /stillsuit command.
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


            itemMeta.addEnchant(Enchantment.LURE, 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            itemStack.setItemMeta(itemMeta);

            p.getInventory().addItem(itemStack);
            p.sendMessage("Stillsuit added to Inventory");
            return true;

        } else {
            p.sendMessage("You do not have access to that command.");
        }
        return false;
    }
}
