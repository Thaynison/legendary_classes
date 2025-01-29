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
import java.util.UUID;

public class DorothyUnsworth implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public DorothyUnsworth(Legendary_classes plugin) {
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

        skillsUser.removeStatModifier("DorothyUnsworth_System_WISDOM");
        skillsUser.removeStatModifier("DorothyUnsworth_System_HEALTH");
        skillsUser.removeStatModifier("DorothyUnsworth_System_STRENGTH");
        skillsUser.removeStatModifier("DorothyUnsworth_System_TOUGHNESS");
        skillsUser.removeStatModifier("DorothyUnsworth_System_LUCK");
        skillsUser.removeStatModifier("DorothyUnsworth_System_SPEED");
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

        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_WISDOM", Stats.WISDOM, 4096.0));
        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_HEALTH", Stats.HEALTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_STRENGTH", Stats.STRENGTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_TOUGHNESS", Stats.TOUGHNESS, 4096.0));
        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_LUCK", Stats.LUCK, 20.0));
        skillsUser.addStatModifier(new StatModifier("DorothyUnsworth_System_SPEED", Stats.SPEED, 20.0));
    }

    @EventHandler
    public void onPlayerMoveApplyWeakness(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (IsDorothyUnsworth(player)) {
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
                                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 60, 10));
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
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (IsDorothyUnsworth(player)) {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand != null && itemInHand.getType() == Material.BOOK) {
                    ItemMeta itemMeta = itemInHand.getItemMeta();
                    if (itemMeta != null) {
                        // Remove cores e formatações do nome do item
                        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());
                        if (itemName != null && itemName.equalsIgnoreCase("Grimorio do Sonho")) {
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

            if (IsDorothyUnsworth(player)) {
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
                        if (itemName != null && itemName.equalsIgnoreCase("Grimorio do Sonho")) {
                            // Verificar cooldown
                            if (cooldowns.containsKey(playerId)) {
                                long lastUseTime = cooldowns.get(playerId);
                                long timeElapsed = (currentTime - lastUseTime) / 1000; // Converter para segundos

                                if (timeElapsed < 5) {
                                    player.sendMessage("§cVocê precisa esperar " + (5 - timeElapsed) + " segundos para usar isso novamente!");
                                    return;
                                }
                            }

                            // Verificar se o jogador tem mana suficiente
                            if (manaAtual >= 500) { // Mana necessária para ativar a habilidade
                                // Registrar o tempo atual para o cooldown
                                cooldowns.put(playerId, currentTime);

                                // Pegar a entidade mais próxima do jogador
                                Entity target = getTargetEntity(player, 5); // 5 é a distância máxima para selecionar a entidade
                                if (target != null) {
                                    // Adiciona um efeito de náusea
                                    if (target instanceof LivingEntity livingTarget) {
                                        livingTarget.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 600, 10)); // Duração: 30s, Nível: 10
                                        livingTarget.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 10)); // Duração: 30s, Nível: 10
                                        livingTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 10)); // Duração: 30s, Nível: 10
                                    }

                                    // Trava a entidade
                                    target.setVelocity(target.getVelocity().setX(0).setY(0).setZ(0));
                                    target.setGravity(false);

                                    // Após 30 segundos, liberar a entidade
                                    Bukkit.getScheduler().runTaskLater(Legendary_classes.getInstance(), () -> {
                                        target.setGravity(true);
                                    }, 600L); // 600 ticks = 30 segundos

                                    // Consumir a mana
                                    skillsUser.setMana(manaAtual - 500);
                                    event.setCancelled(true);
                                }
                            } else {
                                player.sendMessage("§cVocê não tem mana suficiente para usar essa habilidade!");
                            }
                        }
                    }
                }
            }
        }
    }

    private Entity getTargetEntity(Player player, double range) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();
        World world = player.getWorld();

        for (double i = 0; i <= range; i += 0.5) {
            Location checkLocation = eyeLocation.clone().add(direction.clone().multiply(i));
            for (Entity entity : world.getNearbyEntities(checkLocation, 0.5, 0.5, 0.5)) {
                if (entity != player) {
                    return entity;
                }
            }
        }
        return null;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsDorothyUnsworth(player)) {
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
        if (killer != null && IsDorothyUnsworth(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 55 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsDorothyUnsworth(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "DorothyUnsworth".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}