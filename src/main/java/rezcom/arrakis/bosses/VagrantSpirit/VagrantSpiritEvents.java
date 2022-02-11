package rezcom.arrakis.bosses.VagrantSpirit;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rezcom.arrakis.crysknife.crysknifeEvents;
import rezcom.arrakis.fragments.soulFragmentFunctions;
import rezcom.arrakis.stillsuit.stillsuitFunctions;

import java.util.Random;

public class VagrantSpiritEvents implements Listener {

	@EventHandler
	void onVagrantArrowHit(EntityDamageByEntityEvent event){
		if (!(VagrantSpiritCommand.isMobVagrantSpirit(event.getEntity()))){
			return;
		}
		if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE || event.getCause() == EntityDamageEvent.DamageCause.FALL){
			event.setCancelled(true);
		} else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
			event.setDamage(event.getDamage() / 4);
		}
	}

	@EventHandler
	void onVagrantHitChangeAggro(EntityDamageByEntityEvent event){
		if (!(VagrantSpiritCommand.isMobVagrantSpirit(event.getEntity())) || !(event.getDamager() instanceof Player)){
			return;
		}

		Player player = (Player) event.getDamager();
		Husk husk = (Husk) event.getEntity();
		Random random = new Random();
		if (random.nextDouble() <= 0.33){
			husk.setTarget(player);
		}

	}

	@EventHandler
	void onVagrantDamage(EntityDamageByEntityEvent event){
		if (!(VagrantSpiritCommand.isMobVagrantSpirit(event.getDamager())) || (!(event.getEntity() instanceof Player)) || event.getCause() == EntityDamageEvent.DamageCause.THORNS){
			// Not a vagrant spirit attack, or defender isn't a player, or thorns damage proc
			return;
		}

		Player player = (Player) event.getEntity();
		EntityEquipment playerEquipment = player.getEquipment();
		playerEquipment.getItemInOffHand();
		Random random = new Random();

		if (random.nextDouble() <= 0.25){
			// Create Potion Itemstack and meta
			ItemStack thrownItem = new ItemStack(Material.SPLASH_POTION);
			PotionMeta potionMeta = (PotionMeta) thrownItem.getItemMeta();
			// Create harming effect and apply meta
			PotionEffect potionEffect = new PotionEffect(PotionEffectType.HARM,1,2);
			thrownItem.setItemMeta(potionMeta);
			// Apply ItemStack to a thrown entity splash potion
			Entity potion = event.getDamager().getWorld().spawnEntity(event.getDamager().getLocation(), EntityType.SPLASH_POTION);
			ThrownPotion thrownPotion = (ThrownPotion) potion;
			thrownPotion.setItem(thrownItem);
		}

		if (playerEquipment.getItemInOffHand().getType() == Material.SHIELD){
			if (random.nextDouble() <= 0.85){
				crysknifeEvents.dropArmorItem(playerEquipment.getItemInOffHand(),player);
				playerEquipment.setItemInOffHand(null);
			}
		} else if (playerEquipment.getItemInMainHand().getType() == Material.SHIELD){
			if (random.nextDouble() <= 0.85){
				crysknifeEvents.dropArmorItem(playerEquipment.getItemInMainHand(),player);
				playerEquipment.setItemInMainHand(null);
			}
		}

		if (random.nextDouble() <= 0.16){
			PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 60,5);
			player.addPotionEffect(weakness);
		}

		if (random.nextDouble() <= 0.85){
			// REMOVE EQUIPMENT!
			double whichArmorResult = random.nextDouble();
			if (whichArmorResult <= 0.25){
				// Remove helmet
				ItemStack helmet = playerEquipment.getHelmet();
				if (helmet == null || helmet.getType() == Material.AIR){return;}
				crysknifeEvents.dropArmorItem(playerEquipment.getHelmet(), player);
				playerEquipment.setHelmet(null);
			} else if (whichArmorResult <= 0.50){
				// Remove chestplate
				ItemStack chestplate = playerEquipment.getChestplate();
				if (chestplate == null || chestplate.getType() == Material.AIR){return;}
				crysknifeEvents.dropArmorItem(playerEquipment.getChestplate(), player);
				playerEquipment.setChestplate(null);
			} else if (whichArmorResult <= 0.75){
				// Remove leggings
				ItemStack leggings = playerEquipment.getLeggings();
				if (leggings == null || leggings.getType() == Material.AIR){return;}
				crysknifeEvents.dropArmorItem(playerEquipment.getLeggings(),player);
				playerEquipment.setLeggings(null);
			} else {
				// Remove boots
				ItemStack boots = playerEquipment.getBoots();
				if (boots == null || boots.getType() == Material.AIR){return;}
				crysknifeEvents.dropArmorItem(playerEquipment.getBoots(),player);
				playerEquipment.setBoots(null);
			}
			crysknifeEvents.playScaryNoises(player);
		}
	}

	@EventHandler
	void onHuskDeath(EntityDeathEvent event){
		if (!(event.getEntity() instanceof Husk) || !VagrantSpiritCommand.isMobVagrantSpirit(event.getEntity())){
			return;
		}
		event.getDrops().clear();
		event.getDrops().add(stillsuitFunctions.stillsuitItemStack);
		event.getDrops().add(soulFragmentFunctions.fragmentBStack);
	}
}
