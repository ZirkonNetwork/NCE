package nathan.enchants.events;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nathan.enchants.Items.*;
import static nathan.enchants.NathansEnchants.config;
import static nathan.enchants.NathansEnchants.plugin;
import static nathan.enchants.enchantments.Enchantments.*;

public class ItemDrop implements Listener {
    /**
     * Might eventually add as a config option
     * {@code public static List<String> supportedItemsFlight = null;}
     * {@code public static List<EnchantmentTarget> supportedItemsSoulbound = Arrays.asList(BREAKABLE, WEARABLE);}
     */
    private static final List<String> telekinesisSupportedMaterials = Arrays.asList("axe", "hoe", "shovel", "shears", "sword");
    private static final List<Material> workstations = Arrays.asList(Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL);

    @EventHandler(priority = EventPriority.HIGH)
    public void enchantItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item itemDrop = event.getItemDrop();

        if (itemDrop.getItemStack().isSimilar(flightCore()) && config.getBoolean("enable-flight-enchantment")) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (itemDrop.isValid() && workstations.contains(itemDrop.getLocation().add(0D, -1D, 0D).getBlock().getType())) {
                    itemDrop.setPickupDelay(60);
                    for (Entity entity : itemDrop.getNearbyEntities(1.5D, 1.5D, 1.5D)) {
                        if (entity instanceof Item item) {
                            if (item.getItemStack().getType().name().endsWith("CHESTPLATE") && !item.getItemStack().containsEnchantment(FLIGHT)) {
                                item.setPickupDelay(60);
                                if (canUseCombine(player)) {
                                    ItemStack targetItem = item.getItemStack();
                                    addCustomEnchantment(targetItem, FLIGHT);

                                    itemDrop.remove();

                                    playOutcomeEffect(item.getLocation().add(0, 0.5, 0), Particle.CRIT_MAGIC,
                                            250, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                                    item.setPickupDelay(10);
                                } else {
                                    playOutcomeEffect(itemDrop.getLocation().add(0, 0.5, 0), Particle.BLOCK_MARKER,
                                            250, Sound.BLOCK_ANVIL_LAND, 1, 1);
                                }
                                return;
                            }

                        }
                    }
                }
            }, 60L);
        } else if (itemDrop.getItemStack().isSimilar(soulboundCore()) && config.getBoolean("enable-soulbound-enchantment")) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (itemDrop.isValid() && workstations.contains(itemDrop.getLocation().add(0D, -1D, 0D).getBlock().getType())) {
                    itemDrop.setPickupDelay(60);
                    for (Entity entity : itemDrop.getNearbyEntities(1.5D, 1.5D, 1.5D)) {
                        if (entity instanceof Item item) {
                            if (!(item.getItemStack().getType() == Material.ENCHANTED_BOOK) && !item.getItemStack().containsEnchantment(SOULBOUND)) {
                                item.setPickupDelay(60);
                                if (canUseCombine(player)) {
                                    ItemStack targetItem = item.getItemStack();
                                    addCustomEnchantment(targetItem, SOULBOUND);

                                    itemDrop.remove();

                                    playOutcomeEffect(item.getLocation().add(0, 0.5, 0), Particle.CRIT_MAGIC,
                                            250, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                                    item.setPickupDelay(10);
                                } else {
                                    playOutcomeEffect(itemDrop.getLocation().add(0, 0.5, 0), Particle.BLOCK_MARKER,
                                            250, Sound.BLOCK_ANVIL_LAND, 1, 1);
                                }
                                return;
                            }
                        }
                    }
                }
            }, 60L);
        } else if (itemDrop.getItemStack().isSimilar(telekinesisCore()) && config.getBoolean("enable-telekinesis-enchantment")) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (itemDrop.isValid() && workstations.contains(itemDrop.getLocation().add(0D, -1D, 0D).getBlock().getType())) {
                    itemDrop.setPickupDelay(60);
                    for (Entity entity : itemDrop.getNearbyEntities(1.5D, 1.5D, 1.5D)) {
                        if (entity instanceof Item item) {
                            if (telekinesisSupportedMaterials.stream().anyMatch(ending -> item.getItemStack().getType().name().toLowerCase().endsWith(ending))
                                    && !item.getItemStack().containsEnchantment(TELEKINESIS)) {
                                item.setPickupDelay(60);
                                if (canUseCombine(player)) {
                                    ItemStack targetItem = item.getItemStack();
                                    addCustomEnchantment(targetItem, TELEKINESIS);

                                    itemDrop.remove();

                                    playOutcomeEffect(item.getLocation().add(0, 0.5, 0), Particle.CRIT_MAGIC,
                                            250, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
                                    item.setPickupDelay(10);
                                } else {
                                    playOutcomeEffect(itemDrop.getLocation().add(0, 0.5, 0), Particle.BLOCK_MARKER,
                                            250, Sound.BLOCK_ANVIL_LAND, 1, 1);
                                }
                                return;
                            }
                        }
                    }
                }
            }, 60L);
        }
    }

    private static boolean canUseCombine(Player player) {
        return !(config.getBoolean("use-combine-permissions") && !player.hasPermission("nce.crafting.use"));
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    private void addCustomEnchantment(ItemStack target, Enchantment enchantment) {
        ItemMeta meta = target.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + enchantment.getName());
        if (meta.hasLore()) lore.addAll(meta.getLore());
        meta.setLore(lore);
        target.setItemMeta(meta);
        target.addUnsafeEnchantment(enchantment, 1);
    }

    @SuppressWarnings({"SameParameterValue", "ConstantConditions"})
    private void playOutcomeEffect(Location location, Particle particle, int particleCount, Sound sound, float volume, float pitch) {
        World world = location.getWorld();
        if (particle == Particle.BLOCK_MARKER) {
            world.spawnParticle(particle, location, particleCount, Material.BARRIER.createBlockData());
        } else {
            world.spawnParticle(particle, location, particleCount);
        }
        world.playSound(location, sound, volume, pitch);
    }
}
