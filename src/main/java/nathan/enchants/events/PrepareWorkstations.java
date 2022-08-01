package nathan.enchants.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;

import static nathan.enchants.Items.*;
import static nathan.enchants.NathansEnchants.config;
import static nathan.enchants.enchantments.Enchantments.*;

@SuppressWarnings("ConstantConditions")
public class PrepareWorkstations implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void prepareCrafting(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();

        if (inventory.getResult() != null) {
            if (inventory.getResult().isSimilar(emptySoulVial()) && (!inventory.containsAtLeast(soulGlass(), 3)
                    || !inventory.containsAtLeast(soularium(), 1))) {
                inventory.setResult(new ItemStack(Material.AIR));
            } else if (!inventory.getResult().isSimilar(emptySoulVial()) && inventory.contains(soularium(), 1)) {
                inventory.setResult(new ItemStack(Material.AIR));
            } else if (inventory.getResult().isSimilar(soulboundCore()) && (!inventory.containsAtLeast(compressedEnderPearl(), 3)
                    || !inventory.containsAtLeast(filledSoulVial(), 1))) {
                inventory.setResult(new ItemStack(Material.AIR));
            } else if (inventory.getResult().isSimilar(flightCore()) && !inventory.containsAtLeast(compressedFeather(), 2)) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void prepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack slot0 = inventory.getItem(0);
        ItemStack slot1 = inventory.getItem(1);

        if (event.getResult() != null) {
            ItemStack result = event.getResult();
            final boolean hasSecondItem = slot1 != null;

            if (inventory.getItem(0).isSimilar(soularium())
                    || (inventory.getItem(1) != null && inventory.getItem(1).isSimilar(soularium()))) {
                event.setResult(new ItemStack(Material.AIR));
            } else if ((slot0.getItemMeta().hasEnchant(TELEKINESIS)
                    || (hasSecondItem && slot1.getItemMeta().hasEnchant(TELEKINESIS)))
                    && !result.getItemMeta().hasEnchant(TELEKINESIS)) {
                result.addUnsafeEnchantment(TELEKINESIS, 1);
                event.setResult(result);
            } else if ((slot0.getItemMeta().hasEnchant(FLIGHT)
                    || (hasSecondItem && slot1.getItemMeta().hasEnchant(FLIGHT)))
                    && !result.getItemMeta().hasEnchant(FLIGHT)) {
                result.addUnsafeEnchantment(FLIGHT, 1);
                event.setResult(result);
            } else if ((slot0.getItemMeta().hasEnchant(SOULBOUND)
                    || (hasSecondItem && slot1.getItemMeta().hasEnchant(SOULBOUND)))
                    && !result.getItemMeta().hasEnchant(SOULBOUND)) {
                result.addUnsafeEnchantment(SOULBOUND, 1);
                event.setResult(result);
            }
        } else if (config.getBoolean("enable-soulbound-recipes") && ((slot0 != null && slot0.isSimilar(new ItemStack(Material.GOLD_INGOT, 1)))
                && (slot1 != null && slot1.isSimilar(new ItemStack(Material.SOUL_SAND, 1))))) {
            int slot0Amount = slot0.getAmount();
            int slot1Amount = slot1.getAmount();
            ItemStack output = soularium();

            if (slot0Amount == slot1Amount) {
                inventory.setRepairCost(6 * slot0Amount);
                if (output.getAmount() > 1) output.setAmount(slot0Amount);
                event.setResult(output);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void prepareSmithing(PrepareSmithingEvent event) {
        if (event.getResult() != null) {
            SmithingInventory inventory = event.getInventory();
            ItemStack result = event.getResult();

            if (inventory.getItem(0).containsEnchantment(TELEKINESIS)
                    && !result.containsEnchantment(TELEKINESIS)) {
                result.addUnsafeEnchantment(TELEKINESIS, 1);
                event.setResult(result);
            } else if (inventory.getItem(0).containsEnchantment(FLIGHT)
                    && !result.containsEnchantment(FLIGHT)) {
                result.addUnsafeEnchantment(FLIGHT, 1);
                event.setResult(result);
            } else if (inventory.getItem(0).containsEnchantment(SOULBOUND)
                    && !result.containsEnchantment(SOULBOUND)) {
                result.addUnsafeEnchantment(SOULBOUND, 1);
                event.setResult(result);
            } else if (inventory.contains(soularium())) {
                event.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
