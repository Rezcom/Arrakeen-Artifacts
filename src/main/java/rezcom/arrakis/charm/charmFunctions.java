package rezcom.arrakis.charm;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class charmFunctions {

    // Name
    private static final TextComponent charmName =
            Component.text("Charm of Muad'Dib").color(TextColor.color(0x00f539)).decoration(TextDecoration.ITALIC,true);

    // Identifying Component - Identifies this item as the charm!
    public static final Component charmIdentifier =
            Component.text("Arrakeen Artifact - Ancient Charm").color(TextColor.color(0xebd196)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

    // Lore
    private static final ArrayList<Component> charmLore = new ArrayList<>(Arrays.asList(
            charmIdentifier,
            Component.text(" "),
            Component.text("A curious funeral charm from a distant, harsh world.").color(TextColor.color(0xc0e670)).decoration(TextDecoration.ITALIC,false),
            Component.text("It is said that it was once owned by a powerful").color(TextColor.color(0xc0e670)).decoration(TextDecoration.ITALIC,false),
            Component.text("prophet. Legends say that he dreamt of not only the").color(TextColor.color(0xc0e670)).decoration(TextDecoration.ITALIC,false),
            Component.text("future, but all possible futures.").color(TextColor.color(0xc0e670)).decoration(TextDecoration.ITALIC,false),
            Component.text(" "),
            Component.text("Prevents the loss of items upon the death of its").color(TextColor.color(0x5bc9fc)).decoration(TextDecoration.ITALIC,false),
            Component.text("carrier, but vanishes upon use.").color(TextColor.color(0x5bc9fc)).decoration(TextDecoration.ITALIC,false),
            Component.text(" "),
            Component.text("Full moon calls thee-").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
            Component.text("Shai-hulud shalt thou see;").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
            Component.text("Red the night, dusky sky,").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
            Component.text("Bloody death didst thou die.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
    ));

    public static ItemStack charmItemStack = null;

    public static void initializeCharm(){
        // Initializes the charm item stack!
        // Please call this VERY early!

        // Build the charm item stack
        ItemStack itemStack = new ItemStack(Material.CLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(charmName);
        itemMeta.lore(charmLore);

        itemMeta.addEnchant(Enchantment.LURE,0,true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(itemMeta);

        charmItemStack = itemStack;
    }

    public static boolean isItemMuaddibCharm(ItemStack item){
        // Is this item a charm?
        if (item == null || (!item.hasItemMeta()) || (!item.getItemMeta().hasLore())){ return false; }
        return Objects.requireNonNull(item.getItemMeta().lore()).contains(charmIdentifier);
    }
}
