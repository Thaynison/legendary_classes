package www.legendarycommunity.com.br.legendary_classes.classes;

import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import www.legendarycommunity.com.br.legendary_classes.Legendary_classes;
import www.legendarycommunity.com.br.legendary_classes.PlayerClassData;
import www.legendarycommunity.com.br.legendary_classes.sistemas.blockCraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Ares implements Listener {

    private static final Map<Material, Integer> HOE_DAMAGE_MULTIPLIERS = new EnumMap<>(Material.class);
    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public Ares(Legendary_classes plugin) {
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
    }

    // DESATIVAR SISTEMA DE CRAFT DE ITEM!
    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        if (result != null &&
                (blockCraft.itensMinecraft(result.getType()) ||
                        blockCraft.isIron_Itens(result.getType()) ||
                        blockCraft.isGold_Itens(result.getType()) ||
                        blockCraft.isDiamond_Itens(result.getType()) ||
                        blockCraft.isNetherite_Itens(result.getType()))) {
            Player player = (Player) event.getView().getPlayer();
            if (IsAres(player)) {
                inventory.setResult(null);
            }
        }
    }

    // DESATIVAR SISTEMA DE CRAFT DE ITEM!
    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        ItemStack result = event.getResult();
        if (result != null &&
                (blockCraft.itensMinecraft(result.getType()) ||
                        blockCraft.isIron_Itens(result.getType()) ||
                        blockCraft.isGold_Itens(result.getType()) ||
                        blockCraft.isDiamond_Itens(result.getType()) ||
                        blockCraft.isNetherite_Itens(result.getType()))) {
            Player player = (Player) event.getView().getPlayer();
            if (IsAres(player)) {
                event.setResult(null);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveApplyWeakness(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (IsAres(player)) {
            World world = player.getWorld();
            SimpleClans simpleClans = (SimpleClans) Bukkit.getPluginManager().getPlugin("SimpleClans");
            Material currentBlockType = player.getLocation().getBlock().getType();
            if ((currentBlockType == Material.SOUL_SAND || currentBlockType == Material.NETHERRACK) || currentBlockType == Material.NETHER_BRICK || currentBlockType == Material.SAND && player.isSwimming()) {
                for (Entity entity : world.getNearbyEntities(player.getLocation(), 10, 10, 10)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;

                        if (livingEntity instanceof Player) {
                            Player nearbyPlayer = (Player) livingEntity;
                            if (simpleClans != null) {
                                ClanPlayer AresClanPlayer = simpleClans.getClanManager().getClanPlayer(player);
                                ClanPlayer nearbyClanPlayer = simpleClans.getClanManager().getClanPlayer(nearbyPlayer);
                                if (AresClanPlayer != null && nearbyClanPlayer != null &&
                                        AresClanPlayer.getClan() != null &&
                                        AresClanPlayer.getClan().equals(nearbyClanPlayer.getClan())) {
                                    nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 5));
                                    nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60, 2));
                                    nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 2));
                                    continue;
                                }
                            }
                        }
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60, 5));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1));
                    }
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 60, 5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60, 5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 5));
            }
        }
    }

    // AO ATACAR COM ENCHADA TEM DANO AUMENTADO
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (IsAres(damager)) {
                Material material = damager.getInventory().getItemInMainHand().getType();
                if (HOE_DAMAGE_MULTIPLIERS.containsKey(material)) {
                    int multiplier = HOE_DAMAGE_MULTIPLIERS.get(material);
                    event.setDamage(event.getDamage() * multiplier);
                }
            }
        }
    }

    static {
        HOE_DAMAGE_MULTIPLIERS.put(Material.WOODEN_HOE, 5);
        HOE_DAMAGE_MULTIPLIERS.put(Material.STONE_HOE, 10);
        HOE_DAMAGE_MULTIPLIERS.put(Material.IRON_HOE, 15);
        HOE_DAMAGE_MULTIPLIERS.put(Material.GOLDEN_HOE, 20);
        HOE_DAMAGE_MULTIPLIERS.put(Material.DIAMOND_HOE, 25);
        HOE_DAMAGE_MULTIPLIERS.put(Material.NETHERITE_HOE, 30);
    }

    // AO QUEBRAR GANHA XP
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsAres(player)) {
            int level = getPlayerLevel(player);
            int xpToGive = 9 * getXpForLevel(level);
            if (xpToGive > 0) {
                player.giveExp(xpToGive);
            }
        }
    }

    // AO MATAR GANHA XP
    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null && IsAres(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 9 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsAres(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Ares".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}