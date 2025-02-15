package www.legendarycommunity.com.br.legendary_classes.classes;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import www.legendarycommunity.com.br.legendary_classes.Legendary_classes;
import www.legendarycommunity.com.br.legendary_classes.PlayerClassData;
import www.legendarycommunity.com.br.legendary_classes.sistemas.blockCraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Lemiel implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public Lemiel(Legendary_classes plugin) {
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

        if (Bukkit.getPluginManager().getPlugin("AuraSkills") == null) {
            return;
        }
        AuraSkillsApi auraSkills = AuraSkillsApi.get();
        SkillsUser skillsUser = auraSkills.getUser(player.getUniqueId());
        if (skillsUser == null) {
            return;
        }

        skillsUser.removeStatModifier("SupremaDivindade_System_WISDOM");
        skillsUser.removeStatModifier("SupremaDivindade_System_HEALTH");
        skillsUser.removeStatModifier("SupremaDivindade_System_STRENGTH");
        skillsUser.removeStatModifier("SupremaDivindade_System_TOUGHNESS");
        skillsUser.removeStatModifier("SupremaDivindade_System_LUCK");
        skillsUser.removeStatModifier("SupremaDivindade_System_SPEED");
        skillsUser.removeStatModifier("ReiDemonio_System_WISDOM");
        skillsUser.removeStatModifier("ReiDemonio_System_HEALTH");
        skillsUser.removeStatModifier("ReiDemonio_System_STRENGTH");
        skillsUser.removeStatModifier("ReiDemonio_System_TOUGHNESS");
        skillsUser.removeStatModifier("ReiDemonio_System_LUCK");
        skillsUser.removeStatModifier("ReiDemonio_System_SPEED");
        skillsUser.removeStatModifier("Mereoleona_System_WISDOM");
        skillsUser.removeStatModifier("Mereoleona_System_HEALTH");
        skillsUser.removeStatModifier("Mereoleona_System_STRENGTH");
        skillsUser.removeStatModifier("Mereoleona_System_TOUGHNESS");
        skillsUser.removeStatModifier("Mereoleona_System_LUCK");
        skillsUser.removeStatModifier("Mereoleona_System_SPEED");
        skillsUser.removeStatModifier("Demiurgo_System_WISDOM");
        skillsUser.removeStatModifier("Demiurgo_System_HEALTH");
        skillsUser.removeStatModifier("Demiurgo_System_STRENGTH");
        skillsUser.removeStatModifier("Demiurgo_System_TOUGHNESS");
        skillsUser.removeStatModifier("Demiurgo_System_LUCK");
        skillsUser.removeStatModifier("Demiurgo_System_SPEED");
        skillsUser.removeStatModifier("CaosDivindade_System_WISDOM");
        skillsUser.removeStatModifier("CaosDivindade_System_HEALTH");
        skillsUser.removeStatModifier("CaosDivindade_System_STRENGTH");
        skillsUser.removeStatModifier("CaosDivindade_System_TOUGHNESS");
        skillsUser.removeStatModifier("CaosDivindade_System_LUCK");
        skillsUser.removeStatModifier("CaosDivindade_System_SPEED");
        skillsUser.removeStatModifier("Albedo_System_WISDOM");
        skillsUser.removeStatModifier("Albedo_System_HEALTH");
        skillsUser.removeStatModifier("Albedo_System_STRENGTH");
        skillsUser.removeStatModifier("Albedo_System_TOUGHNESS");
        skillsUser.removeStatModifier("Albedo_System_LUCK");
        skillsUser.removeStatModifier("Albedo_System_SPEED");
        skillsUser.removeStatModifier("AinzOoalGown_System_WISDOM");
        skillsUser.removeStatModifier("AinzOoalGown_System_HEALTH");
        skillsUser.removeStatModifier("AinzOoalGown_System_STRENGTH");
        skillsUser.removeStatModifier("AinzOoalGown_System_TOUGHNESS");
        skillsUser.removeStatModifier("AinzOoalGown_System_LUCK");
        skillsUser.removeStatModifier("AinzOoalGown_System_SPEED");
        skillsUser.removeStatModifier("Lemiel_System_WISDOM");
        skillsUser.removeStatModifier("Lemiel_System_HEALTH");
        skillsUser.removeStatModifier("Lemiel_System_STRENGTH");
        skillsUser.removeStatModifier("Lemiel_System_TOUGHNESS");
        skillsUser.removeStatModifier("Lemiel_System_LUCK");
        skillsUser.removeStatModifier("Lemiel_System_SPEED");
        skillsUser.removeStatModifier("DorothyUnsworth_System_WISDOM");
        skillsUser.removeStatModifier("DorothyUnsworth_System_HEALTH");
        skillsUser.removeStatModifier("DorothyUnsworth_System_STRENGTH");
        skillsUser.removeStatModifier("DorothyUnsworth_System_TOUGHNESS");
        skillsUser.removeStatModifier("DorothyUnsworth_System_LUCK");
        skillsUser.removeStatModifier("DorothyUnsworth_System_SPEED");

        skillsUser.addStatModifier(new StatModifier("Lemiel_System_WISDOM", Stats.WISDOM, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Lemiel_System_HEALTH", Stats.HEALTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Lemiel_System_STRENGTH", Stats.STRENGTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Lemiel_System_TOUGHNESS", Stats.TOUGHNESS, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Lemiel_System_LUCK", Stats.LUCK, 20.0));
        skillsUser.addStatModifier(new StatModifier("Lemiel_System_SPEED", Stats.SPEED, 20.0));
    }

    @EventHandler
    public void onPlayerMoveApplyWeakness(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (IsLemiel(player)) {
            World world = player.getWorld();
            SimpleClans simpleClans = (SimpleClans) Bukkit.getPluginManager().getPlugin("SimpleClans");

            for (Entity entity : world.getNearbyEntities(player.getLocation(), 20, 20, 20)) {
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
                                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 10));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onGrimorioInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (IsLemiel(player)) {

                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand != null && itemInHand.getType() == Material.BOOK) {

                    ItemMeta itemMeta = itemInHand.getItemMeta();
                    if (itemMeta != null) {
                        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());
                        if (itemName != null && itemName.equalsIgnoreCase("Grimorio de Luz")) {

                            if (player.getAllowFlight()) {
                                player.setAllowFlight(false);
                            } else {
                                player.setAllowFlight(true);
                            }
                        }
                    }
                }
            }
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (Bukkit.getPluginManager().getPlugin("AuraSkills") == null) {
                return;
            }
            AuraSkillsApi auraSkills = AuraSkillsApi.get();
            if (IsLemiel(player)) {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                SkillsUser skillsUser = auraSkills.getUser(player.getUniqueId());
                if (skillsUser == null) {
                    return;
                }
                double manaAtual = skillsUser.getMana();
                if (itemInHand != null && itemInHand.getType() == Material.BOOK) {
                    ItemMeta itemMeta = itemInHand.getItemMeta();
                    if (itemMeta != null) {
                        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());
                        if (itemName != null && itemName.equalsIgnoreCase("Grimorio de Luz")) {
                            // Aqui você lança o WindCharge
                            if (manaAtual >= 500) {
                                launchWindCharge(player);
                                skillsUser.setMana(manaAtual - 500);
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void launchWindCharge(Player player) {
        WindCharge windCharge = player.getWorld().spawn(player.getEyeLocation(), WindCharge.class);
        windCharge.setCustomName("Luz");
        windCharge.setCustomNameVisible(false);
        windCharge.setShooter(player);
        Vector velocity = player.getEyeLocation().getDirection().multiply(1.5);
        windCharge.setVelocity(velocity);
        windCharge.setGravity(false);
    }

    @EventHandler
    public void onWindChargeHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof WindCharge) {
            WindCharge windCharge = (WindCharge) event.getDamager();
            if ("Luz".equals(windCharge.getCustomName()) && windCharge.getShooter() instanceof Player) {
                event.setDamage(100000);
                createMultipleExplosions(windCharge.getLocation(), 4, 2.0F);
            }
        }
    }

    private void createMultipleExplosions(org.bukkit.Location location, int count, float power) {
        World world = location.getWorld();
        if (world != null) {
            for (int i = 0; i < count; i++) {
                double offsetX = (Math.random() - 0.5) * 2.0;
                double offsetY = (Math.random() - 0.5) * 2.0;
                double offsetZ = (Math.random() - 0.5) * 2.0;
                org.bukkit.Location explosionLocation = location.clone().add(offsetX, offsetY, offsetZ);
                world.createExplosion(explosionLocation, power, false, false);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsLemiel(player)) {
            int level = getPlayerLevel(player);
            int xpToGive = 55 * getXpForLevel(level);
            if (xpToGive > 0) {
                player.giveExp(xpToGive);
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null && IsLemiel(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 55 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsLemiel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Lemiel".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}