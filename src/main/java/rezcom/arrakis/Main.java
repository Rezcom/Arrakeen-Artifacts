package rezcom.arrakis;


import org.bukkit.plugin.java.JavaPlugin;
import rezcom.arrakis.charm.CharmPrepareEvents;
import rezcom.arrakis.charm.PlayerDiesEvent;
import rezcom.arrakis.charm.charmFunctions;
import rezcom.arrakis.stillsuit.*;
import rezcom.arrakis.stylus.BrewingStandEvents;
import rezcom.arrakis.stylus.GesseritMobCommand;
import rezcom.arrakis.stylus.GesseritMobDeath;
import rezcom.arrakis.stylus.stylusFunctions;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static boolean debugMessages = false;
    public static Logger logger;

    // Set of Arrakeen items, which should not be crafted, enchanted, grindstone, etc.
    // Holds text components to identify items as from this plugin, Arrakeen.

    @Override
    public void onEnable() {
        logger = this.getLogger();
        logger.log(Level.INFO, "Initializing plugin.");

        // Plugin startup logic
        this.saveDefaultConfig();

        // Initialize Debugging Booleans
        initializeDebugBooleans();

        // Initialize Item Stacks
        initializeItemStacks();

        // Initialize Events
        initializeEvents();

        // Register Commands
        try {
            this.getCommand("giveartifact").setExecutor(new giveArtifactCommand());
            this.getCommand("gesseritmob").setExecutor(new GesseritMobCommand());
        } catch (NullPointerException e){
            logger.log(Level.SEVERE,"Commands were not initialized correctly! One of the executors returned a nullpointer exception.");
        }

        logger.log(Level.INFO, "Plugin initialized.");
    }

     private void initializeItemStacks(){
        // Initializes all item stacks for the artifacts. Call this early!

        // Stillsuit
        stillsuitFunctions.initializeStillsuit();
        stillsuitFunctions.durabilityReplenish = this.getConfig().getInt("durability-replenish");
        ExhaustEvent.initExhaustProbs(this.getConfig());

         // Initialize the charm item stack
         charmFunctions.initializeCharm();

         // Initialize stylus item stack
         stylusFunctions.initializeStylus();

         // Initialize Stray custom bow
         GesseritMobCommand.initializeStrayEquipment();
    }

    private void initializeDebugBooleans(){
        // Initializes all booleans for debugging
        // various components of the plugin
        debugMessages = this.getConfig().getBoolean("debug-general");
        EatEvent.debugEatEvent = this.getConfig().getBoolean("debug-EatEvent");

        StillsuitPrepareEvents.cancelStillsuitDebug = this.getConfig().getBoolean("debug-stillsuit-cancel");
        CharmPrepareEvents.cancelCharmDebug = this.getConfig().getBoolean("debug-charm-cancel");
        BrewingStandEvents.brewDebug = this.getConfig().getBoolean("debug-brewer");


        giveArtifactCommand.debugGiveCommand = this.getConfig().getBoolean("debug-give-command");

        ExhaustEvent.debugExhaustEvent = this.getConfig().getBoolean("debug-exhaust");

    }

    private void initializeEvents(){
        // Initializes all events

        // Register Events for Stillsuit
        getServer().getPluginManager().registerEvents(new CauldronEvent(), this);
        getServer().getPluginManager().registerEvents(new ExhaustEvent(), this);
        getServer().getPluginManager().registerEvents(new GainHPEvent(),this);
        getServer().getPluginManager().registerEvents(new EatEvent(),this);
        getServer().getPluginManager().registerEvents(new StillsuitPrepareEvents(),this);
        getServer().getPluginManager().registerEvents(new ItemDamagedEvent(), this);

        // Register Events for Charm
        getServer().getPluginManager().registerEvents(new PlayerDiesEvent(), this);
        getServer().getPluginManager().registerEvents(new CharmPrepareEvents(), this);

        // Register Events for Stylus
        getServer().getPluginManager().registerEvents(new BrewingStandEvents(), this);
        getServer().getPluginManager().registerEvents(new GesseritMobDeath(), this);

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
