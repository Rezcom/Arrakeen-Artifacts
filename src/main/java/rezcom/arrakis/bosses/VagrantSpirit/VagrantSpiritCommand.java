package rezcom.arrakis.bosses.VagrantSpirit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.Main;
import rezcom.arrakis.stillsuit.stillsuitFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public class VagrantSpiritCommand implements CommandExecutor {

	private static ItemStack customKnife;
	private static ItemStack customHelmet;
	private static ItemStack customChestplate;
	private static ItemStack customLeggings;
	private static ItemStack customBoots;

	private static final int projectileLevel = 4;
	private static final int explosionLevel = 4;
	private static final int protectLevel = 3;
	private static final int thornsLevel = 10;

	public static final Component bossName =
			Component.text("Vagrant Spirit").color(TextColor.color(0xd4a200)).decoration(TextDecoration.ITALIC,false);

	public static final Component customKnifeIdentifier =
			Component.text("'Now it's complete, because it's ended here.'").color(TextColor.color(0xFB3232)).decoration(TextDecoration.ITALIC, true);

	public static final ArrayList<Component> customKnifeLore = new ArrayList<>(Arrays.asList(
			Component.text("Arrakis teaches the attitude of the knife -").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			Component.text("chopping off what's incomplete and saying:").color(TextColor.color(0xe9a800)).decoration(TextDecoration.ITALIC, true),
			customKnifeIdentifier
	));

	public static final Component customKnifeName =
			Component.text("Arrakeen Crysknife").color(TextColor.color(0xdaf7fe)).decoration(TextDecoration.ITALIC,true);

	public VagrantSpiritCommand(){

	}

	public static void initializeHuskEquipment(){
		ItemStack knife = new ItemStack(Material.IRON_SWORD);

		ItemMeta meta = knife.getItemMeta();
		meta.addEnchant(Enchantment.KNOCKBACK,4,true);
		meta.addEnchant(Enchantment.DAMAGE_ALL,19,true);
		meta.addEnchant(Enchantment.DURABILITY,255,true);
		meta.displayName(customKnifeName);
		meta.lore(customKnifeLore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		knife.setItemMeta(meta);

		customKnife = knife;

		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta helmetMeta = helmet.getItemMeta();
		helmetMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, projectileLevel, true);
		helmetMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, explosionLevel, true);
		helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,protectLevel,true);
		helmet.setItemMeta(helmetMeta);

		customHelmet = helmet;

		ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,projectileLevel,true);
		leggingsMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,explosionLevel,true);
		leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,protectLevel,true);
		leggings.setItemMeta(leggingsMeta);

		customLeggings = leggings;

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();

		LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) chestplateMeta;
		leatherArmorMeta.setColor(stillsuitFunctions.stillsuitColor);
		chestplate.setItemMeta(leatherArmorMeta);

		chestplateMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,projectileLevel,true);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,explosionLevel,true);
		chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,protectLevel,true);
		chestplateMeta.addEnchant(Enchantment.THORNS,thornsLevel,true);
		chestplateMeta.addEnchant(Enchantment.DURABILITY,255,true);
		chestplate.setItemMeta(chestplateMeta);

		customChestplate = chestplate;

		ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,projectileLevel,true);
		bootsMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,explosionLevel,true);
		bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,protectLevel,true);
		boots.setItemMeta(bootsMeta);

		customBoots = boots;

	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can execute this command.");
			return false;
		}
		Player player = (Player) sender;
		if (!(player.hasPermission("arrakis.vagrantspiritmob"))){
			player.sendMessage("You don't have permission");
			return false;
		}

		LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.HUSK);
		Husk husk = (Husk) mob;

		husk.customName(bossName);
		husk.setCustomNameVisible(false);
		husk.setCanPickupItems(false);
		husk.setPersistent(true);
		husk.setCollidable(false);
		husk.setRemoveWhenFarAway(false);
		husk.setAI(true);

		try {
			husk.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(80);
			husk.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.5);
		} catch (NullPointerException e){
			Main.logger.log(Level.WARNING, "Could not set GENERIC_FOLLOW_RANGE of Vagrant Spirit because the attribute returned null!");
		}

		EntityEquipment entityEquipment = husk.getEquipment();
		entityEquipment.setItemInMainHand(customKnife);

		entityEquipment.setChestplate(customChestplate);
		entityEquipment.setLeggings(customLeggings);
		entityEquipment.setHelmet(customHelmet);
		entityEquipment.setBoots(customBoots);

		entityEquipment.setItemInMainHandDropChance(0.0f);
		entityEquipment.setHelmetDropChance(0.0f);
		entityEquipment.setChestplateDropChance(0.0f);
		entityEquipment.setLeggingsDropChance(0.0f);
		entityEquipment.setBootsDropChance(0.0f);

		PotionEffect fireResist = new PotionEffect(PotionEffectType.FIRE_RESISTANCE,Integer.MAX_VALUE,0);
		PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,2);

		husk.addPotionEffect(fireResist);
		husk.addPotionEffect(resist);
		husk.addPotionEffect(speed);

		return true;
	}

	public static boolean isMobVagrantSpirit(Entity entity){
		if (!(entity instanceof LivingEntity)){
			return false;
		}
		LivingEntity livingEntity = (LivingEntity) entity;
		EntityEquipment entityEquipment = livingEntity.getEquipment();
		if (entityEquipment == null){return false;}
		ItemStack weapon = entityEquipment.getItemInMainHand();
		if (!weapon.hasItemMeta() || !weapon.getItemMeta().hasLore()){
			return false;
		}

		return Objects.requireNonNull(weapon.getItemMeta().lore()).contains(customKnifeIdentifier);
	}

}
