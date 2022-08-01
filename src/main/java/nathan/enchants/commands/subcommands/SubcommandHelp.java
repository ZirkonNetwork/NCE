package nathan.enchants.commands.subcommands;

import nathan.enchants.commands.Subcommand;
import org.bukkit.command.CommandSender;

import static nathan.enchants.NathansEnchants.*;

public class SubcommandHelp extends Subcommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(translateColorCodes("&8&l&m&n______&8 [&b&lNCE&8] &b(" + VERSION + ") &3Help &8&l&m&n______\n" +
                " &b&l/nce help                           &8&l| &3Shows this message \n" +
                " &b&l/nce reload                         &8&l| &3Reloads the configuration file to update any changed\n" +
                " &b&l/nce items [player]                 &8&l| &3Gives you (or the specified player) items with the custom enchantments from this plugin\n" +
                " &b&l/nce enchant [player] [enchantment] &8&l| &3Puts the specified enchantment on the specified player's held item"));
        return true;
    }

}
