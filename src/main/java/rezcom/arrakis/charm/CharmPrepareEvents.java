package rezcom.arrakis.charm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.Main;

public class CharmPrepareEvents implements Listener {

    public static boolean cancelCharmDebug = false;

    // Prevent a player from enchanting
    @EventHandler
    void onPlayerEnchant(PrepareItemEnchantEvent event){
        Main.sendDebugMessage("Charm Enchanting event attempt",cancelCharmDebug);
        ItemStack itemStack = event.getItem();
        if (charmFunctions.isItemMuaddibCharm(itemStack)){
            event.setCancelled(true);
            Main.sendDebugMessage("Charm Enchanting event triggered",cancelCharmDebug);
        }
    }

    // Prevent a player from using crafting
    @EventHandler
    void onPlayerCraft(PrepareItemCraftEvent event){
        Main.sendDebugMessage("Charm Crafting event attempt",cancelCharmDebug);
        ItemStack[] itemStacks = event.getInventory().getMatrix();
        for (ItemStack item : itemStacks){
            if (charmFunctions.isItemMuaddibCharm(item)){
                event.getInventory().setResult(null);
                Main.sendDebugMessage("Charm Crafting event triggered",cancelCharmDebug);
            }
        }
    }

    @EventHandler
    void onPlayerSmithing(PrepareSmithingEvent event){
        Main.sendDebugMessage("Charm Smithing event attempt",cancelCharmDebug);
        ItemStack itemStack = event.getInventory().getInputEquipment();
        if (charmFunctions.isItemMuaddibCharm(itemStack)){
            event.setResult(null);
            Main.sendDebugMessage("Charm Smithing event triggered",cancelCharmDebug);
        }
    }

    @EventHandler
    void onPlayerAnvil(PrepareAnvilEvent event){
        Main.sendDebugMessage("Charm Anvil event attempt",cancelCharmDebug);

        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();

        if (charmFunctions.isItemMuaddibCharm(firstItem)){
            event.setResult(null);
            Main.sendDebugMessage("Charm Anvil event triggered",cancelCharmDebug);
        } else if (charmFunctions.isItemMuaddibCharm(secondItem)){
            event.setResult(null);
            Main.sendDebugMessage("Charm Anvil event triggered",cancelCharmDebug);
        }
    }
}
