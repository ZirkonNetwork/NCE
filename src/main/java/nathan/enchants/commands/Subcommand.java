package nathan.enchants.commands;

import org.bukkit.command.CommandSender;

public abstract class Subcommand {
    public Subcommand() {}

    public abstract boolean onCommand(CommandSender sender, String[] args);
}
