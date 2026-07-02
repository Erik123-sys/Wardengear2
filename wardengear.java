package sk.erik.wardengear;

import org.bukkit.plugin.java.JavaPlugin;

public final class WardenGear extends JavaPlugin {

    private static WardenGear instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        getLogger().info("");
        getLogger().info("================================");
        getLogger().info("WardenGear enabled!");
        getLogger().info("Running Paper 1.21.11");
        getLogger().info("================================");
        getLogger().info("");

    }

    @Override
    public void onDisable() {

        getLogger().info("WardenGear disabled.");

    }

    public static WardenGear getInstance() {
        return instance;
    }

}
