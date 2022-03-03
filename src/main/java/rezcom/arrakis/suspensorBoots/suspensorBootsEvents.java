package rezcom.arrakis.suspensorBoots;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class suspensorBootsEvents implements Listener {

	public static boolean bootsDebug = true;

	@EventHandler
	void onPlayerFall(EntityDamageEvent event){
		if (!(event.getEntity() instanceof Player) || !(event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)){
			// Should only modify if a player took fall damage or
			// flew into a wall.
			return;
		}
		Player player = (Player) event.getEntity();
		if (!suspensorBootsFunctions.isPlayerWearingSuspensor(player) || player.getLevel() < 15){return;}

		ItemStack bootsStack = player.getEquipment().getBoots();
		if (bootsStack == null || !bootsStack.hasItemMeta()){return;}

		Damageable bootsDamage = (Damageable) bootsStack.getItemMeta();
		Material material = bootsStack.getType();
		int breakingPoint = material.getMaxDurability() - 1;
		if (bootsDamage.getDamage() == breakingPoint){
			return;
		}

		double damage = event.getDamage();
		if (damage <= 2){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 1, breakingPoint));
		} else if (damage <= 4){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 2, breakingPoint));
		} else if (damage <= 6){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 4, breakingPoint));
		} else if (damage <= 8){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 6, breakingPoint));
		} else if (damage <= 10){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 8, breakingPoint));
		} else if (damage <= 12){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 10, breakingPoint));
		} else if (damage <= 14){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 12, breakingPoint));
		} else if (damage <= 16){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 14, breakingPoint));
		} else if (damage <= 18){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 16, breakingPoint));
		} else if (damage <= 24){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 20, breakingPoint));
		} else if (damage <= 30){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 25, breakingPoint));
		} else if (damage <= 36){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 30, breakingPoint));
		} else if (damage <= 40){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 36, breakingPoint));
		} else if (damage <= 44){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 42, breakingPoint));
		} else if (damage > 44){
			bootsDamage.setDamage(Math.min(bootsDamage.getDamage() + 50, breakingPoint));
		}

		bootsStack.setItemMeta(bootsDamage);

		World world = player.getWorld();
		Location loc = player.getLocation();
		if (damage > 8 && damage <= 12){
			world.createExplosion(player,loc,2f,false,false);
		} else if (damage > 12 && damage <= 14){
			world.createExplosion(player,loc,2f,true,true);
		} else if (damage > 14 && damage <= 18){
			world.createExplosion(player,loc,3f,true,true);
		} else if (damage > 18 && damage <= 24){
			world.createExplosion(player,loc,4f,true,true);
		} else if (damage > 24 && damage <= 28){
			world.createExplosion(player,loc,5f,true,true);
		} else if (damage > 28 && damage <= 32){
			world.createExplosion(player,loc,6f,true,true);
		} else if (damage > 32){
			world.createExplosion(player,loc,7f,true,true);
		}
		world.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT,1.2f,0.55f);
		if (damage >= 20){
			world.playSound(loc,Sound.ENTITY_PIGLIN_BRUTE_DEATH,0.85f,0.3f);
			world.playSound(loc,Sound.ITEM_FLINTANDSTEEL_USE,1.2f,0.55f);
		}
		event.setDamage(0.0);
	}

	@EventHandler
	void onPlayerAnvil(PrepareAnvilEvent event){
		AnvilInventory anvilInventory = event.getInventory();
		ItemStack firstItem = anvilInventory.getFirstItem();
		ItemStack secondItem = anvilInventory.getSecondItem();

		if (suspensorBootsFunctions.isItemSuspensorBoots(secondItem)){
			event.setResult(null);
			return;
		}

		if (suspensorBootsFunctions.isItemSuspensorBoots(firstItem)){
			if (secondItem == null || (!(secondItem.getType() == Material.ENDER_EYE)) || secondItem.getAmount() != 1){
				event.setResult(null);
			} else {
				event.setResult(suspensorBootsFunctions.bootsStack);
				anvilInventory.setRepairCost(10);
			}
		}
	}

	@EventHandler
	void onPlayerCraft(PrepareItemCraftEvent event){
		ItemStack[] itemStacks = event.getInventory().getMatrix();
		for (ItemStack item : itemStacks){
			if (suspensorBootsFunctions.isItemSuspensorBoots(item)){
				event.getInventory().setResult(null);
			}
		}
	}

	@EventHandler
	void onPlayerEnchant(PrepareItemEnchantEvent event){
		ItemStack item = event.getItem();
		if (suspensorBootsFunctions.isItemSuspensorBoots(item)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	void onPlayerSmithing(PrepareSmithingEvent event){
		ItemStack itemStack = event.getInventory().getInputEquipment();
		if (suspensorBootsFunctions.isItemSuspensorBoots(itemStack)){
			event.setResult(null);
		}
	}
}
