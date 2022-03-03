package rezcom.arrakis.suspensorBoots;

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

public class suspensorBootsFunctions {

	// Name
	private static final TextComponent bootsName =
			Component.text("Suspensor Boots").color(TextColor.color(0x349eeb)).decoration(TextDecoration.ITALIC,true);

	// Identifying Component
	public static final Component bootsIdentifier =
			Component.text("Arrakeen Artifact - Suspensor Boots").color(TextColor.color(0xff9747)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

	// Lore
	private static final ArrayList<Component> bootsLore = new ArrayList<>(Arrays.asList(
			bootsIdentifier,
			Component.text(" "),
			Component.text("An impressive pair of boots from a harsh, distant world.").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("It was used by a repulsive, grotesque tyrant that was so").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("abhorrently obese, he required this advanced anti-gravity").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("technology to provide him even a modicum of movement.").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text(" "),
			Component.text("Dissipates a user's kinetic energy output, allowing them").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("to fall from great heights without taking damage. May ").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("become unstable at extreme elevations. Requires Level 15+").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("to utilize. Replenish at an anvil with an Eye of Ender.").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text(" "),
			Component.text("And I stood upon the sand of the sea").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("and saw a beast rise up out of the").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("the sea... and upon his heads the ").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("name of blasphemy.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
	));

	public static ItemStack bootsStack = null;

	public static void initializeBoots(){
		// Please call this early

		ItemStack itemStack = new ItemStack(Material.GOLDEN_BOOTS);
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.displayName(bootsName);
		itemMeta.lore(bootsLore);

		itemMeta.addEnchant(Enchantment.LURE,0,true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		itemStack.setItemMeta(itemMeta);

		bootsStack = itemStack;
	}

	public static boolean isItemSuspensorBoots(ItemStack item){
		if (item == null || (!item.hasItemMeta()) || (!item.getItemMeta().hasLore())){ return false; }
		return Objects.requireNonNull(item.getItemMeta().lore()).contains(bootsIdentifier);
	}

	public static boolean isPlayerWearingSuspensor(Player player){
		if (player == null){return false;}
		ItemStack itemStack = player.getEquipment().getBoots();
		if (itemStack == null){return false;}
		return isItemSuspensorBoots(itemStack);
	}
}
