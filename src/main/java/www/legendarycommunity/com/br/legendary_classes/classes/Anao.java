package www.legendarycommunity.com.br.legendary_classes.classes;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import www.legendarycommunity.com.br.legendary_classes.Legendary_classes;
import www.legendarycommunity.com.br.legendary_classes.PlayerClassData;
import www.legendarycommunity.com.br.legendary_classes.sistemas.blockCraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Anao implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;
    private static final Map<Material, Integer> PICKAXE_DAMAGE_MULTIPLIERS = new EnumMap<>(Material.class);

    public Anao(Legendary_classes plugin) {
        this.levels = new HashMap<>();
        this.plugin = plugin;

        setupDefaultLevels();
    }

    private void setupDefaultLevels() {
        for (int i = 1; i <= 100; i++) {
            levels.put(i, i * 2);
        }
    }

    public void applyPowers(Player player) {
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, Integer.MAX_VALUE, 5, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, Integer.MAX_VALUE, 10, true, false));
    }

    // DESATIVAR SISTEMA DE CRAFT DE ITEM!
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        if (result != null &&
                (blockCraft.isNetherite_Itens(result.getType()))) {
            Player player = (Player) event.getView().getPlayer();
            if (IsAnao(player)) {
                inventory.setResult(null);
            }
        }
    }

    // DESATIVAR SISTEMA DE CRAFT DE ITEM!
    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        ItemStack result = event.getResult();
        if (result != null &&
                (blockCraft.isNetherite_Itens(result.getType()))) {
            Player player = (Player) event.getView().getPlayer();
            if (IsAnao(player)) {
                event.setResult(null);
            }
        }
    }

    // PROÍBE O JOGADOR DE USAR ARMADURA VIA INVENTÁRIO
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                ItemStack item = event.getCursor();
                if (item != null && isRestrictedArmor(item) && IsAnao(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsAnao(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    // PROÍBE O JOGADOR DE USAR ARMADURA AO INTERAGIR
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && isRestrictedArmor(item) && IsAnao(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsAnao(player)) {
            PlayerInventory inventory = player.getInventory();
            ItemStack[] armorContents = inventory.getArmorContents();
            for (int i = 0; i < armorContents.length; i++) {
                ItemStack armorPiece = armorContents[i];
                if (armorPiece != null && isRestrictedArmor(armorPiece)) {
                    player.getWorld().dropItemNaturally(player.getLocation(), armorPiece);
                    armorContents[i] = null;
                }
            }
            inventory.setArmorContents(armorContents);
        }
    }

    // Verifica se o item é uma armadura restrita
    private boolean isRestrictedArmor(ItemStack item) {
        return blockCraft.isDiamond_Itens(item.getType()) ||
                blockCraft.isNetherite_Itens(item.getType());
    }

    // AO ATACAR COM PICARETA TEM DANO AUMENTADO
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (IsAnao(damager)) {
                Material material = damager.getInventory().getItemInMainHand().getType();
                if (PICKAXE_DAMAGE_MULTIPLIERS.containsKey(material)) {
                    int multiplier = PICKAXE_DAMAGE_MULTIPLIERS.get(material);
                    event.setDamage(event.getDamage() * multiplier);
                }
            }
        }
    }

    static {
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.WOODEN_HOE, 2);
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.STONE_HOE, 4);
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.IRON_HOE, 6);
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.GOLDEN_HOE, 8);
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.DIAMOND_HOE, 10);
        PICKAXE_DAMAGE_MULTIPLIERS.put(Material.NETHERITE_HOE, 12);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsAnao(player)) {
            int level = getPlayerLevel(player);
            int xpToGive = 7 * getXpForLevel(level);
            if (xpToGive > 0) {
                player.giveExp(xpToGive);
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null && IsAnao(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 7 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsAnao(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Anao".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}