package www.legendarycommunity.com.br.legendary_classes.classes;

import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
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
import java.util.HashMap;
import java.util.Map;

public class MortoVivo implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public MortoVivo(Legendary_classes plugin) {
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
            if (IsMortoVivo(player)) {
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
            if (IsMortoVivo(player)) {
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
                if (item != null && isRestrictedArmor(item) && IsMortoVivo(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsMortoVivo(player)) {
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
            if (item != null && isRestrictedArmor(item) && IsMortoVivo(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsMortoVivo(player)) {
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

    @EventHandler
    public void onAttackSanguineClan(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }
        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        if (IsMortoVivo(attacker)) {
            SimpleClans simpleClans = (SimpleClans) Bukkit.getPluginManager().getPlugin("SimpleClans");
            if (simpleClans != null) {
                ClanPlayer attackerClanPlayer = simpleClans.getClanManager().getClanPlayer(attacker);
                ClanPlayer victimClanPlayer = simpleClans.getClanManager().getClanPlayer(victim);
                if (attackerClanPlayer != null && victimClanPlayer != null
                        && attackerClanPlayer.getClan() != null
                        && attackerClanPlayer.getClan().equals(victimClanPlayer.getClan())) {
                    return;
                }
            }
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 10 * 20, 1));
            double newHealth = Math.min(attacker.getHealth() + 20.0, attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            attacker.setHealth(newHealth);
            if (simpleClans != null) {
                ClanPlayer attackerClanPlayer = simpleClans.getClanManager().getClanPlayer(attacker);
                if (attackerClanPlayer != null && attackerClanPlayer.getClan() != null) {
                    Clan clan = attackerClanPlayer.getClan();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        ClanPlayer onlineClanPlayer = simpleClans.getClanManager().getClanPlayer(onlinePlayer);
                        if (onlineClanPlayer != null && onlineClanPlayer.getClan() == clan && !onlinePlayer.equals(attacker)) {
                            double clanMemberNewHealth = Math.min(onlinePlayer.getHealth() + 2.0, onlinePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                            onlinePlayer.setHealth(clanMemberNewHealth);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (IsMortoVivo(player) && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (IsMortoVivo(player)) {
                PotionEffect newEffect = event.getNewEffect();
                if (newEffect != null &&
                        (newEffect.getType() == PotionEffectType.WEAKNESS)) {
                    double healingAmount = 20.0 * (newEffect.getAmplifier() + 1);
                    double newHealth = Math.min(player.getHealth() + healingAmount, player.getMaxHealth());
                    player.setHealth(newHealth);
                    event.setCancelled(true);
                }
                if (newEffect != null &&
                        (newEffect.getType() == PotionEffectType.REGENERATION ||
                                newEffect.getType() == PotionEffectType.INSTANT_HEALTH ||
                                newEffect.getType() == PotionEffectType.HEALTH_BOOST)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsMortoVivo(player)) {
            int level = getPlayerLevel(player);
            int xpToGive = 6 * getXpForLevel(level);
            if (xpToGive > 0) {
                player.giveExp(xpToGive);
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null && IsMortoVivo(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 6 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsMortoVivo(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "MortoVivo".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}