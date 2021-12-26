package rezcom.arrakis;

import com.destroystokyo.paper.event.inventory.PrepareGrindstoneEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class CancelResultEvents implements Listener {

    // Arrakeen items should not be crafted with, enchanted, used on
    // a grindstone, etc. These event handlers should cancel them.

    public static boolean cancelDebug = false;

    // Prevent a player from enchanting
    @EventHandler
    void onPlayerEnchant(PrepareItemEnchantEvent event){
        Main.sendDebugMessage("Enchanting event triggered",cancelDebug);
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Component c : Main.arrakeenIdentifiers){
            if (Objects.requireNonNull(itemMeta.lore()).contains(c)){
                Main.sendDebugMessage("Enchanting event set to null",cancelDebug);
                event.setCancelled(true);
                return;
            }
        }
    }

    // Prevent a player from using crafting
    @EventHandler
    void onPlayerCraft(PrepareItemCraftEvent event){
        Main.sendDebugMessage("Crafting event triggered",cancelDebug);
        ItemStack[] itemStacks = event.getInventory().getMatrix();
        for (ItemStack item : itemStacks){
            if (item != null && item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();
                for (Component c : Main.arrakeenIdentifiers){
                    if (itemMeta.hasLore() && itemMeta.lore().contains(c)){
                        Main.sendDebugMessage("Crafting event result set to null",cancelDebug);
                        event.getInventory().setResult(null);
                    }
                }
            }
        }
    }

    // Prevent a player from smithing
    @EventHandler
    void onPlayerSmithing(PrepareSmithingEvent event){
        Main.sendDebugMessage("Smithing event triggered",cancelDebug);
        ItemStack itemStack = event.getInventory().getInputEquipment();
        if (itemStack != null && itemStack.hasItemMeta()){
            ItemMeta itemMeta = itemStack.getItemMeta();
            for (Component c : Main.arrakeenIdentifiers){
                if (itemMeta.hasLore() && itemMeta.lore().contains(c)){
                    Main.sendDebugMessage("Smithing event set to null",cancelDebug);
                    event.setResult(null);
                }
            }
        }
    }

    // Prevent a player from using anvil
    @EventHandler
    void onPlayerAnvil(PrepareAnvilEvent event){
        Main.sendDebugMessage("Anvil Event Triggered",cancelDebug);
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();

        for (Component c : Main.arrakeenIdentifiers){
            // Check first item
            if (firstItem != null && firstItem.hasItemMeta()){
                ItemMeta firstMeta = firstItem.getItemMeta();
                if (firstMeta.hasLore() && firstMeta.lore().contains(c)){
                    event.setResult(null);
                    Main.sendDebugMessage("Anvil Event Result set to null",cancelDebug);
                    return;
                }
            }

            // Check second item
            if (secondItem != null && secondItem.hasItemMeta()){
                ItemMeta secondMeta = secondItem.getItemMeta();
                if (secondMeta.hasLore() && secondMeta.lore().contains(c)){
                    event.setResult(null);
                    Main.sendDebugMessage("Anvil Event Result set to null",cancelDebug);
                    return;
                }
            }

        }
    }

    // Prevent a player from using grindstone
    // Deprecated..?
    @EventHandler
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
    }
}
