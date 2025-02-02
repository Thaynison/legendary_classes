package www.legendarycommunity.com.br.legendary_classes.classes;

import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
import java.util.HashMap;
import java.util.Map;

public class CavaleiroDaPeste implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public CavaleiroDaPeste(Legendary_classes plugin) {
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
            if (IsCavaleiroDaPeste(player)) {
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
            if (IsCavaleiroDaPeste(player)) {
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
                if (item != null && isRestrictedArmor(item) && IsCavaleiroDaPeste(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsCavaleiroDaPeste(player)) {
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
            if (item != null && isRestrictedArmor(item) && IsCavaleiroDaPeste(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsCavaleiroDaPeste(player)) {
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
        return blockCraft.isNetherite_Itens(item.getType());
    }

    @EventHandler
    public void onPlayerMoveApplyVeneno(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (IsCavaleiroDaPeste(player)) {
            World world = player.getWorld();
            SimpleClans simpleClans = (SimpleClans) Bukkit.getPluginManager().getPlugin("SimpleClans");
            for (Entity entity : world.getNearbyEntities(player.getLocation(), 5, 5, 5)) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (livingEntity instanceof Player) {
                        Player targetPlayer = (Player) livingEntity;
                        if (targetPlayer.equals(player)) {
                            continue;
                        }
                        if (IsCavaleiroDaPeste(targetPlayer)) {
                            continue;
                        }
                        if (simpleClans != null) {
                            ClanPlayer playerClan = simpleClans.getClanManager().getClanPlayer(player);
                            ClanPlayer targetClan = simpleClans.getClanManager().getClanPlayer(targetPlayer);
                            if (playerClan != null && targetClan != null &&
                                    playerClan.getClan() != null &&
                                    playerClan.getClan().equals(targetClan.getClan())) {
                                continue;
                            }
                        }
                    }
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 5));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 5));
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsCavaleiroDaPeste(player)) {
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
        if (killer != null && IsCavaleiroDaPeste(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 7 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsCavaleiroDaPeste(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "CavaleiroDaPeste".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}