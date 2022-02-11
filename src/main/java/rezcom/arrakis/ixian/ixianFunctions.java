package rezcom.arrakis.ixian;

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


public class ixianFunctions {

	private static final TextComponent ixianName =
			Component.text("Ixian Probe").color(TextColor.color(0xf93f90)).decoration(TextDecoration.ITALIC,true);

	private static final Component ixianIdentifier =
			Component.text("Arrakeen Artifact - Heretical Device").color(TextColor.color(0x40a8ed)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

	private static final ArrayList<Component> ixianLore = new ArrayList<>((Arrays.asList(
			ixianIdentifier,
			Component.text(" "),
			Component.text("An invasive device left from a harsh, distant world.").color(TextColor.color(0x16c96c)).decoration(TextDecoration.ITALIC,false),
			Component.text("It is said that it looks at brain tissue so carefully").color(TextColor.color(0x16c96c)).decoration(TextDecoration.ITALIC,false),
			Component.text("that memories can be seen. It was often used in").color(TextColor.color(0x16c96c)).decoration(TextDecoration.ITALIC,false),
			Component.text("interrogations and thought analysis.").color(TextColor.color(0x16c96c)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("Allows the user to convert their experience").color(TextColor.color(0xff45b8)).decoration(TextDecoration.ITALIC,false),
			Component.text("points to Mending enchanted items. Hold a").color(TextColor.color(0xff45b8)).decoration(TextDecoration.ITALIC,false),
			Component.text("sufficiently enchanted item while wearing, and").color(TextColor.color(0xff45b8)).decoration(TextDecoration.ITALIC,false),
			Component.text("drink fluids to utilize. Converts 10 levels.").color(TextColor.color(0xff45b8)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("Tell me of thy sleeping").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("And I will tell thee of thy waking.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("Tell me of thy desires").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("And I will tell thee of thy need.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
	)));

	public static ItemStack ixianItemStack = null;

	public static void initializeIxian(){
		ItemStack itemStack = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.displayName(ixianName);
		itemMeta.lore(ixianLore);
		itemMeta.addEnchant(Enchantment.LURE,0,true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(itemMeta);

		ixianItemStack = itemStack;
	}

	public static boolean isItemIxian(ItemStack item){
		// Is this an ixian probe?
		if (item == null || (!item.hasItemMeta()) || (!item.getItemMeta().hasLore())){ return false; }
		return Objects.requireNonNull(item.getItemMeta().lore()).contains(ixianIdentifier);
	}

	public static boolean isWearingIxian(Player player){
		return isItemIxian(player.getInventory().getHelmet());
	}
}
