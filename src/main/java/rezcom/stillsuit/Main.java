package rezcom.stillsuit;

//import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register commands
        this.getCommand("stillsuit").setExecutor(new giveStillsuitCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic ok
    }
}
