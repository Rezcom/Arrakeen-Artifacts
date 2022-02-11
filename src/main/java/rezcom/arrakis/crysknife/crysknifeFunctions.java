package rezcom.arrakis.crysknife;

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

public class crysknifeFunctions {

	// Name
	private static final TextComponent crysknifeName =
			Component.text("Arrakeen Crysknife").color(TextColor.color(0xdaf7fe)).decoration(TextDecoration.ITALIC,true);

	// Identifier
	public static final TextComponent crysknifeIdentifier =
			Component.text("Arrakeen Artifact - Crysknife").color(TextColor.color(0x7a9dff)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

	public static final ArrayList<Component> crysknifeLore = new ArrayList<>(Arrays.asList(
			crysknifeIdentifier,
			Component.text(" "),
			Component.text("A sacred, delicate weapon once used in a harsh, distant world.").color(TextColor.color(0xff867a)).decoration(TextDecoration.ITALIC,false),
			Component.text("It was forbidden to ever leave that realm, and thus, it is").color(TextColor.color(0xff867a)).decoration(TextDecoration.ITALIC,false),
			Component.text("now terribly fragile. Its sharpness leaves its efficacy against").color(TextColor.color(0xff867a)).decoration(TextDecoration.ITALIC,false),
			Component.text("even the toughest protective armors unparalleled.").color(TextColor.color(0xff867a)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("Upon landing a critical hit, the user has a chance to").color(TextColor.color(0x7aff83)).decoration(TextDecoration.ITALIC,false),
			Component.text("remove a piece of a foe's armor, at the cost of durability.").color(TextColor.color(0x7aff83)).decoration(TextDecoration.ITALIC,false),
			Component.text("Replenish it with a Smooth Quartz Block at an anvil.").color(TextColor.color(0x7aff83)).decoration(TextDecoration.ITALIC,false),
			Component.text("Requires Level 30+ to utilize its armor-piercing abilities.").color(TextColor.color(0x7aff83)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("Arrakis teaches the attitude of the knife -").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("chopping off what's incomplete and saying:").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("'Now it's complete, because it's ended here.'").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
	));

	public static ItemStack crysknifeStack = null;

	public static void initializeCrysknife(){
		// Please call this early
		ItemStack itemStack = new ItemStack(Material.IRON_SWORD);
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.displayName(crysknifeName);
		itemMeta.lore(crysknifeLore);

		itemMeta.addEnchant(Enchantment.PROTECTION_FIRE,0,true);

		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		itemStack.setItemMeta(itemMeta);
		crysknifeStack = itemStack;
	}

	public static boolean isItemCrysknife(ItemStack item){
		if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()){
			return false;
		}
		return Objects.requireNonNull(item.getItemMeta().lore()).contains(crysknifeIdentifier);
	}


}
