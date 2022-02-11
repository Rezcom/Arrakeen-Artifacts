package rezcom.arrakis.fragments;

import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.crysknife.crysknifeFunctions;
import rezcom.arrakis.dilapidatedbow.dilapidatedBowFunc;

public class ValidAnvilFragments {

	public static void validateAnvil(PrepareAnvilEvent event){
		// This function assumes that the first item is
		// a fragment.

		AnvilInventory anvilInventory = event.getInventory();
		ItemStack firstItem = anvilInventory.getFirstItem();
		ItemStack secondItem = anvilInventory.getSecondItem();
		if (!soulFragmentFunctions.isFragment(firstItem)){return;}
		if (soulFragmentFunctions.isFragmentA(firstItem)){
			if (soulFragmentFunctions.isFragmentB(secondItem)){
				event.setResult(crysknifeFunctions.crysknifeStack);
				anvilInventory.setRepairCost(35);
				return;
			}
			event.setResult(null);
		}
		event.setResult(null);
	}
}
