package nathan.enchants.events;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;

import static nathan.enchants.constants.Recipes.*;

public class GiveCustomRecipes implements Listener {
    private final List<NamespacedKey> customRecipes = Arrays.asList(compressedSSRecipe().getKey(), soulGlassRecipe().getKey(),
            soulVialRecipe().getKey(), compressedEpRecipe().getKey(), compressedFeatherRecipe().getKey(),
            flightCoreRecipe().getKey(), soulboundCoreRecipe().getKey(), telekinesisCoreRecipe().getKey());

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (NamespacedKey customRecipe : customRecipes) {
            if (!event.getPlayer().hasDiscoveredRecipe(customRecipe) && Bukkit.getRecipe(customRecipe) != null) {
                player.discoverRecipe(customRecipe);
            }
        }
    }
}
