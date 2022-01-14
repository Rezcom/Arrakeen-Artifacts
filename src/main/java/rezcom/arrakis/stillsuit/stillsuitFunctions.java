package rezcom.arrakis.stillsuit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import rezcom.arrakis.Main;

import java.util.*;
import java.util.logging.Level;

public class stillsuitFunctions {

    // Contains a bunch of helper functions when it comes to Stillsuits.

    // These shouldn't change.

    // Set of all foods that "count" towards replenishing the Stillsuit
    public static Set<Material> replenishFoods = new HashSet<>();

    // Color of the Chestplate for the Stillsuit
    private static final Color stillsuitColor = Color.fromRGB(0x7cc1f4);

    // How much durability the stillsuit should replenish upon drinking.
    // Please keep in mind that stillsuits are leather armor, meaning 80 points total.
    public static int durabilityReplenish = 16;

    // Identifying Text Component - Identifies a chestplate as being a stillsuit!
    private static final Component stillsuitIdentifier = Component.text("Arrakeen Artifact - Stillsuit").color(TextColor.color(0xdd4d34)).decoration(TextDecoration.UNDERLINED, true).decoration(TextDecoration.ITALIC, false);

    // Name
    private static final TextComponent stillsuitName =
            Component.text("Arrakeen Stillsuit").color(TextColor.color(0xee2727)).decoration(TextDecoration.ITALIC,true).decoration(TextDecoration.BOLD,true);

    // Lore
    private static final ArrayList<Component> stillsuitLore = new ArrayList<>(Arrays.asList(
            stillsuitIdentifier,
            Component.text(" "),
            Component.text("An unusual artifact from a distant, harsh world.").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("It's unfit for combat, and its true properties are unknown,").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("but it is rumored to have been essential for its people's").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text("survival. It should be handled with care.").color(TextColor.color(0xffd75e)).decoration(TextDecoration.ITALIC, false),
            Component.text(" "),
            Component.text("Recovers the user's moisture, allowing them to").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text("survive longer without sustenance. Consume fluids").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text("while wearing to replenish the suit.").color(TextColor.color(0x5effb5)).decoration(TextDecoration.ITALIC, false),
            Component.text(" "),
            Component.text("Lord, save us from that horrible land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
            Component.text("Save us... oh-h-h, save us").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
            Component.text("From the dry and thirsty land!").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
    ));

    public static ItemStack stillsuitItemStack = null;

    public static void initializeStillsuit(){
        // Actually initializes the stillsuit item stack!
        // Please run this VERY EARLY!!

        // Stillsuits are Arrakeen, so adding to main set of all Arrakeen identifiers
        Main.arrakeenIdentifiers.add(stillsuitIdentifier);

        // Set the foods that replenish the suit.
        replenishFoods.add(Material.MILK_BUCKET);
        replenishFoods.add(Material.BEETROOT_SOUP);
        replenishFoods.add(Material.POTION);
        replenishFoods.add(Material.MUSHROOM_STEW);
        replenishFoods.add(Material.SUSPICIOUS_STEW);
        replenishFoods.add(Material.RABBIT_STEW);

        // Build the Stillsuit item stack
        ItemStack itemStack = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Set the color appropriately.
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(stillsuitColor);
        itemStack.setItemMeta(leatherArmorMeta);


        itemMeta.displayName(stillsuitName);
        itemMeta.lore(stillsuitLore);


        itemMeta.addEnchant(Enchantment.DURABILITY, 7, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(itemMeta);



        stillsuitItemStack = itemStack;
    }

    public static void addDurability(Player p){

        // Adds durability to the player's current chestplate.
        // Please only call with a non-null player lol
        if (p == null){ return; }
        ItemStack chestplate = p.getInventory().getChestplate();
        if (chestplate == null || chestplate.getItemMeta() == null){
            return;
        }
        Damageable damageable = (Damageable) chestplate.getItemMeta();
        if (damageable.hasDamage()){
            int current = damageable.getDamage();
            damageable.setDamage(current - durabilityReplenish);
            if (damageable.getDamage() < 0){
                damageable.setDamage(0);
            }
        }
        chestplate.setItemMeta(damageable);
    }

    public static boolean isWearingStillsuit(Player p) {
        // Returns whether the player is wearing a stillsuit.
        if (p == null){
            return false;
        }
        ItemStack chestplate = p.getInventory().getChestplate();
        if (chestplate == null) { return false; }
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        Main.sendDebugMessage("Found chestplate. Comparing now",Main.debugMessages);
        if (chestplate.lore() == null){ return false; }
        if (Main.debugMessages){
            if (Objects.requireNonNull(chestplateMeta.lore()).contains(stillsuitIdentifier)){
                Main.sendDebugMessage("Chestplate matches stillsuit.",true);
            } else {
                Main.sendDebugMessage("Chestplate doesn't match stillsuit.",true);
            }
        }
        return Objects.requireNonNull(chestplateMeta.lore()).contains(stillsuitIdentifier);
    }

    public static boolean isItemStillsuit(ItemStack itemStack){
        if (itemStack.lore() == null){
            return false;
        }
        //Main.logger.log(Level.INFO, "Checking if an item is a stillsuit");
        return Objects.requireNonNull(itemStack.lore()).contains(stillsuitIdentifier);
    }

    public static boolean isInBiome(Player p, Biome b){

        if (p == null || b == null){
            return false;
        }

        Location playerLocation = p.getLocation();
        int x = (int) playerLocation.getX();
        int y = (int) playerLocation.getY();
        int z = (int) playerLocation.getZ();

        BiomeProvider biomeProvider = p.getWorld().getBiomeProvider();
        if (biomeProvider == null) { return false; }
        return biomeProvider.getBiome((WorldInfo) p.getWorld(),x,y,z) == b;
    }

}
