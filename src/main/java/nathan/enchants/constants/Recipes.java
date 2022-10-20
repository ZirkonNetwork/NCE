package nathan.enchants.constants;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import static nathan.enchants.constants.Items.*;
import static nathan.enchants.NathansEnchants.plugin;

public final class Recipes {
    //Soul vial recipes
    public static ShapedRecipe compressedSSRecipe() {
        NamespacedKey cssKey = new NamespacedKey(plugin, "compressed_soul_sand");
        ShapedRecipe cssRecipe = new ShapedRecipe(cssKey, compressedSoulSand());

        cssRecipe.shape("SSS", "SSS", "SSS");
        cssRecipe.setIngredient('S', Material.SOUL_SAND);
        return cssRecipe;
    }

    public static ShapedRecipe soulGlassRecipe() {
        RecipeChoice.ExactChoice cssRC = new RecipeChoice.ExactChoice(compressedSoulSand());
        NamespacedKey sgKey = new NamespacedKey(plugin, "soul_glass");
        ItemStack sg = soulGlass();
        sg.setAmount(4);
        ShapedRecipe sgRecipe = new ShapedRecipe(sgKey, sg);

        sgRecipe.shape("SSS", "SGS", "SSS");
        sgRecipe.setIngredient('S', cssRC);
        sgRecipe.setIngredient('G', Material.GLASS);
        return sgRecipe;
    }

    public static ShapedRecipe soulVialRecipe() {
        RecipeChoice.ExactChoice sgRC = new RecipeChoice.ExactChoice(soulGlass());
        RecipeChoice.ExactChoice soulariumRC = new RecipeChoice.ExactChoice(soularium());
        NamespacedKey svKey = new NamespacedKey(plugin, "empty_soul_vial");
        ShapedRecipe svRecipe = new ShapedRecipe(svKey, emptySoulVial());

        svRecipe.shape(" S ", "G G", " G ");
        svRecipe.setIngredient('S', soulariumRC);
        svRecipe.setIngredient('G', sgRC);
        return svRecipe;
    }

    public static ShapedRecipe compressedEpRecipe() {
        NamespacedKey cepKey = new NamespacedKey(plugin, "compressed_ender_pearl");
        ShapedRecipe cepRecipe = new ShapedRecipe(cepKey, compressedEnderPearl());

        cepRecipe.shape("EEE", "EEE", "EEE");
        cepRecipe.setIngredient('E', Material.ENDER_PEARL);
        return cepRecipe;
    }

    public static ShapedRecipe compressedFeatherRecipe() {
        NamespacedKey cfKey = new NamespacedKey(plugin, "compressed_feather");
        ShapedRecipe cfRecipe = new ShapedRecipe(cfKey, compressedFeather());

        cfRecipe.shape("FFF", "FFF", "FFF");
        cfRecipe.setIngredient('F', Material.FEATHER);
        return cfRecipe;
    }

    public static ShapedRecipe flightCoreRecipe() {
        RecipeChoice.ExactChoice compressedFeatherRC = new RecipeChoice.ExactChoice(compressedFeather());
        NamespacedKey fcKey = new NamespacedKey(plugin, "flight_core");
        ShapedRecipe fcRecipe = new ShapedRecipe(fcKey, flightCore());

        fcRecipe.shape("NGN", "GEG", "FDF");
        fcRecipe.setIngredient('N', Material.NETHERITE_SCRAP);
        fcRecipe.setIngredient('G', Material.GOLD_BLOCK);
        fcRecipe.setIngredient('E', Material.ELYTRA);
        fcRecipe.setIngredient('F', compressedFeatherRC);
        fcRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        return fcRecipe;
    }

    public static ShapedRecipe soulboundCoreRecipe() {
        RecipeChoice.ExactChoice compressedEnderPearlRC = new RecipeChoice.ExactChoice(compressedEnderPearl());
        RecipeChoice.ExactChoice filledSoulVialRC = new RecipeChoice.ExactChoice(filledSoulVial());
        NamespacedKey scKey = new NamespacedKey(plugin, "soulbound_core");
        ShapedRecipe scRecipe = new ShapedRecipe(scKey, soulboundCore());

        scRecipe.shape("NBP", "NVP", "N P");
        scRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        scRecipe.setIngredient('B', Material.WRITABLE_BOOK);
        scRecipe.setIngredient('P', compressedEnderPearlRC);
        scRecipe.setIngredient('V', filledSoulVialRC);
        return scRecipe;
    }

    public static ShapedRecipe telekinesisCoreRecipe() {
        NamespacedKey tcKey = new NamespacedKey(plugin, "telekinesis_core");
        ShapedRecipe tcRecipe = new ShapedRecipe(tcKey, telekinesisCore());

        tcRecipe.shape("GBG", "LPL", "GEG");
        tcRecipe.setIngredient('G', Material.GOLD_INGOT);
        tcRecipe.setIngredient('B', Material.BOOK);
        tcRecipe.setIngredient('L', Material.LAPIS_BLOCK);
        tcRecipe.setIngredient('P', Material.IRON_PICKAXE);
        tcRecipe.setIngredient('E', Material.ENDER_PEARL);
        return tcRecipe;
    }
}
