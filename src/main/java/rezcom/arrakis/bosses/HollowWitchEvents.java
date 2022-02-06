package rezcom.arrakis.bosses;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import rezcom.arrakis.Main;
import rezcom.arrakis.bosses.HollowWitchCommand;
import rezcom.arrakis.dilapidatedbow.dilapidatedBowFunc;
import rezcom.arrakis.fragments.soulFragmentFunctions;

import java.util.List;
import java.util.Random;

public class HollowWitchEvents implements Listener {

	public static boolean hollowDeathDebug = false;

	private static boolean canShootAgain = true;

	@EventHandler
	void onStrayShoot(EntityShootBowEvent event){
		// When the stray shoots, splash potion of harming
		if (!(event.getEntity() instanceof Stray)){
			return;
		}
		Stray stray = (Stray) event.getEntity();

		ItemStack bow = stray.getEquipment().getItemInMainHand();
		if (!bow.hasItemMeta()){
			return;
		}

		ItemMeta bowMeta = bow.getItemMeta();
		// If the stray is holding the special bow
		if (!(bowMeta.hasLore() && bowMeta.lore().contains(HollowWitchCommand.customBowIdentifier))){
			return;
		}
		// Extra shoots
		if (canShootAgain){
			canShootAgain = false; // Extra shot lock

			Random random = new Random();
			if (random.nextDouble() <= 0.1){
				// Create Potion ItemStack and Meta
				ItemStack thrownItem = new ItemStack(Material.SPLASH_POTION);
				PotionMeta potionMeta = (PotionMeta) thrownItem.getItemMeta();
				// Create harming effect and apply meta
				PotionEffect potionEffect = new PotionEffect(PotionEffectType.HARM,1,1);
				potionMeta.addCustomEffect(potionEffect,true);
				thrownItem.setItemMeta(potionMeta);
				// Apply ItemStack to a thrown entity splash potion
				Entity potion = stray.getWorld().spawnEntity(stray.getLocation(), EntityType.SPLASH_POTION);
				ThrownPotion thrownPotion = (ThrownPotion) potion;
				thrownPotion.setItem(thrownItem);
			}

			List<Entity> near = stray.getNearbyEntities(100.0D,100.0D,100.0D);
			for (Entity entity : near){
				if (entity instanceof Player){
					new BukkitRunnable(){
						@Override
						public void run(){
							cancel();
						}
					}.runTaskTimer(Main.thisPlugin, 40L,40L);
					stray.rangedAttack((LivingEntity) entity, 1.0f);
					new BukkitRunnable(){
						@Override
						public void run(){
							cancel();
						}
					}.runTaskTimer(Main.thisPlugin, 40L,40L);
					stray.rangedAttack((LivingEntity) entity, 1.0f);
				}
			}
			canShootAgain = true;
		}

	}

	@EventHandler
	void onStrayDeath(EntityDeathEvent event){
		if (!(event.getEntity() instanceof Stray)){
			return;
		}
		Main.sendDebugMessage("Stray died",hollowDeathDebug);
		Stray stray = (Stray) event.getEntity();
		ItemStack bow = stray.getEquipment().getItemInMainHand();
		if (!bow.hasItemMeta()){
			return;
		}
		ItemMeta meta = bow.getItemMeta();

		if (meta.hasLore() && meta.lore().contains(HollowWitchCommand.customBowIdentifier)){
			Main.sendDebugMessage("Was custom bow",hollowDeathDebug);
			event.getDrops().clear();
			event.getDrops().add(soulFragmentFunctions.fragmentAStack);
			event.getDrops().add(dilapidatedBowFunc.bowItemStack);
		} else {
			Main.sendDebugMessage("Wasn't custom bow or doesn't contain lore",hollowDeathDebug);
		}
	}

	@EventHandler
	void onWitchKnockback(EntityKnockbackByEntityEvent event){
		if (!(event.getEntity() instanceof Stray)){
			return;
		}
		Stray stray = (Stray) event.getEntity();
		ItemStack bow = stray.getEquipment().getItemInMainHand();
		if (!bow.hasItemMeta()){
			return;
		}
		ItemMeta meta = bow.getItemMeta();

		if (meta.hasLore() && meta.lore().contains(HollowWitchCommand.customBowIdentifier)){
			event.setCancelled(true);
		}
	}


}
