package rezcom.arrakis.fragments;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class soulFragmentEvents implements Listener {
	// Prepare events, cancel em! We don't want players renaming em etc
	@EventHandler
	void onPlayerCraft(PrepareItemCraftEvent event){
		ItemStack[] itemStacks = event.getInventory().getMatrix();
		for (ItemStack item : itemStacks){
			if (soulFragmentFunctions.isFragmentA(item)){
				event.getInventory().setResult(null);
			}
		}
	}

	@EventHandler
	void onPlayerEnchant(PrepareItemEnchantEvent event){
		ItemStack itemStack = event.getItem();
		if (soulFragmentFunctions.isFragmentA(itemStack)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	void onPlayerAnvil(PrepareAnvilEvent event){
		AnvilInventory inventory = event.getInventory();
		ItemStack firstItem = inventory.getFirstItem();
		ItemStack secondItem = inventory.getSecondItem();

		if (!soulFragmentFunctions.isFragment(firstItem) && soulFragmentFunctions.isFragment(secondItem)){
			// First item isn't a fragment, but second one is.
			// Nothing happens.
			event.setResult(null);

		} else if (soulFragmentFunctions.isFragment(firstItem) || soulFragmentFunctions.isFragment(secondItem)){
			// First item is a fragment.
			ValidAnvilFragments.validateAnvil(event);
		}

	}
}
