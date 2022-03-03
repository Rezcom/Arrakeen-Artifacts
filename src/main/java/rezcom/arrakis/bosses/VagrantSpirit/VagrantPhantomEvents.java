package rezcom.arrakis.bosses.VagrantSpirit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rezcom.arrakis.Main;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class VagrantPhantomEvents implements Listener {

	public static final int maxPhantoms = 5;
	public static boolean phantomDebug = true;

	private static final Component phantomName =
			Component.text("Vagrant Phantom").color(TextColor.color(0x235e78)).decoration(TextDecoration.ITALIC,false);

	private static final Component phantomIdentifier =
			Component.text("Vagrant Phantom").color(TextColor.color(0x235e78)).decoration(TextDecoration.ITALIC,false);

	@EventHandler
	void onPhantomHit(EntityDamageByEntityEvent event){
		if (!(isEntityVagrantPhantom(event.getDamager())) || !(event.getEntity() instanceof Player)){
			// Not a vagrant phantom attack on a player
			return;
		}
		Main.sendDebugMessage("A vagrant phantom hit a player",phantomDebug);
		Player player = (Player) event.getEntity();
		Random random = new Random();

		if (random.nextDouble() <= 0.66){
			player.setFireTicks(110);
		}
		PotionEffect fatigue = new PotionEffect(PotionEffectType.SLOW_DIGGING,160,255);
		if (random.nextDouble() <= 0.75){
			player.addPotionEffect(fatigue);
		}
	}

	@EventHandler
	void onPhantomDeath(EntityDeathEvent event){
		if (!(isEntityVagrantPhantom(event.getEntity()))){
			return;
		}
		event.setDroppedExp(0);
		event.getDrops().clear();
	}

	public static void spawnPhantom(World world, Location location){
		// Spawns a phantom but only if there's less than a certain
		// amount.
		if (reachedPhantomLimit(location)){
			// Can't spawn anymore! Max reached.
			Main.sendDebugMessage("Reached phantom limit",phantomDebug);
			return;
		}

		LivingEntity mob = (LivingEntity) world.spawnEntity(location, EntityType.PHANTOM);
		Phantom phantom = (Phantom) mob;

		phantom.customName(phantomName);
		phantom.setCustomNameVisible(false);
		phantom.setCanPickupItems(false);
		phantom.setPersistent(true);
		phantom.setCollidable(false);
		phantom.setRemoveWhenFarAway(false);
		phantom.setAI(true);

		ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.lore(Collections.singletonList(phantomIdentifier));
		chestplate.setItemMeta(chestplateMeta);

		EntityEquipment entityEquipment = phantom.getEquipment();
		entityEquipment.setChestplate(chestplate);

		entityEquipment.setChestplateDropChance(0.0f);

		PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,3);
		PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE,Integer.MAX_VALUE,2);

		phantom.addPotionEffect(resist);
		phantom.addPotionEffect(speed);
		phantom.addPotionEffect(strength);

		Collection<Player> nearbyPlayers = phantom.getLocation().getNearbyPlayers(50);
		Player targetPlayer = randItemFromColl(nearbyPlayers);

		phantom.setTarget(targetPlayer);
		Main.sendDebugMessage("TARGET PLAYER: " + targetPlayer.name(),phantomDebug);
	}

	private static boolean reachedPhantomLimit(Location loc){
		int sum = 0;
		for (Entity entity : loc.getNearbyLivingEntities(50)){
			if (isEntityVagrantPhantom(entity)){
				sum += 1;
			}
		}
		return sum >= VagrantPhantomEvents.maxPhantoms;
	}

	public static <T> T randItemFromColl(Collection<T> collection){
		int num = (int) (Math.random() * collection.size());
		for (T p: collection) if (--num < 0) return p;
		throw new AssertionError();
	}

	public static boolean isEntityVagrantPhantom(Entity entity){
		if (!(entity instanceof Phantom)){return false;}
		Phantom phantom = (Phantom) entity;
		EntityEquipment entityEquipment = phantom.getEquipment();
		ItemStack chestplate = entityEquipment.getChestplate();
		if (chestplate == null || chestplate.getType() != Material.NETHERITE_CHESTPLATE){return false;}
		if (!chestplate.hasItemMeta() || !chestplate.getItemMeta().hasLore()){return false;}
		return Objects.requireNonNull(chestplate.getItemMeta().lore()).contains(phantomIdentifier);
	}
}
