package nathan.enchants.events;

import nathan.enchants.enchantments.Enchantments;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static nathan.enchants.constants.Items.filledSoulVial;
import static nathan.enchants.constants.Items.emptySoulVial;

@SuppressWarnings("ConstantConditions")
public class SoulboundEvents implements Listener {
    public static final List<SoulboundItemData> soulboundItems = new ArrayList<>();

    public static final class SoulboundItemData {
        private final ItemStack itemStack;
        private final UUID playerUUID;

        SoulboundItemData(ItemStack itemStack, UUID playerUUID) {
            this.itemStack = itemStack;
            this.playerUUID = playerUUID;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }

        public UUID getPlayerUUID() {
            return playerUUID;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void saveSoulboundItems(PlayerDeathEvent event) {
        if (!event.getKeepInventory()) {
            Iterator<ItemStack> playerDropsIterator = event.getDrops().iterator();

            while (playerDropsIterator.hasNext()) {
                ItemStack playerDrop = playerDropsIterator.next();

                if (playerDrop.containsEnchantment(Enchantments.SOULBOUND)) {
                    playerDropsIterator.remove();
                    soulboundItems.add(new SoulboundItemData(playerDrop, event.getEntity().getUniqueId()));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void getSoulboundItems(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!soulboundItems.isEmpty()) {
            Iterator<SoulboundItemData> soulboundItemIterator = soulboundItems.iterator();

            while (soulboundItemIterator.hasNext()) {
                SoulboundItemData itemData = soulboundItemIterator.next();

                if (itemData.getPlayerUUID().equals(player.getUniqueId())) {
                    PlayerInventory inventory = player.getInventory();

                    inventory.setItem(inventory.firstEmpty(), itemData.getItemStack());
                    soulboundItemIterator.remove();
                }
            }
        }
    }

    @EventHandler
    public void takeEndermanSoul(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        Entity rightClicked = event.getRightClicked();

        //System.out.println("Player: " + player.getDisplayName() + "\nClicked Entity: " + event.getRightClicked().getType() + " Age: " + event.getRightClicked().getTicksLived() + "\nItem: " + inventory.getItemInMainHand());
        if (event.getHand().name().equalsIgnoreCase("hand") && rightClicked.getType().equals(EntityType.ENDERMAN) &&
                inventory.getItemInMainHand().isSimilar(emptySoulVial())) {
            if (((Enderman) rightClicked).getTarget() == null) {
                Location loc = rightClicked.getLocation();
                World world = loc.getWorld();

                if (inventory.firstEmpty() == -1 || inventory.getItemInMainHand().getAmount() > 1) {
                    world.playSound(loc, Sound.BLOCK_ANVIL_LAND, 1, 2);
                    player.sendTitle("\n", ChatColor.RED + "You do not have enough space in your inventory to do this", 10, 70, 20);
                } else {
                    rightClicked.remove();
                    inventory.removeItem(emptySoulVial());
                    inventory.addItem(filledSoulVial());

                    world.playSound(loc, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1, 2);
                }
            }
        }
    }
}
