package rezcom.arrakis.dilapidatedbow;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class dilapidatedBowFunc {
	// Functions for the Dilapidated Bow

	// Name
	private static final TextComponent bowName =
			Component.text("Dilapidated Bow").color(TextColor.color(0xFF00FF)).decoration(TextDecoration.ITALIC,true);

	// Identifier
	public static final TextComponent bowIdentifier =
			Component.text("Arrakeen Artifact - Dilapidated Bow").color(TextColor.color(0xe63030)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

	public static final ArrayList<Component> bowLore = new ArrayList<Component>(Arrays.asList(
			bowIdentifier,
			Component.text(" "),
			Component.text("An old, decrepit weapon recovered from a harsh, distant world.").color(TextColor.color(0xf7d774)).decoration(TextDecoration.ITALIC,false),
			Component.text("Its functionality is primitive, but it's been modified by").color(TextColor.color(0xf7d774)).decoration(TextDecoration.ITALIC,false),
			Component.text("the hands of astute engineers. They hail from a desperate people").color(TextColor.color(0xf7d774)).decoration(TextDecoration.ITALIC,false),
			Component.text("whose ability to the cruelness of their land is unparalleled.").color(TextColor.color(0xf7d774)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("A bow with unmatched ability to knock foes back,").color(TextColor.color(0xbee743)).decoration(TextDecoration.ITALIC,false),
			Component.text("it also sets victims fiercely ablaze. Replenish it").color(TextColor.color(0xbee743)).decoration(TextDecoration.ITALIC,false),
			Component.text("with a Netherite Block at an anvil.").color(TextColor.color(0xbee743)).decoration(TextDecoration.ITALIC,false),
			Component.text("Requires Level 30+.").color(TextColor.color(0xbee743)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("The purpose of argument").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("is to change the nature of truth.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
	));

	public static ItemStack bowItemStack = null;

	public static void initializeDilapidatedBow(){
		// Please call this early
		ItemStack itemStack = new ItemStack(Material.BOW);
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.displayName(bowName);
		itemMeta.lore(bowLore);

		itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK,5,true);
		itemMeta.addEnchant(Enchantment.ARROW_FIRE,1,true);
		itemMeta.addEnchant(Enchantment.ARROW_DAMAGE,2,true);

		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		itemStack.setItemMeta(itemMeta);
		bowItemStack = itemStack;

	}

	public static boolean isItemDilapidatedBow(ItemStack itemStack){
		if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()){
			return false;
		}
		return Objects.requireNonNull(itemStack.getItemMeta().lore()).contains(bowIdentifier);
	}
}
