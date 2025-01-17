package www.legendarycommunity.com.br.legendary_classes.classes;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.*;
import org.bukkit.entity.*;
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

public class CavaleiroDaGuerra implements Listener {

    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public CavaleiroDaGuerra(Legendary_classes plugin) {
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
            if (IsCavaleiroDaGuerra(player)) {
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
            if (IsCavaleiroDaGuerra(player)) {
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
                if (item != null && isRestrictedArmor(item) && IsCavaleiroDaGuerra(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsCavaleiroDaGuerra(player)) {
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
            if (item != null && isRestrictedArmor(item) && IsCavaleiroDaGuerra(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsCavaleiroDaGuerra(player)) {
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
    public void onLancaTrident(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR)) {
            if (Bukkit.getPluginManager().getPlugin("AuraSkills") == null) {
                return;
            }
            AuraSkillsApi auraSkills = AuraSkillsApi.get();
            if (IsCavaleiroDaGuerra(player)) {
                SkillsUser skillsUser = auraSkills.getUser(player.getUniqueId());
                if (skillsUser == null) {
                    return;
                }
                double manaAtual = skillsUser.getMana();
                if (manaAtual >= 80) {
                    skillsUser.setMana(manaAtual - 80);
                    launchTrident(player);
                    event.setCancelled(true);
                }
            }
        }
    }

    private void launchTrident(Player player) {
        World world = player.getWorld();
        Location location = player.getLocation();
        Trident trident = world.spawn(location, Trident.class);
        trident.setCustomName("Lanca do Odio");
        trident.setPickupStatus(Trident.PickupStatus.CREATIVE_ONLY);
        Vector direction = player.getLocation().getDirection().multiply(2);
        trident.setVelocity(direction);
        trident.setShooter(player);
        Bukkit.getScheduler().runTaskLater(SimpleClans.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (!trident.isDead()) {
                    trident.remove();
                }
            }
        }, 50L);
    }

    @EventHandler
    public void onTridentHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Trident) {
            Trident trident = (Trident) event.getDamager();
            if ("Lanca do Odio".equals(trident.getCustomName()) && trident.getShooter() instanceof Player) {
                event.setDamage(15);
                createMultipleExplosions(trident.getLocation(), 4, 2.0F);
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
                world.createExplosion(explosionLocation, power, false, false); // Cria a explosão
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsCavaleiroDaGuerra(player)) {
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
        if (killer != null && IsCavaleiroDaGuerra(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 7 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsCavaleiroDaGuerra(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "CavaleiroDaGuerra".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}