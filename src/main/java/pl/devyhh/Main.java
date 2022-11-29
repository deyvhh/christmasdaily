package pl.devyhh;

import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.devyhh.commands.Daily;

public final class Main extends JavaPlugin {
    BukkitCommandManager<CommandSender> manager;
    FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        config.addDefault("reward", "DIAMOND");
        config.options().copyDefaults(true);
        saveConfig();
        manager = BukkitCommandManager.create(this);
        manager.registerCommand(new Daily(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
