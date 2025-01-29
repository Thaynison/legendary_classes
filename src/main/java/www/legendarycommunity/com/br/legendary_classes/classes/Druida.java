package www.legendarycommunity.com.br.legendary_classes.classes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Druida implements Listener {

    private final Set<Player> playersWithEffect = new HashSet<>();
    private final Map<Integer, Integer> levels;
    private final Legendary_classes plugin;

    public Druida(Legendary_classes plugin) {
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
            if (IsDruida(player)) {
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
            if (IsDruida(player)) {
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
                if (item != null && isRestrictedArmor(item) && IsDruida(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                ItemStack item = event.getCurrentItem();
                if (item != null && isRestrictedArmor(item) && IsDruida(player)) {
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
            if (item != null && isRestrictedArmor(item) && IsDruida(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (IsDruida(player)) {
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
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Material blockType = player.getLocation().add(0, -1, 0).getBlock().getType();

        // Verifica se o jogador está em um bloco apropriado
        if (blockType == Material.GRASS_BLOCK || blockType == Material.DIRT || blockType == Material.COARSE_DIRT || blockType == Material.SAND) {
            if (IsDruida(player)) { // Função personalizada para verificar se o player é "Druida"
                if (!playersWithEffect.contains(player)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400, 5)); // 20 segundos
                    playersWithEffect.add(player);
                }

                // Fazer todas as plantas crescerem instantaneamente dentro de um raio de 10 blocos
                int radius = 10;
                Location playerLocation = player.getLocation();
                World world = player.getWorld();

                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Location checkLocation = playerLocation.clone().add(x, y, z);
                            Block block = world.getBlockAt(checkLocation);

                            // Verifica se o bloco é um tipo de planta que pode crescer
                            if (block.getType() == Material.WHEAT ||
                                    block.getType() == Material.OAK_SAPLING ||
                                    block.getType() == Material.SPRUCE_SAPLING ||
                                    block.getType() == Material.BIRCH_SAPLING ||
                                    block.getType() == Material.JUNGLE_SAPLING ||
                                    block.getType() == Material.ACACIA_SAPLING ||
                                    block.getType() == Material.DARK_OAK_SAPLING ||
                                    block.getType() == Material.CHERRY_SAPLING ||
                                    block.getType() == Material.MANGROVE_PROPAGULE ||
                                    block.getType() == Material.SWEET_BERRIES ||
                                    block.getType() == Material.BAMBOO ||
                                    block.getType() == Material.SUGAR_CANE ||
                                    block.getType() == Material.NETHER_WART ||
                                    block.getType() == Material.MELON_SEEDS ||
                                    block.getType() == Material.PUMPKIN_SEEDS ||
                                    block.getType() == Material.WHEAT_SEEDS ||
                                    block.getType() == Material.CARROTS ||
                                    block.getType() == Material.POTATOES ||
                                    block.getType() == Material.BEETROOTS) {
                                BlockData blockData = block.getBlockData();
                                if (blockData instanceof Ageable) {
                                    Ageable ageable = (Ageable) blockData;
                                    // Faz a planta crescer ao estágio máximo
                                    ageable.setAge(ageable.getMaximumAge());
                                    block.setBlockData(ageable);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (playersWithEffect.contains(player)) {
                player.removePotionEffect(PotionEffectType.REGENERATION);
                playersWithEffect.remove(player);
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (IsDruida(player)) {
            int level = getPlayerLevel(player);
            int xpToGive = 8 * getXpForLevel(level);
            if (xpToGive > 0) {
                player.giveExp(xpToGive);
            }
        }
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer != null && IsDruida(killer)) {
            int level = getPlayerLevel(killer);
            int xpToGive = 8 * getXpForLevel(level);
            if (xpToGive > 0) {
                killer.giveExp(xpToGive);
            }
        }
    }

    public boolean IsDruida(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null && "Druida".equalsIgnoreCase(data.getClassName());
    }

    public int getPlayerLevel(Player player) {
        PlayerClassData data = plugin.getPlayerData(player.getUniqueId());
        return data != null ? data.getClassLevel() : -1;
    }

    private int getXpForLevel(int level) {
        return levels.getOrDefault(level, 0);
    }
}