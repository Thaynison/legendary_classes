package www.legendarycommunity.com.br.legendary_classes.classes;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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

public class Necromancer implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>(); // Mapa para armazenar o tempo do último uso

    public Necromancer(Legendary_classes plugin) {
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
            if (IsNecromancer(player)) {
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
            if (IsNecromancer(player)) {
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
                if (item != null && isRestrictedArmor(item) && IsNecromancer(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsNecromancer(player)) {
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
            if (item != null && isRestrictedArmor(item) && IsNecromancer(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsNecromancer(player)) {
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
    public void onSpawnMobs(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (Bukkit.getPluginManager().getPlugin("AuraSkills") == null) {
                return;
            }
            AuraSkillsApi auraSkills = AuraSkillsApi.get();
            if (IsNecromancer(player)) {

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
                if (manaAtual >= 60) {
                    skillsUser.setMana(manaAtual - 60);
                    event.setCancelled(true);
                    player.damage(150.0);
                    World world = player.getWorld();
                    Location location = player.getLocation().add(2, 0, 2);
                    for (int i = 0; i < 3; i++) {
                        Zombie zombie = world.spawn(location, Zombie.class);
                        zombie.setCustomName("Mine Necromancer");
                        location.add(2, 0, 0);
                        zombie.setTarget(null);
                        zombie.setRemoveWhenFarAway(false);
                    }
                    location = player.getLocation().add(-2, 0, 2);
                    for (int i = 0; i < 3; i++) {
                        Skeleton skeleton = world.spawn(location, Skeleton.class);
                        skeleton.setCustomName("Mine Necromancer");
                        location.add(-2, 0, 0);
                        skeleton.setTarget(null);
                        skeleton.setRemoveWhenFarAway(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Zombie || entity instanceof Skeleton) {
            if ("Mine Necromancer".equals(entity.getCustomName())) {
                Entity target = event.getTarget();
                if (target instanceof Player && IsNecromancer((Player) target)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsNecromancer(player)) {
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
        if (killer != null && IsNecromancer(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 6 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsNecromancer(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Necromancer".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}