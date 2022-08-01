package nathan.enchants.enchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static nathan.enchants.NathansEnchants.logger;

public class Enchantments {
    public static final Enchantment FLIGHT = new EnchantmentWrapper("flight", "Flight", 1);
    public static final Enchantment SOULBOUND = new EnchantmentWrapper("soulbound", "Soulbound", 1);
    public static final Enchantment TELEKINESIS = new EnchantmentWrapper("telekinesis", "Telekinesis", 1);

    public static void registerCustomEnchantments() {
        Collection<Enchantment> registeredEnchantments = Arrays.stream(Enchantment.values()).toList();

        if (!registeredEnchantments.contains(FLIGHT)) {
            registerEnchantment(FLIGHT);
        }
        if (!registeredEnchantments.contains(SOULBOUND)) {
            registerEnchantment(SOULBOUND);
        }
        if (!registeredEnchantments.contains(TELEKINESIS)) {
            registerEnchantment(TELEKINESIS);
        }
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean isRegistered;

        try {
            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(enchantment);
            isRegistered = true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            isRegistered = false;
            e.printStackTrace();
        }

        if (isRegistered) {
            //noinspection deprecation
            logger.info("Registered " + enchantment.getName());
        }
    }
}
