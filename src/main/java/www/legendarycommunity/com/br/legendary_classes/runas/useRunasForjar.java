package www.legendarycommunity.com.br.legendary_classes.runas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import www.legendarycommunity.com.br.legendary_classes.Legendary_classes;
import www.legendarycommunity.com.br.legendary_classes.PlayerClassData;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class useRunasForjar implements Listener {

    private final Legendary_classes plugin;
    private final Map<String, List<String>> classRunes = new HashMap<>();

    public useRunasForjar(Legendary_classes plugin) {
        this.plugin = plugin;
        initializeClassRunes();
    }

    private void initializeClassRunes() {
        classRunes.put("Anao", List.of("common", "rare"));
        classRunes.put("AnaoMercador", List.of("common", "rare", "epic"));
        classRunes.put("AnaoNobre", List.of("common", "rare", "epic", "legendary"));
        classRunes.put("Lemiel", List.of("common", "rare", "epic", "legendary", "divine"));
        classRunes.put("AinzOoalGown", List.of("common", "rare", "epic", "legendary", "divine"));
    }

    @EventHandler
    public void onPlayerUseRunasForjar(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName() || !meta.hasLore()) return;

        FileConfiguration config = plugin.getRunasConfig();

        for (String key : Objects.requireNonNull(config.getConfigurationSection("Runas")).getKeys(false)) {
            String configuredItem = config.getString("Runas." + key + ".item");
            String configuredName = config.getString("Runas." + key + ".nameDisplay");
            String rarity = config.getString("Runas." + key + ".rarity");

            if (!item.getType().toString().equalsIgnoreCase(configuredItem)) continue;

            String displayName = ChatColor.stripColor(meta.getDisplayName());
            String configName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', configuredName));

            if (displayName == null || configName == null || !displayName.equalsIgnoreCase(configName)) continue;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Você não tem permissão para usar essa runa!");
                return;
            }

            int loreValue = extractLoreValue(meta.getLore());
            if (loreValue == -1) return;

            if (event.getAction().toString().contains("RIGHT")) {
                String command = config.getString("Runas." + key + ".rightClick");
                if (command != null) {
                    executeCommand(player, command, loreValue);
                }
            } else if (event.getAction().toString().contains("LEFT")) {
                String command = config.getString("Runas." + key + ".leftClick");
                if (command != null) {
                    executeCommand(player, command, loreValue);
                }
            }

            player.getInventory().remove(item);
            break;
        }
    }

    private int extractLoreValue(List<String> lore) {
        if (lore == null) return -1;
        Pattern pattern = Pattern.compile(".*❙\\s*([0-9]+).*");

        for (String line : lore) {
            String strippedLine = ChatColor.stripColor(line);
            Matcher matcher = pattern.matcher(strippedLine);
            if (matcher.matches()) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        return -1;
    }

    private void executeCommand(Player player, String command, int loreValue) {
        command = command.replace("%player%", player.getName())
                .replace("%lore_value%", String.valueOf(loreValue));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
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
