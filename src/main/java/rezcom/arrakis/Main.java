package rezcom.arrakis;


import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rezcom.arrakis.bosses.VagrantSpirit.VagrantSpiritCommand;
import rezcom.arrakis.bosses.VagrantSpirit.VagrantSpiritEvents;
import rezcom.arrakis.charm.CharmPrepareEvents;
import rezcom.arrakis.charm.PlayerDiesEvent;
import rezcom.arrakis.charm.charmFunctions;
import rezcom.arrakis.crysknife.crysknifeEvents;
import rezcom.arrakis.crysknife.crysknifeFunctions;
import rezcom.arrakis.dilapidatedbow.dilapidatedBowEvents;
import rezcom.arrakis.dilapidatedbow.dilapidatedBowFunc;
import rezcom.arrakis.fragments.soulFragmentEvents;
import rezcom.arrakis.fragments.soulFragmentFunctions;
import rezcom.arrakis.ixian.ixianConsumeEvent;
import rezcom.arrakis.ixian.ixianFunctions;
import rezcom.arrakis.stillsuit.*;
import rezcom.arrakis.stylus.BrewingStandEvents;
import rezcom.arrakis.bosses.HollowWitch.HollowWitchCommand;
import rezcom.arrakis.bosses.HollowWitch.HollowWitchEvents;
import rezcom.arrakis.stylus.stylusFunctions;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static boolean debugMessages = false;
    public static Logger logger;

    public static Plugin thisPlugin;

    // Set of Arrakeen items, which should not be crafted, enchanted, grindstone, etc.
    // Holds text components to identify items as from this plugin, Arrakeen.

    @Override
    public void onEnable() {
        thisPlugin = this;

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
            this.getCommand("lessersoulmob").setExecutor(new HollowWitchCommand());
            this.getCommand("vagrantspiritmob").setExecutor(new VagrantSpiritCommand());
        } catch (NullPointerException e){
            logger.log(Level.SEVERE,"Commands were not initialized correctly! One of the executors returned a nullpointer exception.");
        }

        logger.log(Level.INFO, "Plugin initialized.");
    }

     private void initializeItemStacks(){
        // Initializes all item stacks for the artifacts. Call this early!

         // Fragments
         soulFragmentFunctions.initializeFragments();

        // Stillsuit
        stillsuitFunctions.initializeStillsuit();
        stillsuitFunctions.durabilityReplenish = this.getConfig().getInt("durability-replenish");
        ExhaustEvent.initExhaustProbs(this.getConfig());

         // Initialize the charm item stack
         charmFunctions.initializeCharm();

         // Initialize stylus item stack
         stylusFunctions.initializeStylus();

         // Dilapidated Bow
         dilapidatedBowFunc.initializeDilapidatedBow();

         // Ixian Probe
         ixianFunctions.initializeIxian();

         // Crysknife
         crysknifeFunctions.initializeCrysknife();

         // BOSSES
         // Initialize Stray custom bow
         HollowWitchCommand.initializeStrayEquipment();
         // Initialize Vagrant Spirit stuff
         VagrantSpiritCommand.initializeHuskEquipment();
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
        GainHPEvent.debugHP = this.getConfig().getBoolean("debug-stillsuit-hp");
    }

    private void initializeEvents(){
        // Initializes all events

        // Register Events for Ixian Probe
        getServer().getPluginManager().registerEvents(new ixianConsumeEvent(), this);

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

        // Register Events for Crysknife
        getServer().getPluginManager().registerEvents(new crysknifeEvents(),this);

        // Register Events for Stylus
        getServer().getPluginManager().registerEvents(new BrewingStandEvents(), this);
        getServer().getPluginManager().registerEvents(new HollowWitchEvents(), this);

        // Register Events for Soul Fragments
        getServer().getPluginManager().registerEvents(new soulFragmentEvents(),this);

        // Register Events for Dilapidated Bow
        getServer().getPluginManager().registerEvents(new dilapidatedBowEvents(),this);

        // Register Events for Vagrant Spirit
        getServer().getPluginManager().registerEvents(new VagrantSpiritEvents(),this);
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
