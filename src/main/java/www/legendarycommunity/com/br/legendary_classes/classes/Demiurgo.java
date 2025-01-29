package www.legendarycommunity.com.br.legendary_classes.classes;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.StatModifier;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
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

public class Demiurgo implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>(); // Mapa para armazenar o tempo do último uso

    public Demiurgo(Legendary_classes plugin) {
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

        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_WISDOM", Stats.WISDOM, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_HEALTH", Stats.HEALTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_STRENGTH", Stats.STRENGTH, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_TOUGHNESS", Stats.TOUGHNESS, 4096.0));
        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_LUCK", Stats.LUCK, 20.0));
        skillsUser.addStatModifier(new StatModifier("Demiurgo_System_SPEED", Stats.SPEED, 20.0));
    }

    @EventHandler
    public void onSpawnMobs(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (Bukkit.getPluginManager().getPlugin("AuraSkills") == null) {
                return;
            }
            AuraSkillsApi auraSkills = AuraSkillsApi.get();
            if (IsDemiurgo(player)) {


                // Verificar cooldown
                long currentTime = System.currentTimeMillis();
                UUID playerId = player.getUniqueId();

                if (cooldowns.containsKey(playerId)) {
                    long lastUseTime = cooldowns.get(playerId);
                    long timeElapsed = (currentTime - lastUseTime) / 1000; // Converter para segundos

                    if (timeElapsed < 30) {
                        player.sendMessage("§cVocê precisa esperar " + (30 - timeElapsed) + " segundos para usar isso novamente!");
                        return;
                    }
                }

                // Registrar o tempo atual para o cooldown
                cooldowns.put(playerId, currentTime);

                SkillsUser skillsUser = auraSkills.getUser(player.getUniqueId());
                if (skillsUser == null) {
                    return;
                }
                double manaAtual = skillsUser.getMana();
                if (manaAtual >= 500) {
                    skillsUser.setMana(manaAtual - 500);
                    event.setCancelled(true);
                    World world = player.getWorld();
                    Location location = player.getLocation().add(2, 0, 2);
                    ItemStack espadaInfernal = new ItemStack(Material.IRON_SWORD);
                    ItemMeta meta = espadaInfernal.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName("Espada Infernal"); // Configurar o nome
                        espadaInfernal.setItemMeta(meta); // Aplicar o meta no item
                    }
                    for (int i = 0; i < 6; i++) {
                        Warden warden = world.spawn(location, Warden.class);
                        warden.setCustomName("Cavaleiro da Morte");
                        warden.setCustomNameVisible(true); // Mostrar o nome
                        if (warden.getEquipment() != null) {
                            warden.getEquipment().setItemInMainHand(espadaInfernal);
                            warden.getEquipment().setItemInMainHandDropChance(0f);
                            warden.getEquipment().setHelmet(null);
                            warden.getEquipment().setHelmetDropChance(0f);
                            warden.getEquipment().setChestplate(null);
                            warden.getEquipment().setChestplateDropChance(0f);
                            warden.getEquipment().setLeggings(null);
                            warden.getEquipment().setLeggingsDropChance(0f);
                            warden.getEquipment().setBoots(null);
                            warden.getEquipment().setBootsDropChance(0f);
                        }
                        location.add(2, 0, 0);
                        warden.setTarget(null);
                        warden.setRemoveWhenFarAway(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Warden) {
            Warden warden = (Warden) entity;
            if ("Cavaleiro da Morte".equals(warden.getCustomName())) {
                Entity target = event.getTarget();
                if (target instanceof Player && IsDemiurgo((Player) target)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsDemiurgo(player)) {
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
        if (killer != null && IsDemiurgo(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 55 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsDemiurgo(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Demiurgo".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}