package nathan.enchants;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("enchant");
            completions.add("items");
            completions.add("reload");
            completions.add("help");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "enchant":
                    for (Player all : Bukkit.getOnlinePlayers()){
                        completions.add(all.getName());
                    }
                    break;
                case "items":
                case "reload":
                    break;
                case "help":
                    completions.add("1");
                    completions.add("2");
                    break;
            }
            return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
        } else if (args.length == 3) {
            switch (args[0].toLowerCase()) {
                case "enchant":
                    completions.add("flight");
                    completions.add("soulbound");
                    completions.add("telekinesis");
                    break;
                case "items":
                case "reload":
                case "help":
                    break;
            }
            return StringUtil.copyPartialMatches(args[2], completions, new ArrayList<>());
        }
        return null;
    }
}
