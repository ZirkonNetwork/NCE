package nathan.enchants.commands;

import nathan.enchants.NathansEnchants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {

    private final Map<String, Subcommand> subcommands = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!subcommands.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(NathansEnchants.translateColorCodes(NathansEnchants.pluginPrefix
                    + "&c\"" + args[0] + "\" is not a valid subcommand."));
            return true;
        } else if (args[0].length() < 2) {
            sender.sendMessage(NathansEnchants.translateColorCodes(NathansEnchants.pluginPrefix
                    + "&cYou must include a subcommand when using this command. Use \"/nce help\" for help with this."));
            return true;
        }
        return subcommands.get(args[0]).onCommand(sender, args);
    }

    public void registerCommand(String cmd, Subcommand subcommand) {
        this.subcommands.put(cmd, subcommand);
    }
}
