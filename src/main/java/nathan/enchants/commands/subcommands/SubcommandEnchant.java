package nathan.enchants.commands.subcommands;

import nathan.enchants.commands.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static nathan.enchants.NathansEnchants.*;
import static nathan.enchants.enchantments.Enchantments.*;

@SuppressWarnings("ConstantConditions")
public class SubcommandEnchant extends Subcommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission("nce.command.enchant")) {
            sender.sendMessage(translateColorCodes(config.getString("no-permission")));
        } else {
            if (!(args[0].length() > 2 || args[1].length() > 5 )) {
                sender.sendMessage(translateColorCodes(pluginPrefix + "&cYou must specify a valid player and valid enchantment"));
            } else {
                Player target = Bukkit.getPlayer(args[0]);

                if (target.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    ItemStack item = target.getInventory().getItemInMainHand();

                    if (args[1].equalsIgnoreCase("flight") && (item.getType().name().endsWith("_CHESTPLATE")
                            && !item.containsEnchantment(FLIGHT))) {
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "Flight");
                        if (meta.hasLore()) lore.addAll(item.getItemMeta().getLore());
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        item.addUnsafeEnchantment(FLIGHT, 1);

                        String lowercaseItem = item.getType().name().toLowerCase().replace('_', ' ');
                        String uppercaseItem = lowercaseItem.substring(0, 1).toUpperCase() + lowercaseItem.substring(1);
                        sender.sendMessage(translateColorCodes(pluginPrefix + "&9Added Flight to " + target.getDisplayName() + "'s " + uppercaseItem));

                    } else if (args[1].equalsIgnoreCase("soulbound") && !item.containsEnchantment(SOULBOUND)) {
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "Soulbound");
                        if (meta.hasLore()) lore.addAll(item.getItemMeta().getLore());
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        item.addUnsafeEnchantment(SOULBOUND, 1);

                        String lowercaseItem = item.getType().name().toLowerCase().replace('_', ' ');
                        String uppercaseItem = lowercaseItem.substring(0, 1).toUpperCase() + lowercaseItem.substring(1);
                        sender.sendMessage(translateColorCodes(pluginPrefix + target.getDisplayName() + "'s " + uppercaseItem));

                    } else if (args[1].equalsIgnoreCase("telekinesis") && !item.containsEnchantment(TELEKINESIS)
                            && (item.getType().name().endsWith("AXE") || item.getType().name().endsWith("_SHOVEL")
                            || item.getType().name().endsWith("_HOE") || item.getType().name().endsWith("_SWORD"))) {
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = new ArrayList<>();

                        lore.add(ChatColor.GRAY + "Telekinesis");
                        if (meta.hasLore()) lore.addAll(item.getItemMeta().getLore());
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        item.addUnsafeEnchantment(TELEKINESIS, 1);

                        String lowercaseItem = item.getType().name().toLowerCase().replace('_', ' ');
                        String uppercaseItem = lowercaseItem.substring(0, 1).toUpperCase() + lowercaseItem.substring(1);
                        sender.sendMessage(translateColorCodes(pluginPrefix + "&9Added Telekinesis to " + target.getDisplayName() + "'s " + uppercaseItem));

                    } else {
                        sender.sendMessage(translateColorCodes(pluginPrefix + "&cYou must choose a valid enchantment (telekinesis/flight) to add to the player's held item.\nThe held item must also be a chestplate or allowed tool that does not already have the enchantment."));
                    }
                } else {
                    sender.sendMessage(translateColorCodes(pluginPrefix + target.getDisplayName() + " is not holding an item"));
                }
            }
        }
        return true;
    }

}
