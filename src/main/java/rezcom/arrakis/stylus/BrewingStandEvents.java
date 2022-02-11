package rezcom.arrakis.stylus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BrewingStandEvents implements Listener {

	public static boolean brewDebug = false;

	private static final Component playerDupeMessage =
			Component.text("An additional potion was received.").color(TextColor.color(0x0CCF2A)).decoration(TextDecoration.ITALIC,false);

	Map<String, Boolean> allBrewingStands = new HashMap<>();

	@EventHandler
	void onBrew(BrewEvent event){

		BrewerInventory brewerInventory = event.getContents();
		BrewingStand brewingStand = brewerInventory.getHolder();
		if (brewingStand == null){ return; }

		// Check if the hashmap has the unique brewing stand
		// Add if not, and make it true so people can get a dupe chance
		updateHashmap(brewingStand, true);
		// False - Not allowed to get an extra potion
		// True - Allowed to get an extra potion

	}

	@EventHandler
	void onPlayerClick(InventoryClickEvent event){
		// When the player clicks inside a brewing stand
		// Register if it's not already. See if dupe chance is allowed
		if (!(event.getClickedInventory() instanceof  BrewerInventory)){
			return;
		}

		BrewerInventory brewerInventory = (BrewerInventory) event.getClickedInventory();
		ItemStack itemStack = event.getCurrentItem();
		ItemStack cursor = event.getCursor();
		Main.sendDebugMessage("getCurrent: " + itemStack,brewDebug);
		Main.sendDebugMessage("getCursor: " + cursor, brewDebug);
		if (brewerInventory == null || itemStack == null || cursor == null){return;}
		BrewingStand brewingStand = brewerInventory.getHolder();
		if (brewingStand == null){return;}

		if (itemStack.getType() != Material.AIR && isAllowedDupe(brewingStand)){
			// Dupe chance is allowed
			Main.sendDebugMessage("DUPE ALLOWED!", brewDebug);
			dupeChance(event, itemStack);
			updateHashmap(brewingStand, false);
		} else if (cursor.getType() != Material.AIR){
			Main.sendDebugMessage("Dupe chance miss!", brewDebug);
			updateHashmap(brewingStand, false);
		}
		// Brewing stand isn't updated
	}

	private void dupeChance(InventoryClickEvent event, ItemStack potion){
		// Processes dupe chance for the event
		if (!(event.getWhoClicked() instanceof Player)){
			return;
		}
		Player player = (Player) event.getWhoClicked();
		int playerLevel = player.getLevel();

		if (!stylusFunctions.isPlayerHoldingStylus(player)){
			Main.sendDebugMessage("Player wasn't holding stylus",brewDebug);
			return;
		}

		Random rand = new Random();
		double chanceLevel = (playerLevel >= 25 ? 0.25 : 0.10);
		Main.sendDebugMessage("NUM TO GO UNDER: " + chanceLevel,brewDebug);
		if (rand.nextDouble() <= chanceLevel){
			//player.setLevel(playerLevel - 10);

			player.playSound(player.getLocation(), Sound.ITEM_SPYGLASS_USE,0.8f,0.4f);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,0.25f,1.5f);
			player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.2f, 0.2f);
			player.playSound(player.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 0.5f, 0.55f);

			player.getWorld().dropItem(player.getLocation(),potion);
			player.sendMessage(playerDupeMessage);
		}
	}

	// Registers new brewing stand
	@EventHandler
	void onPlaceBrewingStand(BlockPlaceEvent event){
		Block block = event.getBlockPlaced();
		Material material = block.getType();
		if (material != Material.BREWING_STAND){
			return;
		}

		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		allBrewingStands.put("" + x + y + z, false);
		Main.sendDebugMessage("BLOCK PLACED: New brewing stand registered\nNUM: " + allBrewingStands.size(),brewDebug);
	}

	// When a player breaks a brewing stand
	// Removes brewing stand from hashmap
	@EventHandler
	void onBreakBrewingStand(BlockBreakEvent event){
		Block block = event.getBlock();
		Material material = block.getType();
		if (material != Material.BREWING_STAND){
			return;
		}

		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		allBrewingStands.remove("" + x + y + z);
		Main.sendDebugMessage("BLOCK BROKE: Removed brewing stand\nNUM: " + allBrewingStands.size(),brewDebug);
	}



	// Prevent a player from enchanting
	@EventHandler
	void onPlayerEnchant(PrepareItemEnchantEvent event){
		ItemStack itemStack = event.getItem();
		if (stylusFunctions.isItemBeneGesseritStylus(itemStack)){
			event.setCancelled(true);
		}
	}

	// Prevent a player from using crafting
	@EventHandler
	void onPlayerCraft(PrepareItemCraftEvent event){
		ItemStack[] itemStacks = event.getInventory().getMatrix();
		for (ItemStack item : itemStacks){
			if (stylusFunctions.isItemBeneGesseritStylus(item)){
				event.getInventory().setResult(null);
			}
		}
	}

	// Prevent a player from smithing
	@EventHandler
	void onPlayerSmithing(PrepareSmithingEvent event){
		ItemStack itemStack = event.getInventory().getInputEquipment();
		if (stylusFunctions.isItemBeneGesseritStylus(itemStack)){
			event.setResult(null);
		}
	}

	// Prevent a player from using anvil
	@EventHandler
	void onPlayerAnvil(PrepareAnvilEvent event){

		AnvilInventory inventory = event.getInventory();
		ItemStack firstItem = inventory.getFirstItem();
		ItemStack secondItem = inventory.getSecondItem();
		if (stylusFunctions.isItemBeneGesseritStylus(firstItem) || stylusFunctions.isItemBeneGesseritStylus(secondItem)){
			event.setResult(null);
		}
	}

	private String convertToStringKey(BrewingStand brewingStand){
		// Given a brewing stand, gets its XYZ location and turns it into a String that
		// can be used as a key for the hashmap of all brewing stands.
		Location brewingStandLoc = brewingStand.getLocation();
		int x = brewingStandLoc.getBlockX();
		int y = brewingStandLoc.getBlockY();
		int z = brewingStandLoc.getBlockZ();
		return "" + x + y + z;
	}

	private boolean isAllowedDupe(BrewingStand brewingStand){
		String stringKey = convertToStringKey(brewingStand);
		if (!(allBrewingStands.containsKey(stringKey))){
			// Brewing stand isn't even registered.
			updateHashmap(brewingStand, false);
			Main.sendDebugMessage("Brewing Stand isn't registered, registering...\nNUM: " + allBrewingStands.size(), brewDebug);
			return false;
		}
		return allBrewingStands.get(stringKey);
	}
	private void updateHashmap(BrewingStand brewingStand, Boolean bool){
		// Hello
		// World
		String stringKey = convertToStringKey(brewingStand);
	    if (allBrewingStands.containsKey(stringKey)){
	        // Update it with bool
	        allBrewingStands.replace(stringKey, bool);
	        Main.sendDebugMessage("Updated already existing stand to " + bool + "\nNUM: " + allBrewingStands.size(), brewDebug);
	    } else {
	        allBrewingStands.put(stringKey, bool);
	        Main.sendDebugMessage("Added new brewing stand\nNUM: " + allBrewingStands.size(),brewDebug);
	    }
	}
}




