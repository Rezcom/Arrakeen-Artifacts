package rezcom.arrakis.stillsuit;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import rezcom.arrakis.Main;

import java.util.Random;

public class GainHPEvent implements Listener {

    public static boolean debugHP = false;

    @EventHandler
    void onHPGain(EntityRegainHealthEvent event){
        Main.sendDebugMessage("Event started, reason is "+event.getRegainReason(),debugHP);
        if (!(event.getEntity() instanceof Player) || (event.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)){
            return;
        }
        Main.sendDebugMessage("Event started; it's a player with SATIATED reason",debugHP);
        Player player = (Player) event.getEntity();
        if (stillsuitFunctions.isWearingStillsuit(player)){
            Main.sendDebugMessage("Player is wearing a stillsuit",debugHP);

            ItemStack chestplate = player.getInventory().getChestplate();
            if (chestplate == null || !stillsuitFunctions.isItemStillsuit(chestplate)){ return; }
            Material material = chestplate.getType();
            Damageable damageable = (Damageable) chestplate.getItemMeta();
            if (damageable == null || damageable.getDamage() == material.getMaxDurability() - 1){ return; }
            Main.sendDebugMessage("Stillsuit is not null",debugHP);

            Random rand = new Random();
            if (rand.nextDouble() <= 0.5){
                damageable.setDamage(damageable.getDamage() + 1);
                chestplate.setItemMeta(damageable);
                Main.sendDebugMessage("Stillsuit durability changed",debugHP);
            }
            Main.sendDebugMessage("Stillsuit amount: "+event.getAmount(),debugHP);
            double newAmount = event.getAmount() * 3;
            event.setAmount(newAmount);
            Main.sendDebugMessage("Gained amount: "+newAmount,debugHP);


        }
    }
}
