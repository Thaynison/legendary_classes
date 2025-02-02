package www.legendarycommunity.com.br.legendary_classes.guis;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import www.legendarycommunity.com.br.legendary_classes.Legendary_classes;
import www.legendarycommunity.com.br.legendary_classes.PlayerClassData;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class magosRunasGui implements CommandExecutor, Listener {

    private final Legendary_classes plugin;
    private final Map<String, List<String>> classRunes = new HashMap<>();
    private final Map<String, Integer> runeStarCost = new HashMap<>();

    public magosRunasGui(Legendary_classes plugin) {
        this.plugin = plugin;
        initializeClassRunes();
        initializeRuneStarCosts();
    }

    private void initializeClassRunes() {
        classRunes.put("Druida", List.of("common", "rare"));
        classRunes.put("Driade", List.of("common", "rare", "epic"));
        classRunes.put("TuathaDeDanann", List.of("common", "rare", "epic"));
        classRunes.put("Overlord", List.of("common", "rare", "epic", "legendary"));
        classRunes.put("Mago", List.of("common", "rare", "epic", "legendary"));
        classRunes.put("MagoRegente", List.of("common", "rare", "epic", "legendary"));
        classRunes.put("ReiMago ", List.of("common", "rare", "epic", "legendary", "divine"));
        classRunes.put("AinzOoalGown ", List.of("common", "rare", "epic", "legendary", "divine"));
        classRunes.put("Lemial", List.of("common", "rare", "epic", "legendary", "divine"));
    }

    private void initializeRuneStarCosts() {
        runeStarCost.put("common", 5);
        runeStarCost.put("rare", 10);
        runeStarCost.put("epic", 15);
        runeStarCost.put("legendary", 20);
        runeStarCost.put("divine", 25);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("magosrunas")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /magosrunas <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory MagusMenu = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Mesa Arcana");

            // Preenchendo com vidro colorido, exceto os slots especificados
            for (int i = 0; i < 27; i++) {
                if (i != 9 && i != 11 && i != 13 && i != 15 && i != 17) {
                    MagusMenu.setItem(i, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1));
                }
            }

            MagusMenu.setItem(9, createRune("Comum", ChatColor.GRAY));
            MagusMenu.setItem(11, createRune("Rara", ChatColor.GOLD));
            MagusMenu.setItem(13, createRune("Epica", ChatColor.LIGHT_PURPLE));
            MagusMenu.setItem(15, createRune("Lendaria", ChatColor.DARK_PURPLE));
            MagusMenu.setItem(17, createRune("Divina", ChatColor.AQUA));

            targetPlayer.openInventory(MagusMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        }
        return false;
    }

    private ItemStack createRune(String name, ChatColor color) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color + "Runa " + name);
            List<String> lore = Arrays.asList(
                    "§7- §fInformação da §f[§dRuna§f].",
                    "",
                    "§aDescrição:",
                    "§a❙ §7Usado para crafts de anões.",
                    "",
                    "§aEconomia:",
                    "§a❙ §4Proibido a comercialização",
                    "",
                    "§a(!) Esse §dITEM §aé " + color + name.toLowerCase() + " §amais informações acesse nosso site!"
            );
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.AQUA + "Mesa Arcana")) {
            event.setCancelled(true); // Impede qualquer modificação no inventário
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            // Verifica a classe do jogador
            PlayerClassData playerClassData = getPlayerClass(player);

            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            Economy economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

            String rarity = null;
            ItemStack itemToGive = null;

            if (slot == 9) { // Runa Comum
                rarity = "common";

                ItemStack spectateItem = new ItemStack(Material.PAPER);
                ItemMeta spectateMeta = spectateItem.getItemMeta();

                if (spectateMeta != null) {
                    spectateMeta.setDisplayName("§7Runa Comum");
                    List<String> spectateLore = new ArrayList<>();
                    spectateLore.add("§7- §fInformação da §f[§dRuna§f].");
                    spectateLore.add("");
                    spectateLore.add("§aDescrição:");
                    spectateLore.add("§a❙ §7Usado para crafts de anões.");
                    spectateLore.add("");
                    spectateLore.add("§aEconomia:");
                    spectateLore.add("§a❙ §4Proibido a comercialização");
                    spectateLore.add("");
                    spectateLore.add("§a(!) Esse §dITEM §aé §7comum §amais informações acesse nosso site!");
                    spectateMeta.setLore(spectateLore);
                    spectateItem.setItemMeta(spectateMeta);

                    itemToGive = spectateItem;
                }  else {
                    getLogger().warning("Falha ao criar o ItemMeta para a Runa Comum.");
                }

            } else if (slot == 11) {
                rarity = "rare";

                ItemStack spectateItem2 = new ItemStack(Material.PAPER);
                ItemMeta spectateMeta2 = spectateItem2.getItemMeta();

                if (spectateMeta2 != null) {
                    spectateMeta2.setDisplayName("§6Runa Rara");
                    List<String> spectateLore2 = new ArrayList<>();
                    spectateLore2.add("§7- §fInformação da §f[§dRuna§f].");
                    spectateLore2.add("");
                    spectateLore2.add("§aDescrição:");
                    spectateLore2.add("§a❙ §7Usado para crafts de anões.");
                    spectateLore2.add("");
                    spectateLore2.add("§aEconomia:");
                    spectateLore2.add("§a❙ §4Proibido a comercialização");
                    spectateLore2.add("");
                    spectateLore2.add("§a(!) Esse §dITEM §aé §6rara §amais informações acesse nosso site!");
                    spectateMeta2.setLore(spectateLore2);
                    spectateItem2.setItemMeta(spectateMeta2);

                    itemToGive = spectateItem2;
                }  else {
                    getLogger().warning("Falha ao criar o ItemMeta para a Runa Rara.");
                }
            } else if (slot == 13) {
                rarity = "epic";

                ItemStack spectateItem3 = new ItemStack(Material.PAPER);
                ItemMeta spectateMeta3 = spectateItem3.getItemMeta();

                if (spectateMeta3 != null) {

                    spectateMeta3.setDisplayName("§5Runa Epica");
                    List<String> spectateLore3 = new ArrayList<>();
                    spectateLore3.add("§7- §fInformação da §f[§dRuna§f].");
                    spectateLore3.add("");
                    spectateLore3.add("§aDescrição:");
                    spectateLore3.add("§a❙ §7Usado para crafts de anões.");
                    spectateLore3.add("");
                    spectateLore3.add("§aEconomia:");
                    spectateLore3.add("§a❙ §4Proibido a comercialização");
                    spectateLore3.add("");
                    spectateLore3.add("§a(!) Esse §dITEM §aé §5epic §amais informações acesse nosso site!");
                    spectateMeta3.setLore(spectateLore3);
                    spectateItem3.setItemMeta(spectateMeta3);

                    itemToGive = spectateItem3;
                }  else {
                    getLogger().warning("Falha ao criar o ItemMeta para a Runa Epica.");
                }
            } else if (slot == 15) {
                rarity = "legendary";

                ItemStack spectateItem4 = new ItemStack(Material.PAPER);
                ItemMeta spectateMeta4 = spectateItem4.getItemMeta();
                if (spectateMeta4 != null) {

                    spectateMeta4.setDisplayName("§dRuna Lendaria");
                    List<String> spectateLore4 = new ArrayList<>();
                    spectateLore4.add("§7- §fInformação da §f[§dRuna§f].");
                    spectateLore4.add("");
                    spectateLore4.add("§aDescrição:");
                    spectateLore4.add("§a❙ §7Usado para crafts de anões.");
                    spectateLore4.add("");
                    spectateLore4.add("§aEconomia:");
                    spectateLore4.add("§a❙ §4Proibido a comercialização");
                    spectateLore4.add("");
                    spectateLore4.add("§a(!) Esse §dITEM §aé §dlendaria §amais informações acesse nosso site!");
                    spectateMeta4.setLore(spectateLore4);
                    spectateItem4.setItemMeta(spectateMeta4);

                    itemToGive = spectateItem4;
                }  else {
                    getLogger().warning("Falha ao criar o ItemMeta para a Runa Lendaria.");
                }
            } else if (slot == 17) {
                rarity = "divine";

                ItemStack spectateItem5 = new ItemStack(Material.PAPER);
                ItemMeta spectateMeta5 = spectateItem5.getItemMeta();
                    if (spectateMeta5 != null) {
                    spectateMeta5.setDisplayName("§dRuna Divina");
                    List<String> spectateLore5 = new ArrayList<>();
                    spectateLore5.add("§7- §fInformação da §f[§dRuna§f].");
                    spectateLore5.add("");
                    spectateLore5.add("§aDescrição:");
                    spectateLore5.add("§a❙ §7Usado para crafts de anões.");
                    spectateLore5.add("");
                    spectateLore5.add("§aEconomia:");
                    spectateLore5.add("§a❙ §4Proibido a comercialização");
                    spectateLore5.add("");
                    spectateLore5.add("§a(!) Esse §dITEM §aé §bdivina §amais informações acesse nosso site!");
                    spectateMeta5.setLore(spectateLore5);
                    spectateItem5.setItemMeta(spectateMeta5);

                    itemToGive = spectateItem5;
                } else {
                    getLogger().warning("Falha ao criar o ItemMeta para a Runa Divina.");
                }
            }

            if (rarity != null && !isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            int requiredStars = runeStarCost.getOrDefault(rarity, 0);

            if (hasEnoughStars(player, requiredStars)) {
                removeStars(player, requiredStars);
                if (itemToGive == null || itemToGive.getType() == Material.AIR) {
                    getLogger().warning("Ocorreu um erro ao criar a runa. Tente novamente.");
                    return;
                }

                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage(ChatColor.RED + "Seu inventário está cheio!");
                    return;
                }

                player.getInventory().addItem(itemToGive);
                player.sendMessage(ChatColor.GREEN + "Você recebeu uma " + itemToGive.getItemMeta().getDisplayName() + "!");

            } else {
                player.sendMessage(ChatColor.RED + "Você precisa de " + requiredStars + " Estrelas da Sorte para isso.");
            }
        }
    }

    private boolean hasEnoughStars(Player player, int requiredAmount) {
        int starCount = 0;
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta starMeta = star.getItemMeta();
        if (starMeta != null) {
            starMeta.setDisplayName("Estrela da Sorte");
            star.setItemMeta(starMeta);
        }
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String displayName = item.getItemMeta().getDisplayName();
                // Remove as cores e compara o texto puro
                if (ChatColor.stripColor(displayName).equals("Estrela da Sorte")) {
                    starCount += item.getAmount();
                }
            }
        }

        return starCount >= requiredAmount;
    }

    private void removeStars(Player player, int amount) {
        int removed = 0;
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta starMeta = star.getItemMeta();
        if (starMeta != null) {
            starMeta.setDisplayName("Estrela da Sorte");
            star.setItemMeta(starMeta);
        }
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String displayName = item.getItemMeta().getDisplayName();
                // Remove as cores e compara o texto puro
                if (ChatColor.stripColor(displayName).equals("Estrela da Sorte")) {
                    int currentAmount = item.getAmount();
                    if (currentAmount + removed <= amount) {
                        removed += currentAmount;
                        player.getInventory().setItem(i, null); // Remove o item
                    } else {
                        item.setAmount(currentAmount - (amount - removed));
                        player.getInventory().setItem(i, item);
                        break;
                    }
                }
            }
            if (removed >= amount) break;
        }
    }

    public boolean isClassAllowed(Player player, String rarity) {
        PlayerClassData playerClassData = getPlayerClass(player);

        if (playerClassData == null || rarity == null) {
            return false;
        }

        List<String> allowedRunes = classRunes.getOrDefault(playerClassData.getClassName(), Collections.emptyList());
        return allowedRunes.contains(rarity.toLowerCase());
    }

    private PlayerClassData getPlayerClass(Player player) {
        return plugin.getPlayerData(player.getUniqueId());
    }
}
