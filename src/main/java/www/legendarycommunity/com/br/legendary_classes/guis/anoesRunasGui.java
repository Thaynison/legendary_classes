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

public class anoesRunasGui implements CommandExecutor, Listener {

    private final Legendary_classes plugin;
    private final Map<String, List<String>> classRunes = new HashMap<>();
    private final Map<String, Object[]> runeStarCost = new HashMap<>();
    private final Map<UUID, Integer> playerPages = new HashMap<>();


    public anoesRunasGui(Legendary_classes plugin) {
        this.plugin = plugin;
        initializeClassRunes();
        initializeRuneStarCosts();
    }

    private void initializeClassRunes() {
        classRunes.put("Anao", List.of("common", "rare"));
        classRunes.put("AnaoMercador", List.of("common", "rare", "epic"));
        classRunes.put("AnaoNobre", List.of("common", "rare", "epic", "legendary"));
        classRunes.put("Lemiel", List.of("common", "rare", "epic", "legendary", "divine"));
        classRunes.put("AinzOoalGown", List.of("common", "rare", "epic", "legendary", "divine"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("anoesrunas")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [01]");

            AnoesMenu.setItem(0, createRuneForja("Cura", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(1, createRuneForja("Cura", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(2, createRuneForja("Cura", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(3, createRuneForja("Cura", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(4, createRuneForja("Cura", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(5, createRuneForja("Maldicao da Morte", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(6, createRuneForja("Maldicao da Morte", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(7, createRuneForja("Maldicao da Morte", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(8, createRuneForja("Maldicao da Morte", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(9, createRuneForja("Maldicao da Morte", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(10, createRuneForja("Maldicao da Fragilidade", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(11, createRuneForja("Maldicao da Fragilidade", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(12, createRuneForja("Maldicao da Fragilidade", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(13, createRuneForja("Maldicao da Fragilidade", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(14, createRuneForja("Maldicao da Fragilidade", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(15, createRuneForja("Maldicao da Mediocridade", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(16, createRuneForja("Maldicao da Mediocridade", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(17, createRuneForja("Maldicao da Mediocridade", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(18, createRuneForja("Maldicao da Mediocridade", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(19, createRuneForja("Maldicao da Mediocridade", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(15, createRuneForja("Maldicao da Infortunio", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(16, createRuneForja("Maldicao da Infortunio", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(17, createRuneForja("Maldicao da Infortunio", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(18, createRuneForja("Maldicao da Infortunio", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(19, createRuneForja("Maldicao da Infortunio", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(20, createRuneForja("Cacador de Xp", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(21, createRuneForja("Cacador de Xp", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(22, createRuneForja("Cacador de Xp", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(23, createRuneForja("Cacador de Xp", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(24, createRuneForja("Cacador de Xp", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(25, createRuneForja("Mestre do Rio", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(26, createRuneForja("Mestre do Rio", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(27, createRuneForja("Mestre do Rio", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(28, createRuneForja("Mestre do Rio", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(29, createRuneForja("Mestre do Rio", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(30, createRuneForja("Pescador Experiente", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(31, createRuneForja("Pescador Experiente", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(32, createRuneForja("Pescador Experiente", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(33, createRuneForja("Pescador Experiente", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(34, createRuneForja("Pescador Experiente", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(35, createRuneForja("Fundicao", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(36, createRuneForja("Fundicao", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(37, createRuneForja("Fundicao", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(38, createRuneForja("Fundicao", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(39, createRuneForja("Fundicao", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(40, createRuneForja("Rebote", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(41, createRuneForja("Rebote", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(42, createRuneForja("Rebote", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(43, createRuneForja("Rebote", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(44, createRuneForja("Rebote", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Maldicao da Infortunio", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(48, createRuneForja("Maldicao da Infortunio", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(49, createRuneForja("Maldicao da Infortunio", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(50, createRuneForja("Maldicao da Infortunio", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(51, createRuneForja("Maldicao da Infortunio", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas2")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas2 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [02]");

            AnoesMenu.setItem(0, createRuneForja("Sobrevivencialista", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(1, createRuneForja("Sobrevivencialista", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(2, createRuneForja("Sobrevivencialista", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(3, createRuneForja("Sobrevivencialista", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(4, createRuneForja("Sobrevivencialista", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(5, createRuneForja("Tunnel", "1", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(6, createRuneForja("Tunnel", "2", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(7, createRuneForja("Tunnel", "3", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(8, createRuneForja("Tunnel", "4", "Comum", ChatColor.GRAY));
            AnoesMenu.setItem(9, createRuneForja("Tunnel", "5", "Comum", ChatColor.GRAY));

            AnoesMenu.setItem(10, createRuneForja("Explosao de Blocos", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(11, createRuneForja("Explosao de Blocos", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(12, createRuneForja("Explosao de Blocos", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(13, createRuneForja("Explosao de Blocos", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(14, createRuneForja("Explosao de Blocos", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(15, createRuneForja("Cegueira", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(16, createRuneForja("Cegueira", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(17, createRuneForja("Cegueira", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(18, createRuneForja("Cegueira", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(19, createRuneForja("Cegueira", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(20, createRuneForja("Salto", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(21, createRuneForja("Salto", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(22, createRuneForja("Salto", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(23, createRuneForja("Salto", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(24, createRuneForja("Salto", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(25, createRuneForja("Flecha Confusa", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(26, createRuneForja("Flecha Confusa", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(27, createRuneForja("Flecha Confusa", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(28, createRuneForja("Flecha Confusa", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(29, createRuneForja("Flecha Confusa", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(30, createRuneForja("Confusao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(31, createRuneForja("Confusao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(32, createRuneForja("Confusao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(33, createRuneForja("Confusao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(34, createRuneForja("Confusao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(35, createRuneForja("Captura Dupla", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(36, createRuneForja("Captura Dupla", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(37, createRuneForja("Captura Dupla", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(38, createRuneForja("Captura Dupla", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(39, createRuneForja("Captura Dupla", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(40, createRuneForja("Maldicao da Afogado", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(41, createRuneForja("Maldicao da Afogado", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(42, createRuneForja("Maldicao da Afogado", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(43, createRuneForja("Maldicao da Afogado", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(44, createRuneForja("Maldicao da Afogado", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Capa da Escuridao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(48, createRuneForja("Capa da Escuridao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(49, createRuneForja("Capa da Escuridao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(50, createRuneForja("Capa da Escuridao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(51, createRuneForja("Capa da Escuridao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas3")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas3 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [03]");

            AnoesMenu.setItem(0, createRuneForja("Extensao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(1, createRuneForja("Extensao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(2, createRuneForja("Extensao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(3, createRuneForja("Extensao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(4, createRuneForja("Extensao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(5, createRuneForja("Pressa", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(6, createRuneForja("Pressa", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(7, createRuneForja("Pressa", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(8, createRuneForja("Pressa", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(9, createRuneForja("Pressa", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(10, createRuneForja("Paireacao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(11, createRuneForja("Paireacao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(12, createRuneForja("Paireacao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(13, createRuneForja("Paireacao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(14, createRuneForja("Paireacao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(15, createRuneForja("Persistencia", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(16, createRuneForja("Persistencia", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(17, createRuneForja("Persistencia", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(18, createRuneForja("Persistencia", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(19, createRuneForja("Persistencia", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(20, createRuneForja("Agilidade", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(21, createRuneForja("Agilidade", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(22, createRuneForja("Agilidade", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(23, createRuneForja("Agilidade", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(24, createRuneForja("Agilidade", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(25, createRuneForja("Flecha Envenenadas", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(26, createRuneForja("Flecha Envenenadas", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(27, createRuneForja("Flecha Envenenadas", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(28, createRuneForja("Flecha Envenenadas", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(29, createRuneForja("Flecha Envenenadas", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(30, createRuneForja("Restauracao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(31, createRuneForja("Restauracao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(32, createRuneForja("Restauracao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(33, createRuneForja("Restauracao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(34, createRuneForja("Restauracao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(35, createRuneForja("Catador", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(36, createRuneForja("Catador", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(37, createRuneForja("Catador", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(38, createRuneForja("Catador", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(39, createRuneForja("Catador", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(40, createRuneForja("Autodestruicao", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(41, createRuneForja("Autodestruicao", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(42, createRuneForja("Autodestruicao", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(43, createRuneForja("Autodestruicao", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(44, createRuneForja("Autodestruicao", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Bau Suave", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(48, createRuneForja("Bau Suave", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(49, createRuneForja("Bau Suave", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(50, createRuneForja("Bau Suave", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(51, createRuneForja("Bau Suave", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        }  else if (command.getName().equalsIgnoreCase("anoesrunas4")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas4 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [04]");

            AnoesMenu.setItem(0, createRuneForja("Franco Atirador", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(1, createRuneForja("Franco Atirador", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(2, createRuneForja("Franco Atirador", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(3, createRuneForja("Franco Atirador", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(4, createRuneForja("Franco Atirador", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(5, createRuneForja("Deslizar", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(6, createRuneForja("Deslizar", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(7, createRuneForja("Deslizar", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(8, createRuneForja("Deslizar", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(9, createRuneForja("Deslizar", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(10, createRuneForja("Telecinese", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(11, createRuneForja("Telecinese", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(12, createRuneForja("Telecinese", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(13, createRuneForja("Telecinese", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(14, createRuneForja("Telecinese", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(15, createRuneForja("Cacador de Tesouro", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(16, createRuneForja("Cacador de Tesouro", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(17, createRuneForja("Cacador de Tesouro", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(18, createRuneForja("Cacador de Tesouro", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(19, createRuneForja("Cacador de Tesouro", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(20, createRuneForja("Flecha Vampirica", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(21, createRuneForja("Flecha Vampirica", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(22, createRuneForja("Flecha Vampirica", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(23, createRuneForja("Flecha Vampirica", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(24, createRuneForja("Flecha Vampirica", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(25, createRuneForja("Veios", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(26, createRuneForja("Veios", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(27, createRuneForja("Veios", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(28, createRuneForja("Veios", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(29, createRuneForja("Veios", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(30, createRuneForja("Venom", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(31, createRuneForja("Venom", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(32, createRuneForja("Venom", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(33, createRuneForja("Venom", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(34, createRuneForja("Venom", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(35, createRuneForja("Defensor da Aldeia", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(36, createRuneForja("Defensor da Aldeia", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(37, createRuneForja("Defensor da Aldeia", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(38, createRuneForja("Defensor da Aldeia", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(39, createRuneForja("Defensor da Aldeia", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(40, createRuneForja("Flechas Murchas", "1", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(41, createRuneForja("Flechas Murchas", "2", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(42, createRuneForja("Flechas Murchas", "3", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(43, createRuneForja("Flechas Murchas", "4", "Raro", ChatColor.GOLD));
            AnoesMenu.setItem(44, createRuneForja("Flechas Murchas", "5", "Raro", ChatColor.GOLD));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Aquaman", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(48, createRuneForja("Aquaman", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(49, createRuneForja("Aquaman", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(50, createRuneForja("Aquaman", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(51, createRuneForja("Aquaman", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas5")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas5 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [05]");

            AnoesMenu.setItem(0, createRuneForja("Auto Pesca", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(1, createRuneForja("Auto Pesca", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(2, createRuneForja("Auto Pesca", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(3, createRuneForja("Auto Pesca", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(4, createRuneForja("Auto Pesca", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(5, createRuneForja("Explosao de TNT", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(6, createRuneForja("Explosao de TNT", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(7, createRuneForja("Explosao de TNT", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(8, createRuneForja("Explosao de TNT", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(9, createRuneForja("Explosao de TNT", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(10, createRuneForja("Aco Frio", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(11, createRuneForja("Aco Frio", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(12, createRuneForja("Aco Frio", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(13, createRuneForja("Aco Frio", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(14, createRuneForja("Aco Frio", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(15, createRuneForja("Flechas Escuras", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(16, createRuneForja("Flechas Escuras", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(17, createRuneForja("Flechas Escuras", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(18, createRuneForja("Flechas Escuras", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(19, createRuneForja("Flechas Escuras", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(20, createRuneForja("Flechas Eletrificadas", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(21, createRuneForja("Flechas Eletrificadas", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(22, createRuneForja("Flechas Eletrificadas", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(23, createRuneForja("Flechas Eletrificadas", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(24, createRuneForja("Flechas Eletrificadas", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(25, createRuneForja("Protecao Elementar", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(26, createRuneForja("Protecao Elementar", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(27, createRuneForja("Protecao Elementar", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(28, createRuneForja("Protecao Elementar", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(29, createRuneForja("Protecao Elementar", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(30, createRuneForja("Escape", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(31, createRuneForja("Escape", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(32, createRuneForja("Escape", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(33, createRuneForja("Escape", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(34, createRuneForja("Escape", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(35, createRuneForja("Flecha Explosiva", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(36, createRuneForja("Flecha Explosiva", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(37, createRuneForja("Flecha Explosiva", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(38, createRuneForja("Flecha Explosiva", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(39, createRuneForja("Flecha Explosiva", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(40, createRuneForja("Aspecto Gelo", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(41, createRuneForja("Aspecto Gelo", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(42, createRuneForja("Aspecto Gelo", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(43, createRuneForja("Aspecto Gelo", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(44, createRuneForja("Aspecto Gelo", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Infernal", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(48, createRuneForja("Infernal", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(49, createRuneForja("Infernal", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(50, createRuneForja("Infernal", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(51, createRuneForja("Infernal", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas6")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas6 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [06]");

            AnoesMenu.setItem(0, createRuneForja("Visao Noturna", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(1, createRuneForja("Visao Noturna", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(2, createRuneForja("Visao Noturna", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(3, createRuneForja("Visao Noturna", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(4, createRuneForja("Visao Noturna", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(5, createRuneForja("Sonica", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(6, createRuneForja("Sonica", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(7, createRuneForja("Sonica", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(8, createRuneForja("Sonica", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(9, createRuneForja("Sonica", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(10, createRuneForja("Forca Asyncrona", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(11, createRuneForja("Forca Asyncrona", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(12, createRuneForja("Forca Asyncrona", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(13, createRuneForja("Forca Asyncrona", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(14, createRuneForja("Forca Asyncrona", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(15, createRuneForja("Wither", "1", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(16, createRuneForja("Wither", "2", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(17, createRuneForja("Wither", "3", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(18, createRuneForja("Wither", "4", "Epica", ChatColor.LIGHT_PURPLE));
            AnoesMenu.setItem(19, createRuneForja("Wither", "5", "Epica", ChatColor.LIGHT_PURPLE));

            AnoesMenu.setItem(20, createRuneForja("Maldicao de Prole", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(21, createRuneForja("Maldicao de Prole", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(22, createRuneForja("Maldicao de Prole", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(23, createRuneForja("Maldicao de Prole", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(24, createRuneForja("Maldicao de Prole", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(25, createRuneForja("Cortadora", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(26, createRuneForja("Cortadora", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(27, createRuneForja("Cortadora", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(28, createRuneForja("Cortadora", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(29, createRuneForja("Cortadora", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(30, createRuneForja("Golpe Duplo", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(31, createRuneForja("Golpe Duplo", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(32, createRuneForja("Golpe Duplo", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(33, createRuneForja("Golpe Duplo", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(34, createRuneForja("Golpe Duplo", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(35, createRuneForja("Bafo de Dragao", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(36, createRuneForja("Bafo de Dragao", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(37, createRuneForja("Bafo de Dragao", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(38, createRuneForja("Bafo de Dragao", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(39, createRuneForja("Bafo de Dragao", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(40, createRuneForja("Arco do Ender", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(41, createRuneForja("Arco do Ender", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(42, createRuneForja("Arco do Ender", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(43, createRuneForja("Arco do Ender", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(44, createRuneForja("Arco do Ender", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Caminhante da Chama", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(48, createRuneForja("Caminhante da Chama", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(49, createRuneForja("Caminhante da Chama", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(50, createRuneForja("Caminhante da Chama", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(51, createRuneForja("Caminhante da Chama", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas7")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas7 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [07]");

            AnoesMenu.setItem(0, createRuneForja("Velocidade", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(1, createRuneForja("Velocidade", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(2, createRuneForja("Velocidade", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(3, createRuneForja("Velocidade", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(4, createRuneForja("Velocidade", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(5, createRuneForja("Escudo de Gelo", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(6, createRuneForja("Escudo de Gelo", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(7, createRuneForja("Escudo de Gelo", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(8, createRuneForja("Escudo de Gelo", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(9, createRuneForja("Escudo de Gelo", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(10, createRuneForja("Paralisar", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(11, createRuneForja("Paralisar", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(12, createRuneForja("Paralisar", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(13, createRuneForja("Paralisar", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(14, createRuneForja("Paralisar", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(15, createRuneForja("Raiva", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(16, createRuneForja("Raiva", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(17, createRuneForja("Raiva", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(18, createRuneForja("Raiva", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(19, createRuneForja("Raiva", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(20, createRuneForja("Recrescimento", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(21, createRuneForja("Recrescimento", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(22, createRuneForja("Recrescimento", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(23, createRuneForja("Recrescimento", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(24, createRuneForja("Recrescimento", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(25, createRuneForja("Foguete", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(26, createRuneForja("Foguete", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(27, createRuneForja("Foguete", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(28, createRuneForja("Foguete", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(29, createRuneForja("Foguete", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(30, createRuneForja("Surpresa", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(31, createRuneForja("Surpresa", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(32, createRuneForja("Surpresa", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(33, createRuneForja("Surpresa", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(34, createRuneForja("Surpresa", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(35, createRuneForja("Temperamento", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(36, createRuneForja("Temperamento", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(37, createRuneForja("Temperamento", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(38, createRuneForja("Temperamento", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(39, createRuneForja("Temperamento", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(40, createRuneForja("Trovao", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(41, createRuneForja("Trovao", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(42, createRuneForja("Trovao", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(43, createRuneForja("Trovao", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(44, createRuneForja("Trovao", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            AnoesMenu.setItem(47, createRuneForja("Vampiro", "1", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(48, createRuneForja("Vampiro", "2", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(49, createRuneForja("Vampiro", "3", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(50, createRuneForja("Vampiro", "4", "Lendaria", ChatColor.DARK_PURPLE));
            AnoesMenu.setItem(51, createRuneForja("Vampiro", "5", "Lendaria", ChatColor.DARK_PURPLE));

            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        } else if (command.getName().equalsIgnoreCase("anoesrunas8")) {

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Use correto: /anoesrunas8 <nome_do_jogador>");
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (targetPlayer == null || !targetPlayer.isOnline()) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado ou offline.");
                return true;
            }

            Inventory AnoesMenu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Forja [08]");

            AnoesMenu.setItem(0, createRuneForja("Decapitador", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(1, createRuneForja("Decapitador", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(2, createRuneForja("Decapitador", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(3, createRuneForja("Decapitador", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(4, createRuneForja("Decapitador", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(5, createRuneForja("Toque Divino", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(6, createRuneForja("Toque Divino", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(7, createRuneForja("Toque Divino", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(8, createRuneForja("Toque Divino", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(9, createRuneForja("Toque Divino", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(10, createRuneForja("Escudo de Fogo", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(11, createRuneForja("Escudo de Fogo", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(12, createRuneForja("Escudo de Fogo", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(13, createRuneForja("Escudo de Fogo", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(14, createRuneForja("Escudo de Fogo", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(15, createRuneForja("Endurecida", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(16, createRuneForja("Endurecida", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(17, createRuneForja("Endurecida", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(18, createRuneForja("Endurecida", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(19, createRuneForja("Endurecida", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(20, createRuneForja("Replantacao", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(21, createRuneForja("Replantacao", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(22, createRuneForja("Replantacao", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(23, createRuneForja("Replantacao", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(24, createRuneForja("Replantacao", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(25, createRuneForja("Saturacao", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(26, createRuneForja("Saturacao", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(27, createRuneForja("Saturacao", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(28, createRuneForja("Saturacao", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(29, createRuneForja("Saturacao", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(30, createRuneForja("Pacto de Alma", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(31, createRuneForja("Pacto de Alma", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(32, createRuneForja("Pacto de Alma", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(33, createRuneForja("Pacto de Alma", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(34, createRuneForja("Pacto de Alma", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(35, createRuneForja("Ovos", "1", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(36, createRuneForja("Ovos", "2", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(37, createRuneForja("Ovos", "3", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(38, createRuneForja("Ovos", "4", "Divina", ChatColor.AQUA));
            AnoesMenu.setItem(39, createRuneForja("Ovos", "5", "Divina", ChatColor.AQUA));

            AnoesMenu.setItem(40, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(41, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(42, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(43, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(44, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(45, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(46, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(47, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(48, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(49, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(50, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(51, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(52, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));
            AnoesMenu.setItem(53, new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1));

            targetPlayer.openInventory(AnoesMenu);
            getLogger().info("O menu foi aberto para " + targetPlayer.getName() + ".");
            return true;
        }
        return false;
    }

    private ItemStack createRuneForja(String name, String level, String rune, ChatColor color) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color + "Runa de " + name);
            List<String> lore = Arrays.asList(
                    "§7- §fInformação da §f[§dRuna§f].",
                    "",
                    "§aDescrição:",
                    "§a❙ §7Usado para runificar §ditens.",
                    "",
                    "§aEncantabilidade:",
                    "§a❙ §7" + level,
                    "",
                    "§a(!) Esse §dITEM §aé " + color + rune.toLowerCase() + " §amais informações acesse nosso site!"
            );
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [01]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Cura", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 1) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Cura", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 2) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Cura", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 3) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Cura", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 4) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Cura", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 5) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao da Morte", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 6) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao da Morte", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 7) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao da Morte", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 8) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao da Morte", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 9) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao da Morte", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 10) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao da Fragilidade", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 11) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao da Fragilidade", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 12) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao da Fragilidade", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 13) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao da Fragilidade", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 14) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao da Fragilidade", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 15) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao da Mediocridade", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 16) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao da Mediocridade", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 17) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao da Mediocridade", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 18) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao da Mediocridade", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 19) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao da Mediocridade", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 20) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Cacador de Xp", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 21) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Cacador de Xp", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 22) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Cacador de Xp", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 23) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Cacador de Xp", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 24) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Cacador de Xp", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 25) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Mestre do Rio", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 26) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Mestre do Rio", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 27) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Mestre do Rio", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 28) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Mestre do Rio", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 29) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Mestre do Rio", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 30) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Pescador Experiente", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 31) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Pescador Experiente", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 32) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Pescador Experiente", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 33) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Pescador Experiente", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 34) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Pescador Experiente", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 35) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Fundicao", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 36) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Fundicao", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 37) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Fundicao", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 38) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Fundicao", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 39) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Fundicao", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 40) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Rebote", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 41) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Rebote", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 42) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Rebote", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 43) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Rebote", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 44) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Rebote", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 47) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao da Infortunio", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 48) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao da Infortunio", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 49) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao da Infortunio", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 50) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao da Infortunio", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 51) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao da Infortunio", "5", "Comum", ChatColor.GRAY);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        } else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [02]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Sobrevivencialista", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 1) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Sobrevivencialista", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 2) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Sobrevivencialista", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 3) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Sobrevivencialista", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 4) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Sobrevivencialista", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 5) {
                rarity = "common";
                runeLevel = 1;
                itemToGive = createRuneForja("Tunnel", "1", "Comum", ChatColor.GRAY);
            } else if (slot == 6) {
                rarity = "common";
                runeLevel = 2;
                itemToGive = createRuneForja("Tunnel", "2", "Comum", ChatColor.GRAY);
            } else if (slot == 7) {
                rarity = "common";
                runeLevel = 4;
                itemToGive = createRuneForja("Tunnel", "3", "Comum", ChatColor.GRAY);
            } else if (slot == 8) {
                rarity = "common";
                runeLevel = 6;
                itemToGive = createRuneForja("Tunnel", "4", "Comum", ChatColor.GRAY);
            } else if (slot == 9) {
                rarity = "common";
                runeLevel = 8;
                itemToGive = createRuneForja("Tunnel", "5", "Comum", ChatColor.GRAY);
            } else if (slot == 10) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Explosao de Blocos", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 11) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Explosao de Blocos", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 12) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Explosao de Blocos", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 13) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Explosao de Blocos", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 14) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Explosao de Blocos", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 15) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Cegueira", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 16) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Cegueira", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 17) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Cegueira", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 18) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Cegueira", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 19) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Cegueira", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 20) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Salto", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 21) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Salto", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 22) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Salto", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 23) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Salto", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 24) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Salto", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 25) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Flecha Confusa", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 26) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Flecha Confusa", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 27) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Flecha Confusa", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 28) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Flecha Confusa", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 29) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Flecha Confusa", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 30) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Confusao", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 31) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Confusao", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 32) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Confusao", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 33) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Confusao", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 34) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Confusao", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 35) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Captura Dupla", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 36) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Captura Dupla", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 37) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Captura Dupla", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 38) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Captura Dupla", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 39) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Captura Dupla", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 40) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao da Afogado", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 41) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao da Afogado", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 42) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao da Afogado", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 43) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao da Afogado", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 44) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao da Afogado", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 47) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Capa da Escuridao", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 48) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Capa da Escuridao", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 49) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Capa da Escuridao", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 50) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Capa da Escuridao", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 51) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Capa da Escuridao", "5", "Rara", ChatColor.GOLD);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        }  else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [03]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Extensao", "1", "Raro", ChatColor.GOLD);
            } else if (slot == 1) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Extensao", "2", "Raro", ChatColor.GOLD);
            } else if (slot == 2) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Extensao", "3", "Raro", ChatColor.GOLD);
            } else if (slot == 3) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Extensao", "4", "Raro", ChatColor.GOLD);
            } else if (slot == 4) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Extensao", "5", "Raro", ChatColor.GOLD);
            } else if (slot == 5) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Pressa", "1", "Raro", ChatColor.GOLD);
            } else if (slot == 6) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Pressa", "2", "Raro", ChatColor.GOLD);
            } else if (slot == 7) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Pressa", "3", "Raro", ChatColor.GOLD);
            } else if (slot == 8) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Pressa", "4", "Raro", ChatColor.GOLD);
            } else if (slot == 9) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Pressa", "5", "Raro", ChatColor.GOLD);
            } else if (slot == 10) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Paireacao", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 11) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Paireacao", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 12) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Paireacao", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 13) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Paireacao", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 14) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Paireacao", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 15) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Persistencia", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 16) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Persistencia", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 17) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Persistencia", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 18) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Persistencia", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 19) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Persistencia", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 20) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Agilidade", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 21) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Agilidade", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 22) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Agilidade", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 23) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Agilidade", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 24) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Agilidade", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 25) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Flecha Envenenadas", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 26) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Flecha Envenenadas", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 27) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Flecha Envenenadas", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 28) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Flecha Envenenadas", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 29) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Flecha Envenenadas", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 30) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Restauracao", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 31) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Restauracao", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 32) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Restauracao", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 33) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Restauracao", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 34) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Restauracao", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 35) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Catador", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 36) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Catador", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 37) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Catador", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 38) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Catador", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 39) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Catador", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 40) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Autodestruicao", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 41) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Autodestruicao", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 42) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Autodestruicao", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 43) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Autodestruicao", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 44) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Autodestruicao", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 47) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Bau Suave", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 48) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Bau Suave", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 49) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Bau Suave", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 50) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Bau Suave", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 51) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Bau Suave", "5", "Rara", ChatColor.GOLD);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        }  else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [04]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Franco Atirador", "1", "Raro", ChatColor.GOLD);
            } else if (slot == 1) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Franco Atirador", "2", "Raro", ChatColor.GOLD);
            } else if (slot == 2) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Franco Atirador", "3", "Raro", ChatColor.GOLD);
            } else if (slot == 3) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Franco Atirador", "4", "Raro", ChatColor.GOLD);
            } else if (slot == 4) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Franco Atirador", "5", "Raro", ChatColor.GOLD);
            } else if (slot == 5) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Deslizar", "1", "Raro", ChatColor.GOLD);
            } else if (slot == 6) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Deslizar", "2", "Raro", ChatColor.GOLD);
            } else if (slot == 7) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Deslizar", "3", "Raro", ChatColor.GOLD);
            } else if (slot == 8) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Deslizar", "4", "Raro", ChatColor.GOLD);
            } else if (slot == 9) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Deslizar", "5", "Raro", ChatColor.GOLD);
            } else if (slot == 10) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Telecinese", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 11) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Telecinese", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 12) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Telecinese", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 13) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Telecinese", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 14) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Telecinese", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 15) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Cacador de Tesouro", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 16) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Cacador de Tesouro", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 17) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Cacador de Tesouro", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 18) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Cacador de Tesouro", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 19) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Cacador de Tesouro", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 20) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Flecha Vampirica", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 21) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Flecha Vampirica", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 22) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Flecha Vampirica", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 23) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Flecha Vampirica", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 24) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Flecha Vampirica", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 25) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Veios", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 26) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Veios", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 27) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Veios", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 28) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Veios", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 29) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Veios", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 30) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Venom", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 31) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Venom", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 32) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Venom", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 33) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Venom", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 34) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Venom", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 35) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Defensor da Aldeia", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 36) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Defensor da Aldeia", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 37) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Defensor da Aldeia", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 38) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Defensor da Aldeia", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 39) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Defensor da Aldeia", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 40) {
                rarity = "rare";
                runeLevel = 1;
                itemToGive = createRuneForja("Flechas Murchas", "1", "Rara", ChatColor.GOLD);
            } else if (slot == 41) {
                rarity = "rare";
                runeLevel = 2;
                itemToGive = createRuneForja("Flechas Murchas", "2", "Rara", ChatColor.GOLD);
            } else if (slot == 42) {
                rarity = "rare";
                runeLevel = 4;
                itemToGive = createRuneForja("Flechas Murchas", "3", "Rara", ChatColor.GOLD);
            } else if (slot == 43) {
                rarity = "rare";
                runeLevel = 6;
                itemToGive = createRuneForja("Flechas Murchas", "4", "Rara", ChatColor.GOLD);
            } else if (slot == 44) {
                rarity = "rare";
                runeLevel = 8;
                itemToGive = createRuneForja("Flechas Murchas", "5", "Rara", ChatColor.GOLD);
            } else if (slot == 47) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Aquaman", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 48) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Aquaman", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 49) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Aquaman", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 50) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Aquaman", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 51) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Aquaman", "5", "Epica", ChatColor.LIGHT_PURPLE);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        }   else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [05]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Auto Pesca", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 1) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Auto Pesca", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 2) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Auto Pesca", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 3) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Auto Pesca", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 4) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Auto Pesca", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 5) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Explosao de TNT", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 6) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Explosao de TNT", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 7) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Explosao de TNT", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 8) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Explosao de TNT", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 9) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Explosao de TNT", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 10) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Aco Frio", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 11) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Aco Frio", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 12) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Aco Frio", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 13) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Aco Frio", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 14) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Aco Frio", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 15) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Flechas Escuras", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 16) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Flechas Escuras", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 17) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Flechas Escuras", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 18) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Flechas Escuras", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 19) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Flechas Escuras", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 20) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Flechas Eletrificadas", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 21) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Flechas Eletrificadas", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 22) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Flechas Eletrificadas", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 23) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Flechas Eletrificadas", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 24) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Flechas Eletrificadas", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 25) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Protecao Elementar", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 26) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Protecao Elementar", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 27) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Protecao Elementar", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 28) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Protecao Elementar", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 29) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Protecao Elementar", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 30) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Escape", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 31) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Escape", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 32) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Escape", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 33) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Escape", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 34) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Escape", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 35) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Flecha Explosiva", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 36) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Flecha Explosiva", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 37) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Flecha Explosiva", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 38) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Flecha Explosiva", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 39) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Flecha Explosiva", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 40) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Aspecto Gelo", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 41) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Aspecto Gelo", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 42) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Aspecto Gelo", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 43) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Aspecto Gelo", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 44) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Aspecto Gelo", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 47) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Infernal", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 48) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Infernal", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 49) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Infernal", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 50) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Infernal", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 51) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Infernal", "5", "Epica", ChatColor.LIGHT_PURPLE);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        } else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [06]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Visao Noturna", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 1) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Visao Noturna", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 2) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Visao Noturna", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 3) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Visao Noturna", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 4) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Visao Noturna", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 5) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Sonica", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 6) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Sonica", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 7) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Sonica", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 8) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Sonica", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 9) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Sonica", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 10) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Forca Asyncrona", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 11) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Forca Asyncrona", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 12) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Forca Asyncrona", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 13) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Forca Asyncrona", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 14) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Forca Asyncrona", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 15) {
                rarity = "epic";
                runeLevel = 1;
                itemToGive = createRuneForja("Wither", "1", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 16) {
                rarity = "epic";
                runeLevel = 2;
                itemToGive = createRuneForja("Wither", "2", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 17) {
                rarity = "epic";
                runeLevel = 4;
                itemToGive = createRuneForja("Wither", "3", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 18) {
                rarity = "epic";
                runeLevel = 6;
                itemToGive = createRuneForja("Wither", "4", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 19) {
                rarity = "epic";
                runeLevel = 8;
                itemToGive = createRuneForja("Wither", "5", "Epica", ChatColor.LIGHT_PURPLE);
            } else if (slot == 20) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Maldicao de Prole", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 21) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Maldicao de Prole", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 22) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Maldicao de Prole", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 23) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Maldicao de Prole", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 24) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Maldicao de Prole", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 25) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Cortadora", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 26) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Cortadora", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 27) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Cortadora", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 28) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Cortadora", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 29) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Cortadora", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 30) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Golpe Duplo", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 31) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Golpe Duplo", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 32) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Golpe Duplo", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 33) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Golpe Duplo", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 34) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Golpe Duplo", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 35) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Bafo de Dragao", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 36) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Bafo de Dragao", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 37) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Bafo de Dragao", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 38) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Bafo de Dragao", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 39) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Bafo de Dragao", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 40) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Arco do Ender", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 41) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Arco do Ender", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 42) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Arco do Ender", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 43) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Arco do Ender", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 44) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Arco do Ender", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 47) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Caminhante da Chama", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 48) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Caminhante da Chama", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 49) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Caminhante da Chama", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 50) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Caminhante da Chama", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 51) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Caminhante da Chama", "5", "Lendaria", ChatColor.DARK_PURPLE);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        } else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [07]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Velocidade", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 1) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Velocidade", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 2) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Velocidade", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 3) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Velocidade", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 4) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Velocidade", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 5) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Escudo de Gelo", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 6) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Escudo de Gelo", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 7) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Escudo de Gelo", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 8) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Escudo de Gelo", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 9) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Escudo de Gelo", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 10) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Paralisar", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 11) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Paralisar", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 12) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Paralisar", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 13) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Paralisar", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 14) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Paralisar", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 15) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Raiva", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 16) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Raiva", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 17) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Raiva", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 18) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Raiva", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 19) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Raiva", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 20) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Recrescimento", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 21) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Recrescimento", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 22) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Recrescimento", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 23) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Recrescimento", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 24) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Recrescimento", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 25) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Foguete", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 26) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Foguete", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 27) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Foguete", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 28) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Foguete", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 29) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Foguete", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 30) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Surpresa", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 31) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Surpresa", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 32) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Surpresa", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 33) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Surpresa", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 34) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Surpresa", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 35) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Temperamento", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 36) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Temperamento", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 37) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Temperamento", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 38) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Temperamento", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 39) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Temperamento", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 40) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Trovao", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 41) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Trovao", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 42) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Trovao", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 43) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Trovao", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 44) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Trovao", "5", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 47) {
                rarity = "legendary";
                runeLevel = 1;
                itemToGive = createRuneForja("Vampiro", "1", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 48) {
                rarity = "legendary";
                runeLevel = 2;
                itemToGive = createRuneForja("Vampiro", "2", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 49) {
                rarity = "legendary";
                runeLevel = 4;
                itemToGive = createRuneForja("Vampiro", "3", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 50) {
                rarity = "legendary";
                runeLevel = 6;
                itemToGive = createRuneForja("Vampiro", "4", "Lendaria", ChatColor.DARK_PURPLE);
            } else if (slot == 51) {
                rarity = "legendary";
                runeLevel = 8;
                itemToGive = createRuneForja("Vampiro", "5", "Lendaria", ChatColor.DARK_PURPLE);
            }


            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        } else if (event.getView().getTitle().equals(ChatColor.GOLD + "Forja [08]")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            PlayerClassData playerClassData = getPlayerClass(player);
            if (playerClassData == null) {
                player.sendMessage(ChatColor.RED + "Você não tem uma classe definida.");
                return;
            }

            String rarity = null;
            ItemStack itemToGive = null;
            int runeLevel = 0;

            if (slot == 0) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Decapitador", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 1) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Decapitador", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 2) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Decapitador", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 3) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Decapitador", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 4) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Decapitador", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 5) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Toque Divino", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 6) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Toque Divino", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 7) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Toque Divino", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 8) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Toque Divino", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 9) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Toque Divino", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 10) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Escudo de Fogo", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 11) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Escudo de Fogo", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 12) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Escudo de Fogo", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 13) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Escudo de Fogo", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 14) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Escudo de Fogo", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 15) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Endurecida", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 16) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Endurecida", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 17) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Endurecida", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 18) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Endurecida", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 19) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Endurecida", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 20) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Replantacao", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 21) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Replantacao", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 22) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Replantacao", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 23) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Replantacao", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 24) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Replantacao", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 25) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Saturacao", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 26) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Saturacao", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 27) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Saturacao", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 28) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Saturacao", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 29) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Saturacao", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 30) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Pacto de Alma", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 31) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Pacto de Alma", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 32) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Pacto de Alma", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 33) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Pacto de Alma", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 34) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Pacto de Alma", "5", "Divina", ChatColor.AQUA);
            } else if (slot == 35) {
                rarity = "divine";
                runeLevel = 1;
                itemToGive = createRuneForja("Ovos", "1", "Divina", ChatColor.AQUA);
            } else if (slot == 36) {
                rarity = "divine";
                runeLevel = 2;
                itemToGive = createRuneForja("Ovos", "2", "Divina", ChatColor.AQUA);
            } else if (slot == 37) {
                rarity = "divine";
                runeLevel = 4;
                itemToGive = createRuneForja("Ovos", "3", "Divina", ChatColor.AQUA);
            } else if (slot == 38) {
                rarity = "divine";
                runeLevel = 6;
                itemToGive = createRuneForja("Ovos", "4", "Divina", ChatColor.AQUA);
            } else if (slot == 39) {
                rarity = "divine";
                runeLevel = 8;
                itemToGive = createRuneForja("Ovos", "5", "Divina", ChatColor.AQUA);
            }

            if (rarity == null) return;

            if (!isClassAllowed(player, rarity)) {
                player.sendMessage(ChatColor.RED + "Sua classe não tem permissão para esse item.");
                return;
            }

            if (!runeStarCost.containsKey(rarity)) {
                player.sendMessage(ChatColor.RED + "Erro: raridade não encontrada nos custos.");
                return;
            }

            Object[] costData = runeStarCost.get(rarity);
            String runeName = (String) costData[0];
            int baseRuneAmount = (int) costData[1];
            int baseFragmentoAmount = (int) costData[2];


            int requiredRuneAmount = baseRuneAmount * runeLevel;
            int requiredFragmentoAmount = baseFragmentoAmount * runeLevel;

            if (!hasEnoughItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount)) {
                player.sendMessage(ChatColor.RED + "Você não tem os itens necessários para essa forja.");
                return;
            }

            removeItems(player, runeName, requiredRuneAmount, requiredFragmentoAmount);
            player.getInventory().addItem(itemToGive);
            player.sendMessage(ChatColor.GREEN + "Você forjou com sucesso a sua runa!");
        }
    }
    
    private boolean hasEnoughItems(Player player, String runeName, int requiredRuneAmount, int requiredFragmentoAmount) {
        int runeCount = 0;
        int fragmentoCount = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName()); // Remove as cores

                if (itemName.equalsIgnoreCase("Runa " + runeName)) {
                    runeCount += item.getAmount();
                } else if (itemName.equalsIgnoreCase("Fragmento de Nidavellir")) {
                    fragmentoCount += item.getAmount();
                }
            }
        }

        return runeCount >= requiredRuneAmount && fragmentoCount >= requiredFragmentoAmount;
    }

    private void removeItems(Player player, String runeName, int amountRune, int amountFragmento) {
        int runasParaRemover = amountRune;
        int fragmentosParaRemover = amountFragmento;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName()); // Remove cores para comparação

                if (itemName.equalsIgnoreCase("Runa " + runeName) && runasParaRemover > 0) {
                    int toRemove = Math.min(item.getAmount(), runasParaRemover);
                    item.setAmount(item.getAmount() - toRemove);
                    runasParaRemover -= toRemove;
                } else if (itemName.equalsIgnoreCase("Fragmento de Nidavellir") && fragmentosParaRemover > 0) {
                    int toRemove = Math.min(item.getAmount(), fragmentosParaRemover);
                    item.setAmount(item.getAmount() - toRemove);
                    fragmentosParaRemover -= toRemove;
                }

                if (runasParaRemover <= 0 && fragmentosParaRemover <= 0) {
                    break;
                }
            }
        }
    }

    private void initializeRuneStarCosts() {
        runeStarCost.put("common", new Object[]{"Comum", 5, 4});
        runeStarCost.put("rare", new Object[]{"Rara", 10, 8});
        runeStarCost.put("epic", new Object[]{"Epica", 15, 16});
        runeStarCost.put("legendary", new Object[]{"Lendaria", 20, 32});
        runeStarCost.put("divine", new Object[]{"Divina", 25, 64});
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
