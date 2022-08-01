package nathan.enchants.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.enchantments.EnchantmentTarget.BREAKABLE;

public class EnchantmentWrapper extends Enchantment {
    private final String name;
    private final int maxLevel;

    public EnchantmentWrapper(@NotNull String namespace, String name, int maxLevel) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.maxLevel = maxLevel;
    }

    @NotNull
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return BREAKABLE;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return false;
    }
}
