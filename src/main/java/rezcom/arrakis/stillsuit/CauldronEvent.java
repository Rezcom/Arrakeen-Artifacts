package rezcom.arrakis.stillsuit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CauldronEvent implements Listener {

    @EventHandler
    void onCauldronWash(CauldronLevelChangeEvent event){
        if (!(event.getReason() == CauldronLevelChangeEvent.ChangeReason.ARMOR_WASH)) { return; }
        if (!(event.getEntity() instanceof Player)) { return; }

        Player player = (Player) event.getEntity();

        ItemStack mainStack = player.getInventory().getItemInMainHand();
        ItemStack offhandStack = player.getInventory().getItemInOffHand();

        if (stillsuitFunctions.isItemStillsuit(mainStack) || stillsuitFunctions.isItemStillsuit(offhandStack)){
            event.setCancelled(true);
        }
    }
}

