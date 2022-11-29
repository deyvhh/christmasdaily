package pl.devyhh.commands;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.devyhh.Main;

import java.util.Arrays;

@Command("daily")
public class Daily extends BaseCommand {
    Main plugin;
    public Daily(Main instance){
        plugin = instance;
    }
    @Default
    public void executor(Player sender){
        Gui gui = Gui.gui()
                .title(Component.text(ChatColor.RED + "Nagroda"))
                .rows(6)
                .disableAllInteractions()
                .create();
        gui.getFiller().fill(Arrays.asList(ItemBuilder.from(Material.WHITE_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem(), ItemBuilder.from(Material.RED_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem()));
        int[] slots = {11, 12, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 38, 39, 41, 42};
        for(int i = 1; i <= slots.length; i++){
            if(plugin.getConfig().getBoolean("days."+i+"."+sender.getUniqueId())){
                gui.setItem(
                        slots[i - 1],
                        ItemBuilder.from(Material.MINECART)
                                .name(Component.text(ChatColor.GRAY + "" + ChatColor.BOLD + "ODEBRANO"))
                                .lore(Component.text(ChatColor.WHITE + "Dzien " + i))
                                .glow()
                                .asGuiItem(event -> {
                                    sender.sendMessage("");
                                    sender.sendMessage(ChatColor.RED + "Odebrales juz to!");
                                    sender.sendMessage("");
                                })
                );
            } else {
                int finalI = i;
                gui.setItem(
                        slots[i - 1],
                        ItemBuilder.from(Material.CHEST_MINECART)
                                .name(Component.text(ChatColor.GREEN + "" + ChatColor.BOLD + "ODBIERZ"))
                                .lore(Component.text(ChatColor.WHITE + "Dzien " + i))
                                .glow()
                                .asGuiItem(event -> {
                                    sender.getInventory().addItem(new ItemStack(Material.getMaterial(plugin.getConfig().getString("reward"))));
                                    plugin.getConfig().set("days."+ finalI +"."+sender.getUniqueId(), true);
                                    gui.close(sender);
                                })
                );
            }
        }
        gui.open(sender);
    }
}
