package rezcom.arrakis;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import rezcom.arrakis.crysknife.crysknifeFunctions;
import rezcom.arrakis.ixian.ixianFunctions;
import rezcom.arrakis.stillsuit.stillsuitFunctions;
import rezcom.arrakis.suspensorBoots.suspensorBootsFunctions;

import java.util.logging.Level;

public class ItemDamagedEvent implements Listener {

    @EventHandler
    void onStillSuitDamaged(PlayerItemDamageEvent event){
        //Main.logger.log(Level.INFO, "Item took damage");
        ItemStack itemStack = event.getItem();
        if (stillsuitFunctions.isItemStillsuit(itemStack)){
            //Main.logger.log(Level.INFO,"Stillsuit took damage");
            event.setCancelled(true);
        } else if (ixianFunctions.isItemIxian(itemStack)){
            event.setCancelled(true);
        } else if (crysknifeFunctions.isItemCrysknife(itemStack)){
            event.setCancelled(true);
        } else if (suspensorBootsFunctions.isItemSuspensorBoots(itemStack)){
            event.setCancelled(true);
        }
    }
}
