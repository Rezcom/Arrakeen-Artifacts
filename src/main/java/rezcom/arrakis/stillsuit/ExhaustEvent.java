package rezcom.arrakis.stillsuit;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import rezcom.arrakis.Main;


import java.util.*;
import java.util.logging.Level;


public class ExhaustEvent implements Listener {

    public static boolean debugExhaustEvent = false;
    public static Map<UUID, Integer> playerStillsuitDurability = new HashMap<>();

    // Probabilities are doubles. The maps are each in form <reason, probability>

    // Probs for if player is in a Desert
    public static Map<EntityExhaustionEvent.ExhaustionReason,Double> desertValues = new HashMap<>();

    // Probs for if a player is in a Savanna
    public static Map<EntityExhaustionEvent.ExhaustionReason,Double> savannaValues = new HashMap<>();

    // Not in a special biome
    public static Map<EntityExhaustionEvent.ExhaustionReason,Double> defaultExhaust = new HashMap<>();




    public static void initExhaustProbs(FileConfiguration fileConfiguration){
        // PLEASE CALL THIS EARLY!!
        try {
            Objects.requireNonNull(fileConfiguration.getConfigurationSection("exhaust-event")).getKeys(false).forEach(reason -> {
                EntityExhaustionEvent.ExhaustionReason exhaustionReason = EntityExhaustionEvent.ExhaustionReason.valueOf(reason);

                defaultExhaust.put(exhaustionReason,fileConfiguration.getDouble("exhaust-event."+reason+".default"));
                Main.sendDebugMessage(reason+" by default: "+defaultExhaust.get(exhaustionReason),debugExhaustEvent);

                savannaValues.put(exhaustionReason,fileConfiguration.getDouble("exhaust-event."+reason+".savanna"));
                Main.sendDebugMessage(reason+" in Savanna: "+savannaValues.get(exhaustionReason),debugExhaustEvent);

                desertValues.put(exhaustionReason, fileConfiguration.getDouble("exhaust-event."+reason+".desert"));
                Main.sendDebugMessage(reason+" in Desert: "+desertValues.get(exhaustionReason),debugExhaustEvent);
            });
        } catch (NullPointerException e){
            Main.logger.log(Level.SEVERE, "Exhaustion config failed, please review. Could not initialize exhaustion values.");
        }



    }

    @EventHandler
    void onPlayerExhaust(EntityExhaustionEvent event){

        if (!(event.getEntity() instanceof Player) || (!(stillsuitFunctions.isWearingStillsuit((Player) event.getEntity())))){
            return;
        }
        if (event.getExhaustion() == 0){
            return;
        }

        Player player = (Player) event.getEntity();
        EntityExhaustionEvent.ExhaustionReason reason = event.getExhaustionReason();
        Main.sendDebugMessage("Player wearing stillsuit Exhaustion occured: " + reason.toString(),debugExhaustEvent);
        Double multiplier = 1.0;
        if (stillsuitFunctions.isInBiome(player, Biome.DESERT)){
            // Player is in the desert.
            // Things should be much more likely
            Main.sendDebugMessage("Exhaustion Reason: "+reason+" in Desert.",debugExhaustEvent);
            multiplier = desertValues.get(reason);

        } else if (stillsuitFunctions.isInBiome(player, Biome.SAVANNA) || stillsuitFunctions.isInBiome(player, Biome.SAVANNA_PLATEAU) || stillsuitFunctions.isInBiome(player, Biome.WINDSWEPT_SAVANNA)){
            // Player is in the savanna.
            // Things should be more likely.
            Main.sendDebugMessage("Exhaustion Reason: "+reason+" in Savanna.",debugExhaustEvent);
            multiplier = savannaValues.get(reason);
        } else {
            // Player isn't in a special biome.
            Main.sendDebugMessage("Exhaustion Reason: "+reason+" without special biome.",debugExhaustEvent);
            multiplier = defaultExhaust.get(reason);
        }
        Main.sendDebugMessage("Exhaustion: "+event.getExhaustion(),debugExhaustEvent);

        float newExhaustion = event.getExhaustion() * multiplier.floatValue();
        Main.sendDebugMessage("Multiplier Float Value: "+multiplier.floatValue(),debugExhaustEvent);

        ItemStack chestplate = player.getInventory().getChestplate();
        if (chestplate == null || chestplate.getItemMeta() == null){
            return;
        }
        Material material = chestplate.getType();
        Damageable damageable = (Damageable) chestplate.getItemMeta();
        if (damageable.getDamage() == material.getMaxDurability() - 1){
            return;
        }

        Random rand = new Random();
        if (rand.nextDouble() <= 0.1){
            damageable.setDamage(damageable.getDamage() + 1);
            chestplate.setItemMeta(damageable);
        }

        event.setExhaustion(newExhaustion);
        Main.sendDebugMessage("Exhaustion Applied: "+newExhaustion,debugExhaustEvent);



    }

}
