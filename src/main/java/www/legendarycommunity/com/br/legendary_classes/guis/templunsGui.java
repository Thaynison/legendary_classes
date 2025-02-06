package www.legendarycommunity.com.br.legendary_classes.guis;

import org.bukkit.*;
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

import java.util.ArrayList;
import java.util.List;

public class templunsGui implements CommandExecutor, Listener {

    private final Legendary_classes plugin;

    public templunsGui(Legendary_classes plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("temploarcano")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Verificando se o jogador tem permissão para acessar
                if (!isClassAllowed(player)) {
                    player.sendMessage(ChatColor.RED + "Você não tem permissão para acessar esta templo.");
                    return true;
                }

                Inventory NidavellirGui = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Templo Arcano");

                // Preenchendo com vidro colorido
                for (int i = 0; i < 27; i++) {
                    if (i != 13) {
                        NidavellirGui.setItem(i, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1));
                    }
                }

                // Criando o item de teleporte
                ItemStack deitarBlock = new ItemStack(Material.PAPER);
                ItemMeta deitarMeta = deitarBlock.getItemMeta();
                deitarMeta.setDisplayName(ChatColor.AQUA + "Teleport Templo Arcano");
                List<String> deitarLore = new ArrayList<>();
                deitarLore.add("§7- §fInformação do §f[§bTemplo§f].");
                deitarLore.add("");
                deitarLore.add("§aDescrição:");
                deitarLore.add("§a❙ §7Esquerdo (Botão de bater) - §bTeleportar§7.");
                deitarLore.add("");
                deitarLore.add("§a(!) Esse §dDimensão §aé §bdivina §amais informações acesse nosso site!");
                deitarMeta.setLore(deitarLore);
                deitarBlock.setItemMeta(deitarMeta);
                NidavellirGui.setItem(13, deitarBlock);

                player.openInventory(NidavellirGui);
            } else {
                sender.sendMessage(ChatColor.RED + "Apenas jogadores podem executar este comando.");
            }
            return true;
        }
        return false;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Corrigindo o nome do inventário
        if (ChatColor.stripColor(event.getView().getTitle()).equals("Templo Arcano")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (slot == 13) {
                // player.sendMessage(ChatColor.YELLOW + "Clicou no item de teleporte.");
                World hardcoreWorld = Bukkit.getWorld("world");

                if (hardcoreWorld == null) {
                    player.sendMessage(ChatColor.RED + "O mundo hardcore não está carregado.");
                    return;
                }

                // player.sendMessage(ChatColor.GREEN + "Mundo carregado, tentando teleportar...");
                Location targetLocation = new Location(hardcoreWorld, -17309.5, 165, -235626.5);
                player.teleport(targetLocation);
                player.sendMessage(ChatColor.GREEN + "Teleportado com sucesso!");
            }

        }
    }

    public boolean isClassAllowed(Player player) {
        PlayerClassData playerClassData = getPlayerClass(player); // Obtém as informações da classe do jogador

        if (playerClassData == null) {
            return false; // Caso o jogador não tenha dados de classe
        }

        // Lista de classes permitidas
        List<String> allowedClasses = List.of("Druida", "Driade", "TuathaDeDanann", "Overlord", "Mago", "MagoRegente", "ReiMago", "AinzOoalGown", "Lemiel");

        // Verifica se a classe do jogador está na lista de classes permitidas
        return allowedClasses.contains(playerClassData.getClassName());
    }

    private PlayerClassData getPlayerClass(Player player) {
        return plugin.getPlayerData(player.getUniqueId());
    }
}
