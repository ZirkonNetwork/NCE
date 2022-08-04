package nathan.enchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public final class Items {
    public static ItemStack compressedSoulSand() {
        ItemStack css = new ItemStack(Material.SOUL_SAND);
        ItemMeta cssMeta = css.getItemMeta();

        css.setAmount(1);
        cssMeta.addEnchant(Enchantment.LUCK, 1, true);
        cssMeta.setDisplayName(ChatColor.YELLOW + "Compressed Soul Sand");
        cssMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        css.setItemMeta(cssMeta);
        return css;
    }

    public static ItemStack soulGlass() {
        ItemStack sg = new ItemStack(Material.BROWN_STAINED_GLASS);
        ItemMeta sgMeta = sg.getItemMeta();

        sg.setAmount(1);
        sgMeta.addEnchant(Enchantment.LUCK, 1, true);
        sgMeta.setDisplayName(ChatColor.YELLOW + "Soul Glass");
        sgMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sg.setItemMeta(sgMeta);
        return sg;
    }

    public static ItemStack soularium() {
        ItemStack soularium = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta soulariumMeta = soularium.getItemMeta();

        soularium.setAmount(1);
        soulariumMeta.addEnchant(Enchantment.LUCK, 1, true);
        soulariumMeta.setDisplayName(ChatColor.YELLOW + "Soularium");
        soulariumMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        soularium.setItemMeta(soulariumMeta);
        return soularium;
    }

    //Soul vial types
    public static ItemStack emptySoulVial() {
        ItemStack sv = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta svMeta = sv.getItemMeta();

        sv.setAmount(1);
        svMeta.addEnchant(Enchantment.LUCK, 1, true);
        svMeta.setDisplayName(ChatColor.AQUA + "Soul Vial");
        svMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        sv.setItemMeta(svMeta);
        return sv;
    }

    public static ItemStack filledSoulVial() {
        ItemStack fsv = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta fsvMeta = fsv.getItemMeta();
        List<String> fsvLore = new ArrayList<>();

        fsv.setAmount(1);
        fsvMeta.addEnchant(Enchantment.LUCK, 1, true);
        fsvMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Filled Soul Vial");
        fsvMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        fsvLore.add(ChatColor.DARK_GRAY + "A bottled soul taken from");
        fsvLore.add(ChatColor.DARK_GRAY + "an unsuspecting Enderman");
        fsvMeta.setLore(fsvLore);
        fsv.setItemMeta(fsvMeta);
        return fsv;
    }

    public static ItemStack compressedEnderPearl() {
        ItemStack cep = new ItemStack(Material.ENDER_PEARL);
        ItemMeta cepMeta = cep.getItemMeta();

        cep.setAmount(1);
        cepMeta.addEnchant(Enchantment.LUCK, 1, true);
        cepMeta.setDisplayName(ChatColor.YELLOW + "Compressed Ender Pearl");
        cepMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cep.setItemMeta(cepMeta);
        return cep;
    }

    public static ItemStack compressedFeather() {
        ItemStack cf = new ItemStack(Material.FEATHER);
        ItemMeta cfMeta = cf.getItemMeta();

        cf.setAmount(1);
        cfMeta.addEnchant(Enchantment.LUCK, 1, true);
        cfMeta.setDisplayName(ChatColor.YELLOW + "Compressed Feather");
        cfMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cf.setItemMeta(cfMeta);
        return cf;
    }

    public static ItemStack flightCore() {
        ItemStack fc = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta fcMeta = fc.getItemMeta();
        List<String> fcLore = new ArrayList<>();

        fcMeta.setDisplayName(ChatColor.AQUA + "Flight Core");
        fcLore.add(ChatColor.BLUE + "Used to add the Flight");
        fcLore.add(ChatColor.BLUE + "enchantment to a chestplate");
        fcMeta.setLore(fcLore);
        fc.setItemMeta(fcMeta);
        return fc;
    }

    public static ItemStack soulboundCore() {
        ItemStack sc = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta scMeta = sc.getItemMeta();
        List<String> scLore = new ArrayList<>();

        scMeta.setDisplayName(ChatColor.DARK_AQUA + "Soulbound Core");
        scLore.add(ChatColor.DARK_PURPLE + "Used to add the Soulbound");
        scLore.add(ChatColor.DARK_PURPLE + "enchantment to an item");
        scMeta.setLore(scLore);
        sc.setItemMeta(scMeta);
        return sc;
    }

    public static ItemStack telekinesisCore() {
        ItemStack tc = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta tcMeta = tc.getItemMeta();
        List<String> tcLore = new ArrayList<>();

        tcMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Telekinesis Core");
        tcLore.add(ChatColor.DARK_PURPLE + "Used to add the Telekinesis");
        tcLore.add(ChatColor.DARK_PURPLE + "enchantment to a tool");
        tcMeta.setLore(tcLore);
        tc.setItemMeta(tcMeta);
        return tc;
    }
}
