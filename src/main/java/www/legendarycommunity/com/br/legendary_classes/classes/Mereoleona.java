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
import org.bukkit.event.entity.EntityDamageEvent;
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

public class Mereoleona implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public Mereoleona(Legendary_classes plugin) {
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

        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_WISDOM", Stats.WISDOM, 60000.0));
        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_HEALTH", Stats.HEALTH, 60000.0));
        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_STRENGTH", Stats.STRENGTH, 60000.0));
        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_TOUGHNESS", Stats.TOUGHNESS, 60000.0));
        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_LUCK", Stats.LUCK, 20.0));
        skillsUser.addStatModifier(new StatModifier("Mereoleona_System_SPEED", Stats.SPEED, 20.0));
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
            if (IsMereoleona(player)) {
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
            if (IsMereoleona(player)) {
                event.setResult(null);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveApplyForca(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (IsMereoleona(player)) {
            // Tornar o jogador imune a fogo e lava
            if (player.getFireTicks() > 0 || player.getLocation().getBlock().getType() == Material.LAVA) {
                player.setFireTicks(0);
            }

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
                                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 60, 10));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (IsMereoleona(player)) {
                // Cancelar dano de fogo ou lava
                if (event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                        event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                        event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Verifica se o jogador é o específico
        if (IsMereoleona(player)) {
            // Verifica se o jogador está segurando uma vara de Blaze (ou outro item definido como "atalho")
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand != null && itemInHand.getType() == Material.BLAZE_ROD) {
                Location location = player.getLocation();
                Block blockBelow = location.clone().subtract(0, 1, 0).getBlock(); // Bloco abaixo do jogador

                // Verifica se o bloco abaixo é grama
                if (blockBelow.getType() == Material.GRASS_BLOCK) {
                    World world = player.getWorld();

                    // Coloca fogo normal no bloco acima da grama
                    Block blockAbove = blockBelow.getRelative(0, 1, 0);
                    if (blockAbove.getType() == Material.AIR) {
                        blockAbove.setType(Material.FIRE);
                    }

                    // Coloca fogo normal em um raio de 10 blocos ao redor
                    for (int x = -10; x <= 10; x++) {
                        for (int z = -10; z <= 10; z++) {
                            Location fireLocation = blockBelow.getLocation().clone().add(x, 1, z);
                            Block blockAtFireLocation = fireLocation.getBlock();

                            // Apenas coloca fogo normal em blocos de ar
                            if (blockAtFireLocation.getType() == Material.AIR) {
                                blockAtFireLocation.setType(Material.FIRE);
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

            if (IsMereoleona(player)) {

                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand != null && itemInHand.getType() == Material.BOOK) {

                    ItemMeta itemMeta = itemInHand.getItemMeta();
                    if (itemMeta != null) {
                        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());

                        if (itemName.equals("Grimorio do Fogo")) {

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
            if (IsMereoleona(player)) {
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if (itemInHand != null && itemInHand.getType() == Material.BOOK) {
                    ItemMeta itemMeta = itemInHand.getItemMeta();
                    if (itemMeta != null) {
                        String itemName = ChatColor.stripColor(itemMeta.getDisplayName());
                        if (itemName.equals("Grimorio do Fogo")) {
                            // Aqui você lança o WindCharge
                            launchFireball(player);
                        }
                    }
                }
            }
        }
    }

    private void launchFireball(Player player) {
        Fireball fireball = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);
        fireball.setYield(0);
        fireball.setIsIncendiary(false);
        fireball.setVelocity(player.getEyeLocation().getDirection().multiply(1.5));
        fireball.setCustomName("Fogo do Purgatorio");
        fireball.setShooter(player);
    }

    @EventHandler
    public void onFireballHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getDamager();
            if ("Fogo do Purgatorio".equals(fireball.getCustomName()) && fireball.getShooter() instanceof Player) {
                event.setDamage(60000);
                Entity entity = event.getEntity();
                spawnLightningAroundEntity(entity);
            }
        }
    }

    private void spawnLightningAroundEntity(Entity entity) {
        Location location = entity.getLocation();
        World world = entity.getWorld();
        for (int i = 0; i < 3; i++) {
            Location lightningLocation = location.clone().add(new Vector(Math.random() * 10 - 5, 0, Math.random() * 10 - 5));
            world.strikeLightning(lightningLocation);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsMereoleona(player)) {
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
        if (killer != null && IsMereoleona(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 6 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsMereoleona(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Mereoleona".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}