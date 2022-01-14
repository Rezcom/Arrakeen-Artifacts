package rezcom.arrakis;


import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import rezcom.arrakis.charm.PlayerDiesEvent;
import rezcom.arrakis.charm.charmFunctions;
import rezcom.arrakis.charm.giveCharmCommand;
import rezcom.arrakis.stillsuit.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static boolean debugMessages = false;
    public static Logger logger;

    // Set of Arrakeen items, which should not be crafted, enchanted, grindstone, etc.
    // Holds text components to identify items as from this plugin, Arrakeen.
    public static Set<Component> arrakeenIdentifiers = new HashSet<>();

    @Override
    public void onEnable() {
        logger = this.getLogger();
        logger.log(Level.INFO, "Initializing plugin.");

        // Plugin startup logic
        this.saveDefaultConfig();

        // Initialize Debugging Booleans
        debugMessages = this.getConfig().getBoolean("debug-general");
        EatEvent.debugEatEvent = this.getConfig().getBoolean("debug-EatEvent");
        CancelResultEvents.cancelDebug = this.getConfig().getBoolean("debug-cancel");
        ExhaustEvent.debugExhaustEvent = this.getConfig().getBoolean("debug-exhaust");

        // Initialize the stillsuit item stack, along with various other stillsuit related values.
        stillsuitFunctions.initializeStillsuit();
        sendDebugMessage("Stillsuit initialized.",debugMessages);
        stillsuitFunctions.durabilityReplenish = this.getConfig().getInt("durability-replenish");

        // Initialize the charm item stack
        charmFunctions.initializeCharm();

        // Initialize the probabilities for exhaust events
        ExhaustEvent.initExhaustProbs(this.getConfig());

        // Register Commands
        try {
            this.getCommand("stillsuit").setExecutor(new giveStillsuitCommand());
            this.getCommand("muaddibcharm").setExecutor(new giveCharmCommand());
        } catch (NullPointerException e){
            logger.log(Level.SEVERE,"Commands were not initialized correctly! One of the executors returned a nullpointer exception.");
        }


        // Register Events for Stillsuit
        getServer().getPluginManager().registerEvents(new ExhaustEvent(), this);
        getServer().getPluginManager().registerEvents(new GainHPEvent(),this);
        getServer().getPluginManager().registerEvents(new EatEvent(),this);
        getServer().getPluginManager().registerEvents(new CancelResultEvents(),this);
        getServer().getPluginManager().registerEvents(new ItemDamagedEvent(), this);

        // Register Events for Charm
        getServer().getPluginManager().registerEvents(new PlayerDiesEvent(), this);

        logger.log(Level.INFO, "Plugin initialized.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic ok
    }

    public static void sendDebugMessage(String string, boolean send){
        if (send){
            logger.log(Level.INFO, string);
        }
    }
}
