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
public final class SubcommandItems extends Subcommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) && args[0].length() == 0) {
            sender.sendMessage(pluginPrefix + ChatColor.RED + "Only players can use this subcommand!");
        } else {
            Player executor = ((Player) sender).getPlayer();

            if (!executor.hasPermission("nce.command.items")) {
                executor.sendMessage(translateColorCodes(config.getString("no-permission")));
            } else if (!(args[0].length() == 0)) {
                addItems(Bukkit.getPlayer(args[0]));
            } else {
                addItems(executor);
            }
        }
        return true;
    }

    private void addItems(Player player) {
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        pickaxe.addUnsafeEnchantment(SOULBOUND, 1);
        pickaxe.addUnsafeEnchantment(TELEKINESIS, 1);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        List<String> pickaxeLore = new ArrayList<>();
        pickaxeLore.add(ChatColor.GRAY + "Soulbound");
        pickaxeLore.add(ChatColor.GRAY + "Telekinesis");
        pickaxeMeta.setLore(pickaxeLore);
        pickaxe.setItemMeta(pickaxeMeta);
        player.getInventory().addItem(new ItemStack(pickaxe));

        ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        chestplate.addUnsafeEnchantment(FLIGHT, 1);
        chestplate.addUnsafeEnchantment(SOULBOUND, 1);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        List<String> chestplateLore = new ArrayList<>();
        chestplateLore.add(ChatColor.GRAY + "Flight");
        chestplateLore.add(ChatColor.GRAY + "Soulbound");
        chestplateMeta.setLore(chestplateLore);
        chestplate.setItemMeta(chestplateMeta);
        player.getInventory().addItem(new ItemStack(chestplate));
    }

}
