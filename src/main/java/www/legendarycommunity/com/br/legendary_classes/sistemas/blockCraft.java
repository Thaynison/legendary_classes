package www.legendarycommunity.com.br.legendary_classes.sistemas;

import org.bukkit.Material;

public class blockCraft {

    public static boolean itensMinecraft(Material material) {
        return material == Material.SHULKER_BOX ||
                material == Material.END_CRYSTAL ||
                material == Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE ||
                material == Material.BREWING_STAND ||
                material == Material.CRAFTER ||
                material == Material.DISPENSER ||
                material == Material.DROPPER ||
                material == Material.ENCHANTING_TABLE;
    }

    public static boolean isIron_Itens(Material material) {
        return material == Material.IRON_PICKAXE ||
                material == Material.IRON_AXE ||
                material == Material.IRON_HOE ||
                material == Material.IRON_SWORD ||
                material == Material.IRON_HELMET ||
                material == Material.IRON_CHESTPLATE ||
                material == Material.IRON_LEGGINGS ||
                material == Material.IRON_BOOTS;
    }

    public static boolean isGold_Itens(Material material) {
        return material == Material.GOLDEN_PICKAXE ||
                material == Material.GOLDEN_AXE ||
                material == Material.GOLDEN_HOE ||
                material == Material.GOLDEN_SWORD ||
                material == Material.GOLDEN_HELMET ||
                material == Material.GOLDEN_CHESTPLATE ||
                material == Material.GOLDEN_LEGGINGS ||
                material == Material.GOLDEN_BOOTS;
    }

    public static boolean isDiamond_Itens(Material material) {
        return material == Material.DIAMOND_PICKAXE ||
                material == Material.DIAMOND_AXE ||
                material == Material.DIAMOND_HOE ||
                material == Material.DIAMOND_SWORD ||
                material == Material.DIAMOND_HELMET ||
                material == Material.DIAMOND_CHESTPLATE ||
                material == Material.DIAMOND_LEGGINGS ||
                material == Material.DIAMOND_BOOTS;
    }

    public static boolean isNetherite_Itens(Material material) {
        return material == Material.NETHERITE_PICKAXE ||
                material == Material.NETHERITE_AXE ||
                material == Material.NETHERITE_HOE ||
                material == Material.NETHERITE_SWORD ||
                material == Material.NETHERITE_HELMET ||
                material == Material.NETHERITE_CHESTPLATE ||
                material == Material.NETHERITE_LEGGINGS ||
                material == Material.NETHERITE_BOOTS;
    }

}
