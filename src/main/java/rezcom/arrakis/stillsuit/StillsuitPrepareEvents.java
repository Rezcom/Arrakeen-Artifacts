package rezcom.arrakis.stillsuit;

import com.destroystokyo.paper.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.Main;

public class StillsuitPrepareEvents implements Listener {

    // These functions completely cancel the crafting,
    // enchanting, anvil usage, etc of items who use these
    // methods.

    public static boolean cancelStillsuitDebug = false;

    // Prevent a player from enchanting
    @EventHandler
    void onPlayerEnchant(PrepareItemEnchantEvent event){
        ItemStack itemStack = event.getItem();
        if (stillsuitFunctions.isItemStillsuit(itemStack)){
            event.setCancelled(true);
            Main.sendDebugMessage("Stillsuit Enchanting event triggered",cancelStillsuitDebug);
        }
    }

    // Prevent a player from using crafting
    @EventHandler
    void onPlayerCraft(PrepareItemCraftEvent event){
        ItemStack[] itemStacks = event.getInventory().getMatrix();
        for (ItemStack item : itemStacks){
            if (item != null && stillsuitFunctions.isItemStillsuit(item)){
                event.getInventory().setResult(null);
                Main.sendDebugMessage("Stillsuit Crafting event triggered",cancelStillsuitDebug);
            }
        }
    }

    // Prevent a player from smithing
    @EventHandler
    void onPlayerSmithing(PrepareSmithingEvent event){
        ItemStack itemStack = event.getInventory().getInputEquipment();
        if (itemStack != null && stillsuitFunctions.isItemStillsuit(itemStack)){
            event.setResult(null);
            Main.sendDebugMessage("Stillsuit Smithing event triggered",cancelStillsuitDebug);
        }
    }

    // Prevent a player from using anvil
    @EventHandler
    void onPlayerAnvil(PrepareAnvilEvent event){

        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();

        if (firstItem != null && stillsuitFunctions.isItemStillsuit(firstItem)){
            event.setResult(null);
            Main.sendDebugMessage("Stillsuit Anvil Event Triggered",cancelStillsuitDebug);
        } else if (secondItem != null && stillsuitFunctions.isItemStillsuit(secondItem)){
            event.setResult(null);
            Main.sendDebugMessage("Stillsuit Anvil Event Triggered",cancelStillsuitDebug);
        }

    }

    // Prevent a player from using grindstone
    // Deprecated..?
    /*@EventHandler
    void onPlayerGrindstone(PrepareGrindstoneEvent event){


        Main.sendDebugMessage("Grindstone event triggered",cancelDebug);
        GrindstoneInventory grindstoneInventory = event.getInventory();
        ItemStack upperItem = grindstoneInventory.getUpperItem();
        ItemStack lowerItem = grindstoneInventory.getLowerItem();

        for (Component c : Main.arrakeenIdentifiers){
            // Check upper item
            if (upperItem != null && upperItem.hasItemMeta()){
                ItemMeta firstMeta = upperItem.getItemMeta();
                if (firstMeta.hasLore() && firstMeta.lore().contains(c)){
                    event.setResult(null);
                    Main.sendDebugMessage("Grindstone Event Result set to null",cancelDebug);
                    return;
                }
            }

            // Check lower item
            if (lowerItem != null && lowerItem.hasItemMeta()){
                ItemMeta secondMeta = lowerItem.getItemMeta();
                if (secondMeta.hasLore() && secondMeta.lore().contains(c)){
                    event.setResult(null);
                    Main.sendDebugMessage("Grindstone Event Result set to null",cancelDebug);
                    return;
                }
            }
        }
    }*/
}
