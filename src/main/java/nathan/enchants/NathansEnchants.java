package nathan.enchants;

import nathan.enchants.commands.MainCommand;
import nathan.enchants.commands.subcommands.SubcommandEnchant;
import nathan.enchants.commands.subcommands.SubcommandHelp;
import nathan.enchants.commands.subcommands.SubcommandItems;
import nathan.enchants.commands.subcommands.SubcommandReload;
import nathan.enchants.enchantments.Enchantments;
import nathan.enchants.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

import static nathan.enchants.Recipes.*;
import static nathan.enchants.enchantments.Enchantments.FLIGHT;

@SuppressWarnings("ConstantConditions")
public final class NathansEnchants extends JavaPlugin {
    public static final String VERSION = "4.1.1";
    public static Configuration config;
    public static Logger logger;
    public static MainCommand mainCommand;
    public static JavaPlugin plugin;
    public static String pluginPrefix = null;

    /** TO-DO:
      * Store soulbound items list?
    */

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        plugin = this;
        config = this.getConfig();
        logger = this.getLogger();
        mainCommand = new MainCommand();
        pluginPrefix = config.getString("plugin-prefix");

        //noinspection ConstantConditions
        this.getCommand("nce").setExecutor(mainCommand);
        this.getCommand("nce").setTabCompleter(new CommandCompleter());

        initSubcommands();
        startup();

        //ItemDrop.supportedItemsFlight = (List<String>) config.getList("support-items-flight");
        //ItemDrop.supportedItemsSoulbound = (List<String>) config.getList("supported-items-soulbound");
        //ItemDrop.telekinesisSupportedMaterials = (List<String>) config.getList("support-items-telekinesis");
    }

    public void initSubcommands() {
        mainCommand.registerCommand("enchant", new SubcommandEnchant());
        mainCommand.registerCommand("help", new SubcommandHelp());
        mainCommand.registerCommand("items", new SubcommandItems());
        mainCommand.registerCommand("reload", new SubcommandReload());
    }

    public void startup() {
        Enchantments.registerCustomEnchantments();

        if (config.getBoolean("enable-flight-enchantment")) {
            startFlightCheckLoop();
        }

        if (config.getBoolean("enable-soulbound-enchantment")) {
            this.getServer().getPluginManager().registerEvents(new SoulboundEvents(), this);
        }

        if (config.getBoolean("enable-telekinesis-enchantment")) {
            this.getServer().getPluginManager().registerEvents(new TelekinesisEvents(), this);
        }

        if (config.getBoolean("enable-combine")) {
            this.getServer().getPluginManager().registerEvents(new ItemDrop(), this);
        }

        if (config.getBoolean("enable-flight-recipes")) {
            Bukkit.addRecipe(compressedFeatherRecipe());
            Bukkit.addRecipe(flightCoreRecipe());
        }

        if (config.getBoolean("enable-soulbound-recipes")) {
            Bukkit.addRecipe(compressedSSRecipe());
            Bukkit.addRecipe(soulGlassRecipe());
            Bukkit.addRecipe(soulVialRecipe());
            Bukkit.addRecipe(compressedEpRecipe());
            Bukkit.addRecipe(soulboundCoreRecipe());
        }

        if (config.getBoolean("enable-telekinesis-recipes")) {
            Bukkit.addRecipe(telekinesisCoreRecipe());
        }

        if (config.getBoolean("enable-flight-recipes")
                || config.getBoolean("enable-soulbound-recipes")
                || config.getBoolean("enable-telekinesis-recipes")) {
            this.getServer().getPluginManager().registerEvents(new GiveCustomRecipes(), this);
        }

        this.getServer().getPluginManager().registerEvents(new PrepareWorkstations(), this);
    }

    private void startFlightCheckLoop() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                    continue;
                }
                if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().containsEnchantment(FLIGHT)
                        || (config.getBoolean("use-enchant-permissions") && !player.hasPermission("nce.enchant.flight"))) {
                    if (!player.hasPermission("nce.admin.flight")) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                    }
                } else {
                    player.setAllowFlight(true);
                }
            }
        }, 0L, 10L);
    }

    public static String translateColorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
