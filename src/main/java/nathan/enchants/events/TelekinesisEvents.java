package nathan.enchants.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

import static nathan.enchants.NathansEnchants.config;
import static nathan.enchants.enchantments.Enchantments.TELEKINESIS;

@SuppressWarnings("ConstantConditions")
public class TelekinesisEvents implements Listener {
    private List<ItemStack> checkCrop(boolean topBlock, Material typeBroken, boolean twoMaterials, Material secondMaterial, Location location, ItemStack itemUsed, Material replacementMaterial) {
        double y = topBlock ? -1D : 1D;
        List<ItemStack> drops = new ArrayList<>();

        while ((location.getBlock().getType() == typeBroken) || (twoMaterials && (location.getBlock().getType() == secondMaterial))) {
            if (!location.getBlock().getDrops(itemUsed).isEmpty()) drops.addAll(location.getBlock().getDrops(itemUsed));
            location.getBlock().setType(replacementMaterial);
            location.add(0D, y, 0D);
        }
        return drops;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block brokenBlock = event.getBlock();
        ItemStack itemUsed = player.getInventory().getItemInMainHand();

        if (itemUsed.getType() != Material.AIR && itemUsed.getItemMeta() != null && itemUsed.getItemMeta().hasEnchant(TELEKINESIS) &&
                (!config.getBoolean("use-enchant-permissions") || player.hasPermission("nce.enchant.telekinesis")) &&
                (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR) &&
                (!(brokenBlock instanceof Container) || ((Container) brokenBlock).getBlock().isEmpty())) {
            List<ItemStack> eventDrops = new ArrayList<>();
            Material typeBroken = brokenBlock.getType();
            Location brokenBlockLocation = brokenBlock.getLocation();

            event.setDropItems(false);

            switch (typeBroken) {
                case WEEPING_VINES_PLANT ->
                        eventDrops.addAll(checkCrop(true, typeBroken, true, Material.WEEPING_VINES, brokenBlockLocation.add(0D, -1D, 0D), itemUsed, Material.AIR));
                case TWISTING_VINES_PLANT ->
                        eventDrops.addAll(checkCrop(false, typeBroken, true, Material.TWISTING_VINES, brokenBlockLocation.add(0D, 1D, 0D), itemUsed, Material.AIR));
                case BIG_DRIPLEAF ->
                        eventDrops.addAll(checkCrop(true, typeBroken, true, Material.BIG_DRIPLEAF_STEM, brokenBlockLocation.add(0D, -1D, 0D), itemUsed, Material.AIR));
                case SUGAR_CANE, BAMBOO ->
                        eventDrops.addAll(checkCrop(false, typeBroken, false, Material.AIR, brokenBlockLocation.add(0D, 1D, 0D), itemUsed, Material.AIR));
                case KELP_PLANT, KELP ->
                        eventDrops.addAll(checkCrop(false, typeBroken, true, Material.KELP, brokenBlockLocation.add(0D, 1D, 0D), itemUsed, Material.WATER));
                case CAVE_VINES_PLANT, VINE -> {
                    if (itemUsed.getType() == Material.SHEARS || itemUsed.containsEnchantment(Enchantment.SILK_TOUCH)) {
                        eventDrops.addAll(checkCrop(true, typeBroken, true, Material.CAVE_VINES, brokenBlockLocation.add(0D, -1D, 0D), itemUsed, Material.AIR));
                    }
                }
                case TALL_SEAGRASS, SEAGRASS -> {
                    if (itemUsed.getType() == Material.SHEARS || itemUsed.containsEnchantment(Enchantment.SILK_TOUCH)) {
                        eventDrops.addAll(checkCrop(false, typeBroken, true, Material.SEAGRASS, brokenBlockLocation.add(0D, 1D, 0D), itemUsed, Material.WATER));
                    }
                }
                default -> {
                    Collection<ItemStack> drops = brokenBlock.getDrops(player.getInventory().getItemInMainHand());

                    if (drops.isEmpty()) return;
                    eventDrops.add(drops.iterator().next());
                }
            }

            Iterator<ItemStack> dropsIterator = eventDrops.iterator();

            while (dropsIterator.hasNext()) {
                ItemStack nextStack = dropsIterator.next();
                Map<Integer, ItemStack> addItems = player.getInventory().addItem(nextStack);

                if (addItems.containsValue(nextStack)) {
                    Location loc = event.getBlock().getLocation();
                    loc.getWorld().dropItemNaturally(loc, nextStack);
                } else {
                    dropsIterator.remove();
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity deadEntity = event.getEntity();

        if (!(deadEntity instanceof Player) && deadEntity.getLastDamageCause() != null &&
                deadEntity.getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent &&
                ((EntityDamageByEntityEvent) deadEntity.getLastDamageCause()).getDamager() instanceof Player) {
            Player killer = (Player) damageEvent.getDamager();
            PlayerInventory killerInventory = killer.getInventory();

            if (!event.getDrops().isEmpty() && killerInventory.getItemInMainHand().containsEnchantment(TELEKINESIS) &&
                    (!config.getBoolean("use-enchant-permissions") || killer.hasPermission("nce.enchant.telekinesis"))) {
                Iterator<ItemStack> drops = event.getDrops().iterator();

                while (drops.hasNext()) {
                    ItemStack nextStack = drops.next();
                    Map<Integer, ItemStack> addItems = killerInventory.addItem(nextStack);

                    if (addItems.containsValue(nextStack)) {
                        Location location = event.getEntity().getLocation();
                        location.getWorld().dropItemNaturally(location, nextStack);
                    } else {
                        drops.remove();
                    }
                }

                killer.setTotalExperience(killer.getTotalExperience() + event.getDroppedExp());
                event.setDroppedExp(0);
            } else if (killer.getInventory().getItemInMainHand().containsEnchantment(TELEKINESIS) &&
                    (!config.getBoolean("use-enchant-permissions") || killer.hasPermission("nce.enchant.telekinesis"))) {
                killer.setTotalExperience(killer.getTotalExperience() + event.getDroppedExp());
                event.setDroppedExp(0);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void shearSheep(PlayerShearEntityEvent event) {
        if (event.getItem().containsEnchantment(TELEKINESIS) && event.getEntity() instanceof Sheep) {
            PlayerInventory playerInventory = event.getPlayer().getInventory();
            int amount = new Random().nextInt(1, 3);

            event.setCancelled(true);
            switch (((Sheep) event.getEntity()).getColor()) {
                case WHITE -> playerInventory.addItem(new ItemStack(Material.WHITE_WOOL, amount));
                case ORANGE -> playerInventory.addItem(new ItemStack(Material.ORANGE_WOOL, amount));
                case MAGENTA -> playerInventory.addItem(new ItemStack(Material.MAGENTA_WOOL, amount));
                case LIGHT_BLUE -> playerInventory.addItem(new ItemStack(Material.LIGHT_BLUE_WOOL, amount));
                case YELLOW -> playerInventory.addItem(new ItemStack(Material.YELLOW_WOOL, amount));
                case LIME -> playerInventory.addItem(new ItemStack(Material.LIME_WOOL, amount));
                case PINK -> playerInventory.addItem(new ItemStack(Material.PINK_WOOL, amount));
                case GRAY -> playerInventory.addItem(new ItemStack(Material.GRAY_WOOL, amount));
                case LIGHT_GRAY -> playerInventory.addItem(new ItemStack(Material.LIGHT_GRAY_WOOL, amount));
                case CYAN -> playerInventory.addItem(new ItemStack(Material.CYAN_WOOL, amount));
                case PURPLE -> playerInventory.addItem(new ItemStack(Material.PURPLE_WOOL, amount));
                case BLUE -> playerInventory.addItem(new ItemStack(Material.BLUE_WOOL, amount));
                case BROWN -> playerInventory.addItem(new ItemStack(Material.BROWN_WOOL, amount));
                case GREEN -> playerInventory.addItem(new ItemStack(Material.GREEN_WOOL, amount));
                case RED -> playerInventory.addItem(new ItemStack(Material.RED_WOOL, amount));
                case BLACK -> playerInventory.addItem(new ItemStack(Material.BLACK_WOOL, amount));
            }
        }
    }
}
