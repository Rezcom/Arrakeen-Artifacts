package rezcom.arrakis.crysknife;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.Main;
import rezcom.arrakis.bosses.VagrantSpirit.VagrantSpiritEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class crysknifeEvents implements Listener {

	public static boolean crysknifeDebug = false;

	private static Component playerMessage =
			Component.text("The Crysknife is blunt, and must be replenished.").color(TextColor.color(0xf86363)).decoration(TextDecoration.ITALIC,true);

	private static final ArrayList<Material> diamondArmor = new ArrayList<>(Arrays.asList(
			Material.DIAMOND_HELMET,
			Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_BOOTS
	));

	private static final ArrayList<Material> netheriteArmor = new ArrayList<>(Arrays.asList(
			Material.NETHERITE_HELMET,
			Material.NETHERITE_CHESTPLATE,
			Material.NETHERITE_LEGGINGS,
			Material.NETHERITE_BOOTS
	));

	@EventHandler
	void onPlayerHit(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Player) || (!(event.getEntity() instanceof LivingEntity)) || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
			// Not a player!, or the defender isn't a living thing!, or it's a projectile!, or it's not a critical!
			Main.sendDebugMessage("Not a player!, or the defender isn't a living thing!, or it's a projectile!",crysknifeDebug);
			return;
		}

		Player player = (Player) event.getDamager();
		if (!crysknifeFunctions.isItemCrysknife(player.getInventory().getItemInMainHand())){
			return;
		}

		if (player.getLevel() < 30 || !event.isCritical()){
			event.setDamage(1.0);
			return;
		}

		Main.sendDebugMessage("Player crit something with a crysknife",crysknifeDebug);
		ItemStack crysknife = player.getInventory().getItemInMainHand();
		Damageable crysDamage = (Damageable) crysknife.getItemMeta();
		Material material = crysknife.getType();
		if (crysDamage.getDamage() == material.getMaxDurability() - 1){
			player.sendMessage(playerMessage);
			event.setDamage(1.0);
			return;
		}

		Main.sendDebugMessage("... and can potentially remove armor",crysknifeDebug);
		Random rand = new Random();
		LivingEntity defender = (LivingEntity) event.getEntity();
		if (defender.customName() != null){
			event.setDamage(1.0);
			return;
		}

		if (rand.nextDouble() <= 0.50){
			Main.sendDebugMessage("Armor gonna get removed!",crysknifeDebug);
			double whichArmorResult = rand.nextDouble();
			EntityEquipment entityEquipment = defender.getEquipment();
			if (entityEquipment == null){event.setDamage(1.0);return;}
			if (whichArmorResult <= 0.25){
				// Remove helmet
				ItemStack helmet = entityEquipment.getHelmet();
				if (helmet == null || helmet.getType() == Material.AIR){event.setDamage(1.0);return;}
				dropArmorItem(helmet,defender);
				entityEquipment.setHelmet(null);

			} else if (whichArmorResult <= 0.50){
				// Remove chestplate
				ItemStack chestplate = entityEquipment.getChestplate();
				if (chestplate == null || chestplate.getType() == Material.AIR){event.setDamage(1.0);return;}

				dropArmorItem(chestplate,defender);
				entityEquipment.setChestplate(null);

			} else if (whichArmorResult <= 0.75){
				// Remove leggings
				ItemStack leggings = entityEquipment.getLeggings();
				if (leggings == null || leggings.getType() == Material.AIR){event.setDamage(1.0);return;}
				dropArmorItem(leggings,defender);
				entityEquipment.setLeggings(null);

			} else {
				// Remove boots
				ItemStack boots = entityEquipment.getBoots();
				if (boots == null || boots.getType() == Material.AIR){event.setDamage(1.0);return;}
				dropArmorItem(boots, defender);
				entityEquipment.setBoots(null);
			}
			// Reduce durability
			crysDamage.setDamage(Math.min(material.getMaxDurability() - 1, crysDamage.getDamage() + 50));
			crysknife.setItemMeta(crysDamage);
			event.setDamage(1.0);
		}
		event.setDamage(1.0);
	}

	@EventHandler
	void onPlayerAnvil(PrepareAnvilEvent event){
		AnvilInventory anvilInventory = event.getInventory();
		ItemStack firstItem = anvilInventory.getFirstItem();
		ItemStack secondItem = anvilInventory.getSecondItem();

		if (crysknifeFunctions.isItemCrysknife(secondItem)){
			event.setResult(null);
			return;
		}

		if (crysknifeFunctions.isItemCrysknife(firstItem)){
			if (secondItem == null || (!(secondItem.getType() == Material.SMOOTH_QUARTZ && secondItem.getAmount() == 1))){
				event.setResult(null);
			} else {
				event.setResult(crysknifeFunctions.crysknifeStack);
				anvilInventory.setRepairCost(15);
			}
		}
	}

	@EventHandler
	void onPlayerCraft(PrepareItemCraftEvent event){
		ItemStack[] itemStacks = event.getInventory().getMatrix();
		for (ItemStack item : itemStacks){
			if (crysknifeFunctions.isItemCrysknife(item)){
				event.getInventory().setResult(null);
			}
		}
	}

	@EventHandler
	void onPlayerEnchant(PrepareItemEnchantEvent event){
		ItemStack itemStack = event.getItem();
		if (crysknifeFunctions.isItemCrysknife(itemStack)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	void onPlayerSmithing(PrepareSmithingEvent event){
		ItemStack itemStack = event.getInventory().getInputEquipment();
		if (crysknifeFunctions.isItemCrysknife(itemStack)){
			event.setResult(null);
		}
	}

	public static void dropArmorItem(ItemStack armorItem, LivingEntity defender){
		Random rand = new Random();
		if (armorItem == null || armorItem.getType() == Material.AIR){return;}
		if (!(defender instanceof Player)){
			playScaryNoises(defender);
			double dropChance = rand.nextDouble();
			double resultToGoUnder = 1.0;
			if (diamondArmor.contains(armorItem.getType())){
				resultToGoUnder = 0.005;
			} else if (netheriteArmor.contains(armorItem.getType())){
				resultToGoUnder = 0.0001;
			}
			if (dropChance <= resultToGoUnder){
				ItemStack itemToDrop = new ItemStack(armorItem.getType());
				Damageable toDropDamage = (Damageable) itemToDrop.getItemMeta();
				int damage = itemToDrop.getType().getMaxDurability() - rand.nextInt(5) + 1;
				Main.sendDebugMessage("Damage to set: " + damage,crysknifeDebug);
				toDropDamage.setDamage(damage);
				itemToDrop.setItemMeta(toDropDamage);
				defender.getWorld().dropItemNaturally(defender.getLocation(),itemToDrop);
			}

		} else {
			Damageable damageable = (Damageable) armorItem.getItemMeta();
			armorItem.setItemMeta(damageable);
			defender.getWorld().dropItemNaturally(defender.getLocation(),armorItem);
			playScaryNoises(defender);
		}

	}

	public static void playScaryNoises(LivingEntity livingEntity){
		livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ITEM_SHIELD_BREAK, 1.35f, 0.55f);
		livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.35f, 0.55f);
	}
}
