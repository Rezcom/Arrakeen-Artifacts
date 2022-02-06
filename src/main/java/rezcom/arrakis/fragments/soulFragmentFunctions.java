package rezcom.arrakis.fragments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class soulFragmentFunctions {

	// Name
	private static final TextComponent fragmentNameA =
			Component.text("Lesser Soul Fragment Alpha").color(TextColor.color(0x34c9eb)).decoration(TextDecoration.ITALIC,true);

	// Identifying Component
	public static final TextComponent fragmentIdentifierA =
			Component.text("Soul Fragment - Sky").color(TextColor.color(0xa2b3ae)).decoration(TextDecoration.ITALIC,false).decoration(TextDecoration.UNDERLINED,true);

	// Lore
	private static final ArrayList<Component> fragmentLoreA = new ArrayList<>(Arrays.asList(
			fragmentIdentifierA,
			Component.text(" "),
			Component.text("Remnant of a wandering soul from a harsh,").color(TextColor.color(0xffcb47)).decoration(TextDecoration.ITALIC,false),
			Component.text("distant world. Within this realm lies its").color(TextColor.color(0xffcb47)).decoration(TextDecoration.ITALIC,false),
			Component.text("true beholder; combined with other fragments").color(TextColor.color(0xffcb47)).decoration(TextDecoration.ITALIC,false),
			Component.text("reveals its true purpose. It whispers remnants").color(TextColor.color(0xffcb47)).decoration(TextDecoration.ITALIC,false),
			Component.text("of its bearer.").color(TextColor.color(0xffcb47)).decoration(TextDecoration.ITALIC,false),
			Component.text(" "),
			Component.text("My son lives and is... human.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
			Component.text("I knew he was... but... he lives.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
			Component.text("Now, I can go on living.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true),
			Component.text("My son lives.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC,true)
	));

	public static ItemStack fragmentAStack;

	public static void initializeFragments(){
		// Initializes the fragment item stacks.
		// Please call this VERY early!

		ItemStack fragmentA = new ItemStack(Material.AMETHYST_SHARD);
		ItemMeta fragmentAmeta = fragmentA.getItemMeta();

		fragmentAmeta.displayName(fragmentNameA);
		fragmentAmeta.lore(fragmentLoreA);
		fragmentA.setItemMeta(fragmentAmeta);
		fragmentAStack = fragmentA;
	}

	public static boolean isFragmentA(ItemStack itemStack){
		if (itemStack == null || (!itemStack.hasItemMeta()) || (!itemStack.getItemMeta().hasLore())){
			return false;
		}
		return Objects.requireNonNull(itemStack.getItemMeta().lore()).contains(fragmentIdentifierA);
	}

}
