package nathan.enchants.commands.subcommands;

import nathan.enchants.NathansEnchants;
import nathan.enchants.commands.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static nathan.enchants.NathansEnchants.*;

public final class SubcommandReload extends Subcommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        String successfullyReloaded = translateColorCodes(pluginPrefix + "&9The config has been reloaded");

        if (!(sender instanceof Player)) {
            NathansEnchants.plugin.reloadConfig();
            logger.info(successfullyReloaded);
        } else if (!sender.hasPermission("nce.command.reload")) {
            sender.sendMessage(translateColorCodes(config.getString("no-permission")));
        } else {
            NathansEnchants.plugin.reloadConfig();
            sender.sendMessage(successfullyReloaded);
            logger.info(successfullyReloaded);
        }
        return true;
    }

}
