package rezcom.arrakis.stylus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GesseritMobCommand implements CommandExecutor {

	public static ItemStack customBow;
	public static ItemStack customChestplate;
	public static ItemStack customHelmet;
	public static ItemStack customBoots;

	public static boolean strayDebug = true;

	public static final Component customBowIdentifier =
			Component.text("Fear is the mind killer.").color(TextColor.color(0xFB3232));

	public static final ArrayList<Component> customBowLore = new ArrayList<>(Arrays.asList(
			Component.text("I must not fear.").color(TextColor.color(0xF7892B)),
			customBowIdentifier
	));

	public static final Component customBowName =
			Component.text("Dilapidated Bow").color(TextColor.color(0xFF00FF));

	public GesseritMobCommand(){

	}

	public static void initializeStrayEquipment(){
		ItemStack bow = new ItemStack(Material.BOW);

		ItemMeta meta = bow.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_KNOCKBACK,800,true);
		meta.addEnchant(Enchantment.ARROW_DAMAGE,30,true);
		meta.addEnchant(Enchantment.DURABILITY,500,true);
		meta.addEnchant(Enchantment.ARROW_FIRE,5,true);
		meta.displayName(customBowName);
		meta.lore(customBowLore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		bow.setItemMeta(meta);

		customBow = bow;

		ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,100,true);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,140,true);
		chestplateMeta.addEnchant(Enchantment.DURABILITY,40,true);
		chestplateMeta.addEnchant(Enchantment.THORNS,1,true);

		chestplate.setItemMeta(chestplateMeta);
		customChestplate = chestplate;

		ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,255,true);
		helmetMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,140,true);
		helmetMeta.addEnchant(Enchantment.DURABILITY,40,true);

		helmet.setItemMeta(helmetMeta);
		customHelmet = helmet;

		ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,255,true);
		bootsMeta.addEnchant(Enchantment.PROTECTION_FALL,255,true);

		boots.setItemMeta(bootsMeta);
		customBoots = boots;
	}
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){

		if (!(sender instanceof Player)){
			Main.sendDebugMessage("Wasn't an instance of player",strayDebug);
			return false;
		}
		Player player = (Player) sender;
		if (!(player.hasPermission("arrakis.gesseritmob"))){
			Main.sendDebugMessage("Didn't have permission",strayDebug);
			player.sendMessage("You do not have permission.");
			return false;
		}
		Main.sendDebugMessage("Had permission, spawning...",strayDebug);

		LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.STRAY);
		Stray stray = (Stray) mob;

		Main.sendDebugMessage("Spawned, now modifying",strayDebug);

		stray.setCustomName("§3Hollow Witch");
		stray.setCustomNameVisible(true);
		stray.setHealth(20.0);
		stray.setArrowsInBody(7);
		stray.setCanPickupItems(false);
		stray.setRemoveWhenFarAway(false);
		stray.setPersistent(true);
		stray.setInvisible(true);
		stray.setCollidable(false);
		stray.setVisualFire(true);
		stray.setAI(true);

		Main.sendDebugMessage("Custom stuff done. Gonna add equipment",strayDebug);

		EntityEquipment entityEquipment = stray.getEquipment();
		entityEquipment.setItemInMainHand(customBow);
		entityEquipment.setItemInOffHand(null);

		entityEquipment.setChestplate(customChestplate);
		entityEquipment.setHelmet(customHelmet);
		entityEquipment.setBoots(customBoots);

		PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3);
		PotionEffect fireResist = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1);

		stray.addPotionEffect(resistance);
		stray.addPotionEffect(fireResist);
		Main.sendDebugMessage("Everything done",strayDebug);

		player.sendMessage("Spawned a Hollow Witch");
		return true;
	}

}
