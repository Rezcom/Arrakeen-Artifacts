package rezcom.arrakis.stylus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class stylusFunctions {

    // Name
    private static final TextComponent stylusName =
            Component.text("Bene Gesserit Stylus").color(TextColor.color(0xC5ACFF)).decoration(TextDecoration.ITALIC,true);

    // Identifying Component - Identifies this item as a stylus
    public static final Component stylusIdentifier =
            Component.text("Arrakeen Artifact - Stylus").color(TextColor.color(0xFFE510)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

    // Lore
    private static final ArrayList<Component> stylusLore = new ArrayList<>(Arrays.asList(
            stylusIdentifier,
            Component.text(" "),
            Component.text("An esoteric alchemy tool from a distant, harsh world.").color(TextColor.color(0xf47cab)).decoration(TextDecoration.ITALIC,false),
            Component.text("Used by a powerful society of witches, this tool is a lasting").color(TextColor.color(0xf47cab)).decoration(TextDecoration.ITALIC,false),
            Component.text("remnant of their mastery of various poisons. It's been").color(TextColor.color(0xf47cab)).decoration(TextDecoration.ITALIC,false),
            Component.text("modified to increase moisture preservation and efficiency.").color(TextColor.color(0xf47cab)).decoration(TextDecoration.ITALIC,false),
            Component.text(" "),
            Component.text("Upon creating a potion, the user has a small chance").color(TextColor.color(0x2bf7ad)).decoration(TextDecoration.ITALIC,false),
            Component.text("to create an additional. Keep it in hand to use.").color(TextColor.color(0x2bf7ad)).decoration(TextDecoration.ITALIC,false),
            Component.text("Odds are increased for Level 25+ users.").color(TextColor.color(0x2bf7ad)).decoration(TextDecoration.ITALIC,false),
            Component.text(" "),
            Component.text("There is no reality ???").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
            Component.text("only our own order imposed on everything.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true)
    ));

    public static ItemStack stylusItemStack = null;

    public static void initializeStylus(){
        // Initializes the stylus item stack!
        // Please call this VERY early!

        // Build the stylus item stack
        ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(stylusName);
        itemMeta.lore(stylusLore);

        itemMeta.addEnchant(Enchantment.LURE,0,false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(itemMeta);

        stylusItemStack = itemStack;
    }

    public static boolean isPlayerHoldingStylus(Player player){
        // Returns if the player is holding the stylus in either hand

        ItemStack mainStack = player.getInventory().getItemInMainHand();
        ItemStack offhandStack = player.getInventory().getItemInOffHand();

        return isItemBeneGesseritStylus(mainStack) || isItemBeneGesseritStylus(offhandStack);
    }

    public static boolean isItemBeneGesseritStylus(ItemStack item){
        if (item == null || (!item.hasItemMeta()) || (!item.getItemMeta().hasLore())){ return false; }
        return Objects.requireNonNull(item.getItemMeta().lore()).contains(stylusIdentifier);
    }
}
