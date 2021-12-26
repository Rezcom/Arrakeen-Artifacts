package rezcom.arrakis.stillsuit;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.Main;


public class EatEvent implements Listener {

    public static boolean debugEatEvent = false;

    @EventHandler
    void onPlayerEat(FoodLevelChangeEvent event){
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        Player player = (Player) event.getEntity();
        ItemStack foodItem = event.getItem();

        Main.sendDebugMessage("Player food level changed",debugEatEvent);

        if (debugEatEvent){
            if (foodItem != null){
                Main.sendDebugMessage("foodItem isn't null",true);
            }
            if (player.getInventory().getChestplate() != null){
                Main.sendDebugMessage("Player is wearing a chestplate.",true);
            }
            if (stillsuitFunctions.isWearingStillsuit(player)){
                Main.sendDebugMessage("Player is wearing a stillsuit.",true);
            }

        }

        if (foodItem != null && player.getInventory().getChestplate() != null && stillsuitFunctions.replenishFoods.contains(foodItem.getType()) && stillsuitFunctions.isWearingStillsuit(player)){
            // The food item is valid, and the player is wearing the stillsuit.
            Main.sendDebugMessage("Player is wearing a stillsuit, and the food item is valid for replenishment.",debugEatEvent);
            stillsuitFunctions.addDurability(player);
            Main.sendDebugMessage("ItemMeta was set for the stillsuit.",debugEatEvent);

        }
    }
}
