package rezcom.arrakis.dilapidatedbow;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class dilapidatedBowEvents implements Listener {

	private static final Component playerMessage =
			Component.text("Your weapon cannot shoot any longer! It must be replenished.").color(TextColor.color(0xf86363)).decoration(TextDecoration.ITALIC,true);

	@EventHandler
	void onPlayerShoot(EntityShootBowEvent event){
		if (!(event.getEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getEntity();
		ItemStack bow = event.getBow();
		if (!dilapidatedBowFunc.isItemDilapidatedBow(bow)){
			return;
		}
		Material material = bow.getType();
		Damageable damageable = (Damageable) bow.getItemMeta();
		if (damageable.getDamage() == material.getMaxDurability() - 1){
			event.setCancelled(true);
			player.sendMessage(playerMessage);

		}

	}

	@EventHandler
	void onPlayerAnvil(PrepareAnvilEvent event){
		AnvilInventory anvilInventory = event.getInventory();
		ItemStack firstItem = anvilInventory.getFirstItem();
		ItemStack secondItem = anvilInventory.getSecondItem();

		if (dilapidatedBowFunc.isItemDilapidatedBow(secondItem)){
			event.setResult(null);
			return;
		}
		if (dilapidatedBowFunc.isItemDilapidatedBow(firstItem)){
			if (secondItem == null || (!(secondItem.getType() == Material.NETHERITE_BLOCK && secondItem.getAmount() == 1))){
				event.setResult(null);
			} else {
				event.setResult(dilapidatedBowFunc.bowItemStack);
			}

		}

	}

	// Players shouldn't be able to craft with it. and all that
	@EventHandler
	void onPlayerCraft(PrepareItemCraftEvent event){
		ItemStack[] itemStacks = event.getInventory().getMatrix();
		for (ItemStack item : itemStacks){
			if (dilapidatedBowFunc.isItemDilapidatedBow(item)){
				event.getInventory().setResult(null);
			}
		}
	}

	@EventHandler
	void onPlayerEnchant(PrepareItemEnchantEvent event){
		ItemStack itemStack = event.getItem();
		if (dilapidatedBowFunc.isItemDilapidatedBow(itemStack)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	void onPlayerSmithing(PrepareSmithingEvent event){
		ItemStack itemStack = event.getInventory().getInputEquipment();
		if (dilapidatedBowFunc.isItemDilapidatedBow(itemStack)){
			event.setResult(null);
		}
	}


}
