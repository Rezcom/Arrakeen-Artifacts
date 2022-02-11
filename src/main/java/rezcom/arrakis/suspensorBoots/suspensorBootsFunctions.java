package rezcom.arrakis.suspensorBoots;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.Arrays;

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
			Component.text("An impressive pair of boots from a distant, harsh world.").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("It was used by a repulsive, grotesque tyrant that was so").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("abhorrently obese, he required this advanced gravity-").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("defying technology to provide him even a modicum of movement.").color(TextColor.color(0xaa69ff)).decoration(TextDecoration.ITALIC, false),
			Component.text(" "),
			Component.text("Dissipates a user's kinetic energy output, allowing them").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("to fall from great heights without taking damage. May become").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("unstable at great heights. Requires Level 15+ to utilize.").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text("Replenish at an anvil with an Eye of Ender.").color(TextColor.color(0x59e6ff)).decoration(TextDecoration.ITALIC, false),
			Component.text(" "),
			Component.text("And I stood upon the sand of the sea").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("and saw a beast rise up out of the").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("the sea... and upon his heads the ").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("name of blasphemy.").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true)
	));
}
