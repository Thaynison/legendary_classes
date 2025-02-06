package www.legendarycommunity.com.br.legendary_classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

import www.legendarycommunity.com.br.legendary_classes.classes.*;
import www.legendarycommunity.com.br.legendary_classes.guis.anoesRunasGui;
import www.legendarycommunity.com.br.legendary_classes.guis.magosRunasGui;
import www.legendarycommunity.com.br.legendary_classes.guis.nidavellirGui;
import www.legendarycommunity.com.br.legendary_classes.guis.templunsGui;
import www.legendarycommunity.com.br.legendary_classes.runas.useRunasForjar;
import www.legendarycommunity.com.br.legendary_classes.sistemas.upleve;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import static net.sacredlabyrinth.phaed.simpleclans.chat.ChatHandler.plugin;

public final class Legendary_classes extends JavaPlugin implements Listener {

    private static Legendary_classes instance; // Instância estática
    private Connection connection;
    private Economy economy;
    private final HashMap<UUID, PlayerClassData> playerDataCache = new HashMap<>();
    private File RunasFile;
    private FileConfiguration RunasConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        connectDatabase();
        saveDefaultRunasConfig();

        // Setup Vault economy
        if (!setupEconomy()) {
            getLogger().severe("Vault or compatible economy plugin not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerEvents();

        getLogger().info("Legendary Classes Plugin iniciado!");
    }

    private void registerEvents() {

        // SISTEMA DE CLASSES
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Humano(this), this);
        getServer().getPluginManager().registerEvents(new HumanoMercador(this), this);
        getServer().getPluginManager().registerEvents(new HumanoNobre(this), this);
        getServer().getPluginManager().registerEvents(new Lamia(this), this);
        getServer().getPluginManager().registerEvents(new Nosferatu(this), this);
        getServer().getPluginManager().registerEvents(new VampiroRei(this), this);
        getServer().getPluginManager().registerEvents(new Demonio(this), this);
        getServer().getPluginManager().registerEvents(new Albion(this), this);
        getServer().getPluginManager().registerEvents(new PrincipeInfernal(this), this);
        getServer().getPluginManager().registerEvents(new Necromancer(this), this);
        getServer().getPluginManager().registerEvents(new MortoVivo(this), this);
        getServer().getPluginManager().registerEvents(new Overlord(this), this);
        getServer().getPluginManager().registerEvents(new CavaleiroDaFome(this), this);
        getServer().getPluginManager().registerEvents(new CavaleiroDaPeste(this), this);
        getServer().getPluginManager().registerEvents(new CavaleiroDaGuerra(this), this);
        getServer().getPluginManager().registerEvents(new CavaleiroDaMorte(this), this);
        getServer().getPluginManager().registerEvents(new Anao(this), this);
        getServer().getPluginManager().registerEvents(new AnaoMercador(this), this);
        getServer().getPluginManager().registerEvents(new AnaoNobre(this), this);
        getServer().getPluginManager().registerEvents(new Druida(this), this);
        getServer().getPluginManager().registerEvents(new Driade(this), this);
        getServer().getPluginManager().registerEvents(new TuathaDeDanann(this), this);
        getServer().getPluginManager().registerEvents(new Poseidon(this), this);
        getServer().getPluginManager().registerEvents(new Ares(this), this);
        getServer().getPluginManager().registerEvents(new AinzOoalGown(this), this);
        getServer().getPluginManager().registerEvents(new Albedo(this), this);
        getServer().getPluginManager().registerEvents(new Demiurgo(this), this);
        getServer().getPluginManager().registerEvents(new ReiDemonio(this), this);
        getServer().getPluginManager().registerEvents(new SupremaDivindade(this), this);
        getServer().getPluginManager().registerEvents(new CaosDivindade(this), this);
        getServer().getPluginManager().registerEvents(new Lemiel(this), this);
        getServer().getPluginManager().registerEvents(new Mereoleona(this), this);
        getServer().getPluginManager().registerEvents(new DorothyUnsworth(this), this);


        // SISTEMA DE RUNAS
        getServer().getPluginManager().registerEvents(new useRunasForjar(this), this);


        // SISTEMA DE COMANDOS
        this.getCommand("nidavellir").setExecutor(new nidavellirGui(this));
        getServer().getPluginManager().registerEvents(new nidavellirGui(this), this);

        this.getCommand("temploarcano").setExecutor(new templunsGui(this));
        getServer().getPluginManager().registerEvents(new templunsGui(this), this);

        this.getCommand("magosrunas").setExecutor(new magosRunasGui(this));
        getServer().getPluginManager().registerEvents(new magosRunasGui(this), this);

        this.getCommand("anoesrunas").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas2").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas3").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas4").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas5").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas6").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas7").setExecutor(new anoesRunasGui(this));
        this.getCommand("anoesrunas8").setExecutor(new anoesRunasGui(this));
        getServer().getPluginManager().registerEvents(new anoesRunasGui(this), this);

    }

    public static Legendary_classes getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        instance = null;
        disconnectDatabase();
        getLogger().info("Legendary Classes Plugin desligado!");
    }

    private void connectDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                String host = getConfig().getString("database.host");
                String database = getConfig().getString("database.name");
                String user = getConfig().getString("database.user");
                String password = getConfig().getString("database.password");
                int port = getConfig().getInt("database.port");

                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void disconnectDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            getLogger().severe("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("classe")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openClassMenu(player);
            } else {
                sender.sendMessage("Este comando só pode ser usado por jogadores!");
            }
            return true;
        }
        return false;
    }

    private void openClassMenu(Player player) {
        // Criação do inventário no thread principal, pois o Bukkit exige isso
        Inventory classMenu = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Painel de Classes");

        // Executa a operação de banco de dados em uma thread assíncrona
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String uuid = player.getUniqueId().toString();
            Map<String, Integer> playerLevels = new HashMap<>();

            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT class_name, class_level FROM legendary_player_classes WHERE uuid = ?");
                statement.setString(1, uuid);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String className = resultSet.getString("class_name");
                    int classLevel = resultSet.getInt("class_level");
                    playerLevels.put(className, classLevel);
                }

                // Criação de itens no inventário deve ser feita no thread principal
                Bukkit.getScheduler().runTask(plugin, () -> {
                    int maxLevel = 100;

                    classMenu.setItem(0, createMenuItem(
                            Material.PAPER,
                            "Humano",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Classe básica para iniciantes."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Humano", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar armaduras de &eOuro&c, &bDiamante &ce &8Netherite &c e usa-las."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar ferramentas de &eOuro&c, &bDiamante &ce &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão crafita &dShulker Box"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Humano"))
                    ));
                    classMenu.setItem(1, createMenuItem(
                            Material.PAPER,
                            "HumanoMercador",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Especialista em comércio."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("HumanoMercador", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Negociador"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7 Recebe desconto na trocar com villagers de vilas."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar armaduras de &bDiamante &ce &8Netherite &c e usa-las."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar ferramentas de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "HumanoMercador"))
                    ));
                    classMenu.setItem(2, createMenuItem(
                            Material.PAPER,
                            "HumanoNobre",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Classe da realeza."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("HumanoNobre", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Evolução"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7 Uma classe que busca uma forma de evoluir..."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar armaduras de &bDiamante &ce &8Netherite &c e usa-las."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar ferramentas de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "HumanoNobre"))
                    ));
                    classMenu.setItem(3, createMenuItem(
                            Material.PAPER,
                            "Anao",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Criatura robusta, mestre da forja e bravura."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Anao", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Forjador"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso a Grande Forja de Nidavellir use: &b/nidavellir"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar Armadura de: &bDiamante"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar Ferramentas de: &bDiamante"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Recebe resistência ao dano de quedas e aumento de dano com picaretas."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Possui 10% sorte aumentada em trocas com villager"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar armadura de &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de usar armaduras de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Anao"))
                    ));
                    classMenu.setItem(4, createMenuItem(
                            Material.PAPER,
                            "Demonio",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Criaturas do inferno, portadoras de dor."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Demonio", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Infernal"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Lança bolas de fogo que causam dano."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Demonio"))
                    ));
                    classMenu.setItem(5, createMenuItem(
                            Material.PAPER,
                            "Lamia",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Seres sedutores, amaldiçoadas pelo caos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Lamia", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Presas vampiricas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Cura-se ao atacar inimigos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cVulneravel ao sol."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Lamia"))
                    ));
                    classMenu.setItem(6, createMenuItem(
                            Material.PAPER,
                            "Nosferatu",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Vampiros deformados pela sede de sangue."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Nosferatu", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Escuridão sem fim"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Cura-se ao atacar inimigos."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Gera uma aura de escuridão que cega os inimigos próximos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cVulneravel ao sol."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Nosferatu"))
                    ));
                    classMenu.setItem(7, createMenuItem(
                            Material.PAPER,
                            "Druida",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Espírito vingativo, gritos que anunciam morte."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Druida", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Amante das plantas "),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7É capaz de criar runas de tipo: &8Comum &7e &eRara"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Se auto cura caso estiver encima da grama"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Faz com que todas as plantações ao seu redor, em uma área de 10x10 blocos, cresçam instantaneamente."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Druida"))
                    ));
                    classMenu.setItem(8, createMenuItem(
                            Material.PAPER,
                            "Necromancer",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Mestre da morte, controlando almas perdidas."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Necromancer", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Necromancia"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Revive esqueletos e zumbis para lutar ao seu lado dando sua vida em troca."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Necromancer"))
                    ));
                    classMenu.setItem(9, createMenuItem(
                            Material.PAPER,
                            "AnaoMercador",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Comerciante astuto, com ouro nas mãos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("AnaoMercador", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Forjador mercante"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso a Grande Forja de Nidavellir use: &b/nidavellir"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar todos os tipos de ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar todos os tipos de armaduras"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Recebe resistência ao dano de quedas e aumento de dano com picaretas."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Possui 50% de sorte aumentada em trocas com villager"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "AnaoMercador"))
                    ));
                    classMenu.setItem(10, createMenuItem(
                            Material.PAPER,
                            "Albion",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Terras antigas, perdidas no tempo e magia."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Albion", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Tempestade de fogo"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Conjura uma tempestade de fogo que causa dano aos inimigos ao redor."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Albion"))
                    ));
                    classMenu.setItem(11, createMenuItem(
                            Material.PAPER,
                            "Driade",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Guardiã das florestas, unida à natureza."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Driade", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7É capaz criar runas de tipo: &8Comum&7,&eRara &7e &dEpico"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7IAplica veneno em inimigos quando estão na grama."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Driade"))
                    ));
                    classMenu.setItem(12, createMenuItem(
                            Material.PAPER,
                            "MortoVivo",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Reanimados pela magia, sem alma ou paz."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("MortoVivo", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Drenagem"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Drena vida dos inimigos próximos e os transforma em aliados mortos-vivos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue usar porção de cura."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "MortoVivo"))
                    ));
                    classMenu.setItem(13, createMenuItem(
                            Material.PAPER,
                            "VampiroRei",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Caçadores noturnos, imortais e sedentos."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("VampiroRei", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Conde dracula"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Cura-se ao atacar inimigos."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Gera uma aura de escuridão que cega os inimigos próximos."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Invulneravel ao sol."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "VampiroRei"))
                    ));
                    classMenu.setItem(14, createMenuItem(
                            Material.PAPER,
                            "PrincipeInfernal",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Herdeiro do inferno, governante das sombras."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("PrincipeInfernal", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Decendente do inferno"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Conjura uma tempestade de fogo que causa dano aos inimigos ao redor."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Invoca demônios para lutar ao lado do jogador."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &bDiamante &ce &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "PrincipeInfernal"))
                    ));
                    classMenu.setItem(15, createMenuItem(
                            Material.PAPER,
                            "TuathaDeDanann",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Mágicos antigos, protetores do equilíbrio."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("TuathaDeDanann", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Alido da floresta"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7É capaz criar runas de tipo: &8Comum&7,&eRara &7e &dEpico"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Aplica resistencia em membros aliados quando está encima da grama."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "TuathaDeDanann"))
                    ));
                    classMenu.setItem(16, createMenuItem(
                            Material.PAPER,
                            "AnaoNobre",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nobre das montanhas, orgulhoso e honrado."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("AnaoNobre", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Forjador Ancião"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso a Grande Forja de Nidavellir use: &b/nidavellir"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar todos os tipos de ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode Crafitar todos os tipos de armaduras"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Recebe resistência ao dano de quedas e aumento de dano com picaretas."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Possui 100% de sorte aumentada em trocas com villager"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "AnaoNobre"))
                    ));
                    classMenu.setItem(17, createMenuItem(
                            Material.PAPER,
                            "CavaleiroDaFome",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Guerreiro faminto, sem compaixão ou descanso."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("CavaleiroDaFome", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Fome eterna"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Dano aumentado quando a fome do jogador está baixa."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Suga a fome total do inimigo"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "CavaleiroDaFome"))
                    ));
                    classMenu.setItem(18, createMenuItem(
                            Material.PAPER,
                            "CavaleiroDaPeste",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Portador de doenças, sem piedade ou cura."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("CavaleiroDaPeste", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Propagador da peste"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Causa um efeito de peste em área."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "CavaleiroDaPeste"))
                    ));
                    classMenu.setItem(19, createMenuItem(
                            Material.PAPER,
                            "CavaleiroDaGuerra",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Soldado eterno, forjado pelo conflito."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("CavaleiroDaGuerra", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Guerreiro absoluto"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Arremeça lanças que causam dano a múltiplos inimigos em linha reta."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Possui o dano de combate e critico aumentado."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "CavaleiroDaGuerra"))
                    ));
                    classMenu.setItem(20, createMenuItem(
                            Material.PAPER,
                            "CavaleiroDaMorte",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Executor das almas, imbatível e sombrio."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("CavaleiroDaMorte", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Cemiterio das almas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Golpes causam dano extra e têm chance de matar instantaneamente."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "CavaleiroDaMorte"))
                    ));
                    classMenu.setItem(21, createMenuItem(
                            Material.PAPER,
                            "Poseidon",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Deus do mar, com poder sobre as águas."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Poseidon", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Deus dos mares"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Possui resistencia aumentada quando está dentro da agua."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode nadar muita mais rapido que o normal e respirar infinitamente abaixo da agua."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &8Netherite"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Poseidon"))
                    ));
                    classMenu.setItem(22, createMenuItem(
                            Material.PAPER,
                            "Ares",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Deus da guerra, impiedoso e sanguinário."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Ares", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Deus da guerra"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Pode equipar qualquer tipo de armadura."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Aplica efeitos sobrehumanos em si-mesmo e aliados proximos."),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Deixa seus inimigos fracos sobre blocos de: Areia, Netherreck, Area da Alma e Nether bricks."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),

                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Ares"))
                    ));
                    classMenu.setItem(23, createMenuItem(
                            Material.PAPER,
                            "Overlord",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Feiticeiro milenar, governando o além."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Overlord", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6O governante das tumbas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Consegue criar runas do tipo: &8Comum&7, &eRara&7, &dEpico &7e &5Lendário"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Lança um poder de Ar explosivo."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão consegue equipar Armadura de: &bDiamante &ce &8Netherite"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de criar runas de tipo: &bDivino"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Overlord"))
                    ));
                    classMenu.setItem(24, createMenuItem(
                            Material.PAPER,
                            "Mago",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Conjurador de feitiços, mestre do arcano."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("Mago", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Mago mestre"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Consegue criar runas do tipo: &8Comum&7, &eRara&7, &dEpico &7e &5Lendário"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Lança uma bola de fogo que causa grande dano aos inimigos ao atingir."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de criar runas de tipo: &bDivino"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "Mago"))
                    ));
                    classMenu.setItem(25, createMenuItem(
                            Material.PAPER,
                            "MagoRegente",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Sub mágico, detentor do saber ancestral."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("MagoRegente", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Grande sabio"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Consegue criar runas do tipo: &8Comum&7, &eRara&7, &dEpico &7e &5Lendário"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Cria uma barreira invisível que absorve dano de ataques inimigos por um tempo."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de crafitar Armadura e nem ferramentas"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão é capaz de criar runas de tipo: &bDivino"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "MagoRegente"))
                    ));
                    classMenu.setItem(26, createMenuItem(
                            Material.PAPER,
                            "ReiMago",
                            ChatColor.translateAlternateColorCodes('&', "&7- &fInformação da &f[&bClasse&f]."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aDescrição:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Soberano do arcano, mestre supremo."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aStatus:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível atual: " + playerLevels.getOrDefault("ReiMago", 0)),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Nível máximo: " + maxLevel),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aSuperioridade: &6Dominador do Chronos"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao Templo Arcano use: &b/temploarcano"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso a Grande Forja de Nidavellir use: &b/nidavellir"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7É capaz de criar todos os tipos de runa"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso total a &bMagia do Tempo!"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso total a &bMagia de Restrição!"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso total a &bMagia de Transformação!"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Acesso ao &bGrimorio do Tempo!"),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aInferioridade:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &cNão pode usar nenhum tipo de armadura."),
                            "",
                            ChatColor.translateAlternateColorCodes('&', "&aEconomia:"),
                            ChatColor.translateAlternateColorCodes('&', "&a❙ &7Custo: $ " + getClassCost(player, "ReiMago"))
                    ));

                    // Abre o inventário para o jogador
                    player.openInventory(classMenu);
                });
            } catch (SQLException e) {
                // Caso ocorra um erro, envia a mensagem no thread principal
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.sendMessage(ChatColor.RED + "Erro ao carregar informações do banco de dados.");
                });
                e.printStackTrace();
            }
        });
    }

    private String getNextClass(String currentClass) {
        switch (currentClass) {
            case "Humano": // 1
                return "HumanoMercador";
            case "HumanoMercador": // 2
                return "HumanoNobre";
            case "HumanoNobre": // 3
                return "Anao";
            case "Anao": // 4
                return "Demonio";
            case "Demonio": // 5
                return "Lamia";
            case "Lamia": // 6
                return "Nosferatu";
            case "Nosferatu": // 7
                return "Druida";
            case "Druida":// 8
                return "Necromancer";
            case "Necromancer": // 9
                return "AnaoMercador";
            case "AnaoMercador":// 10
                return "Albion";
            case "Albion": // 11
                return "Driade";
            case "Driade":// 12
                return "MortoVivo";
            case "MortoVivo": // 13
                return "VampiroRei";
            case "VampiroRei": // 14
                return "PrincipeInfernal";
            case "PrincipeInfernal": // 15
                return "TuathaDeDanann";
            case "TuathaDeDanann":// 16
                return "AnaoNobre";
            case "AnaoNobre":// 17
                return "CavaleiroDaFome";
            case "CavaleiroDaFome":// 18
                return "CavaleiroDaPeste";
            case "CavaleiroDaPeste":// 19
                return "CavaleiroDaGuerra";
            case "CavaleiroDaGuerra":// 20
                return "CavaleiroDaMorte";
            case "CavaleiroDaMorte":// 21
                return "Poseidon";
            case "Poseidon":// 22
                return "Ares";
            case "Ares":// 23
                return "Overlord";
            case "Overlord": // 12
                return "Mago";
            case "Mago":// 24
                return "MagoRegente";
            case "MagoRegente":// 25
                return "ReiMago"; // 26
            default:
                return currentClass;
        }
    }

    private String getClassCost(Player player, String className) {
        try {
            String uuid = player.getUniqueId().toString();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT class_level FROM legendary_player_classes WHERE uuid = ? AND class_name = ?");
            statement.setString(1, uuid);
            statement.setString(2, className);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int currentLevel = resultSet.getInt("class_level");
                upleve.ClassData classData = upleve.getClassData(className, currentLevel + 1);

                if (classData != null) {
                    Number costNumber = classData.getCost(); // Pode ser int ou long
                    double cost = costNumber.doubleValue(); // Convertendo corretamente para double

                    // Usando DecimalFormat para formatar sem o símbolo de moeda
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                    return decimalFormat.format(cost); // Retorna o valor formatado sem o "R$"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Sem custo!";
    }


    private ItemStack createMenuItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + name);
            meta.setLore(Arrays.asList(lore));
            item.setItemMeta(meta);
        }

        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GOLD + "Painel de Classes")) {
            event.setCancelled(true);
            if (event.getAction() == InventoryAction.DROP_ALL_CURSOR || event.getAction() == InventoryAction.DROP_ONE_CURSOR ||
                    event.getAction() == InventoryAction.DROP_ALL_SLOT || event.getAction() == InventoryAction.DROP_ONE_SLOT) {
                event.setCancelled(true);
                return;
            }
            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.PLACE_ALL ||
                    event.getAction() == InventoryAction.PLACE_ONE || event.getAction() == InventoryAction.PLACE_SOME) {
                event.setCancelled(true);
                return;
            }
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                Player player = (Player) event.getWhoClicked();
                String className = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    if (chargeCost(player, className)) {
                        levelUpClass(player, className); // Upar a classe
                        player.sendMessage(ChatColor.GREEN + "Você subiu para o próximo nível de " + "§f[§b" + className + "§f]");
                    } else {
                        player.sendMessage(ChatColor.RED + "Você não tem dinheiro suficiente para subir de nível.");
                    }
                // player.closeInventory();
            }
        }
    }

    private boolean chargeCost(Player player, String className) {
        if (economy == null) {
            getLogger().warning("Sistema de economia não inicializado.");
            return false;
        }
        try {
            String uuid = player.getUniqueId().toString();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT class_level FROM legendary_player_classes WHERE uuid = ? AND class_name = ?");
            statement.setString(1, uuid);
            statement.setString(2, className);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int currentLevel = resultSet.getInt("class_level");
                upleve.ClassData classData = upleve.getClassData(className, currentLevel + 1); // Próximo nível

                if (classData == null) {
                    player.sendMessage(ChatColor.RED + "Custo não encontrado para esta classe.");
                    return false;
                }

                Number costNumber = classData.getCost(); // Pode ser int ou long
                double cost = costNumber.doubleValue(); // Convertendo corretamente para double

                if (economy.getBalance(player) >= cost) {
                    economy.withdrawPlayer(player, cost);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private ItemStack chanceGiveItem() {
        // Criar os itens
        ItemStack item1 = new ItemStack(Material.PAPER);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("§6Bolsa de Presente");
        List<String> lore1 = new ArrayList<>();
        lore1.add("§7- §fInformação da §f[§6Relíquia§f].");
        lore1.add("");
        lore1.add("§aDescrição:");
        lore1.add("§a❙ §7Usado para dar presente ao usuario.");
        lore1.add("");
        lore1.add("§aEconomia:");
        lore1.add("§a❙ §4Proibido a comercialização");
        lore1.add("");
        lore1.add("§a(!) Esse §dITEM §aé §dépico §amais informações acesse nosso site!");
        meta1.setLore(lore1);
        item1.setItemMeta(meta1);

        ItemStack item2 = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("§6Potion de Protecao");
        List<String> lore2 = new ArrayList<>();
        lore2.add("§7- §fInformação da §f[§6Relíquia§f].");
        lore2.add("");
        lore2.add("§aDescrição:");
        lore2.add("§a❙ §7Usado para dar imortabilidade por 1 minuto ao usuario.");
        lore2.add("§a❙ §7As vezes pode falhar ao enfrentar: §cEnder Dragon, Wither, Demônios e Mandamentos.");
        lore2.add("");
        lore2.add("§aEconomia:");
        lore2.add("§a❙ §4Proibido a comercialização");
        lore2.add("");
        lore2.add("§a(!) Esse §dITEM §aé §dépico §amais informações acesse nosso site!");
        meta2.setLore(lore2);
        item2.setItemMeta(meta2);

        ItemStack item3 = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName("§6Potion de Forca");
        List<String> lore3 = new ArrayList<>();
        lore3.add("§7- §fInformação da §f[§6Relíquia§f].");
        lore3.add("");
        lore3.add("§aDescrição:");
        lore3.add("§a❙ §7Usado para dar a força dos deuses por 1 minuto ao usuario.");
        lore3.add("§a❙ §7As vezes pode falhar ao enfrentar: §cEnder Dragon, Wither, Demônios e Mandamentos.");
        lore3.add("");
        lore3.add("§aEconomia:");
        lore3.add("§a❙ §4Proibido a comercialização");
        lore3.add("");
        lore3.add("§a(!) Esse §dITEM §aé §dépico §amais informações acesse nosso site!");
        meta3.setLore(lore3);
        item3.setItemMeta(meta3);

        ItemStack item4 = new ItemStack(Material.PAPER);
        ItemMeta meta4 = item4.getItemMeta();
        meta4.setDisplayName("§4§lAlma de Fada");
        List<String> lore4 = new ArrayList<>();
        lore4.add("§7- §fInformação da §f[§bAlma§f].");
        lore4.add("");
        lore4.add("§aDescrição:");
        lore4.add("§a❙ §7Usado para trocas dentro da /warp loja");
        lore4.add("");
        lore4.add("§aEconomia:");
        lore4.add("§a❙ §7$ 500.000,00");
        lore4.add("");
        lore4.add("§a(!) Esse ITEM é §6raro §amais informações acesse nosso site!");
        meta4.setLore(lore4);
        item4.setItemMeta(meta4);

        double random = Math.random();
        if (random < 0.5) {
            return item1;
        } else if (random < 0.6) {
            return item2;
        } else if (random < 0.7) {
            return item3;
        } else {
            return item4;
        }
    }

    private void levelUpClass(Player player, String className) {
        String uuid = player.getUniqueId().toString();
        String query = "SELECT class_level FROM legendary_player_classes WHERE uuid = ? AND class_name = ?";
        String updateLevelQuery = "UPDATE legendary_player_classes SET class_level = ? WHERE uuid = ? AND class_name = ?";
        String updateClassQuery = "UPDATE legendary_player_classes SET class_name = ? WHERE uuid = ?";

        // Obter o item gerado por chanceGiveItem
        ItemStack generatedItem = chanceGiveItem();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            statement.setString(2, className);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int currentLevel = resultSet.getInt("class_level");

                try (PreparedStatement updateStatement = connection.prepareStatement(updateLevelQuery)) {
                    if (currentLevel == 100) {
                        // Resetar para nível 1 e mudar de classe
                        updateStatement.setInt(1, 1);
                        updateStatement.setString(2, uuid);
                        updateStatement.setString(3, className);
                        updateStatement.executeUpdate();

                        String nextClass = getNextClass(className);

                        try (PreparedStatement updateClassStatement = connection.prepareStatement(updateClassQuery)) {
                            updateClassStatement.setString(1, nextClass);
                            updateClassStatement.setString(2, uuid);
                            updateClassStatement.executeUpdate();
                        }

                        player.getInventory().addItem(generatedItem);
                        player.sendMessage(ChatColor.GREEN + "Você foi promovido para a classe " + nextClass + "!");
                    } else {
                        // Aumentar nível
                        updateStatement.setInt(1, currentLevel + 1);
                        updateStatement.setString(2, uuid);
                        updateStatement.setString(3, className);
                        updateStatement.executeUpdate();

                        player.getInventory().addItem(generatedItem);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void savePlayerClass(Player player, String className) {
        String uuid = player.getUniqueId().toString();
        String playerName = player.getName();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO legendary_player_classes (uuid, player_name, class_name) VALUES (?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE player_name = ?, class_name = ?");
            statement.setString(1, uuid);
            statement.setString(2, playerName);
            statement.setString(3, className);
            statement.setString(4, playerName);
            statement.setString(5, className);
            statement.executeUpdate();

            getLogger().info("Classe salva no banco de dados: " + playerName + " -> " + className);
        } catch (SQLException e) {
            // player.sendMessage(ChatColor.RED + "Erro ao salvar sua classe no banco de dados.");
            getLogger().severe("Erro ao salvar classe no banco de dados: " + e.getMessage());
        }
    }

    private void applyClassPowers(Player player, String className) {
        try {
            String packageName = "www.legendarycommunity.com.br.legendary_classes.classes";
            String fullClassName = packageName + "." + className;
            Class<?> clazz = Class.forName(fullClassName);
            Object instance = clazz.getConstructor(Legendary_classes.class).newInstance(this); // Passa o plugin em vez da Connection

            if (clazz.getMethod("applyPowers", Player.class) != null) {
                clazz.getMethod("applyPowers", Player.class).invoke(instance, player);
            }
        } catch (Exception e) {
            getLogger().severe("Erro ao aplicar poderes da classe: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        try {
            // Consultar dados do jogador
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT class_name, class_level FROM legendary_player_classes WHERE uuid = ?");
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String className = resultSet.getString("class_name");
                int classLevel = resultSet.getInt("class_level");
                if (classLevel == 100) {
                    className = getNextClass(className);
                    classLevel = 1;
                    PreparedStatement updateStatement = connection.prepareStatement(
                            "UPDATE legendary_player_classes SET class_name = ?, class_level = ? WHERE uuid = ?");
                    updateStatement.setString(1, className);
                    updateStatement.setInt(2, classLevel);
                    updateStatement.setString(3, playerUUID.toString());
                    updateStatement.executeUpdate();
                }
                // Armazenar os dados no cache
                playerDataCache.put(playerUUID, new PlayerClassData(className, classLevel));
                PlayerClassData data = playerDataCache.get(playerUUID);
                if (data != null && player.isOnline()) {
                    applyClassPowers(player, data.getClassName());
                    player.setDisplayName("[" + className + "] " + player.getName());
                } else {
                    getLogger().warning("O jogador " + player.getName() + " não possui dados em cache ou não está online.");
                }
            } else {
                savePlayerClass(player, "Humano");
                playerDataCache.put(playerUUID, new PlayerClassData("Humano", 1));
                applyClassPowers(player, "Humano");
                player.setDisplayName("[Humano] " + player.getName());
            }
        } catch (SQLException e) {
            getLogger().severe("Erro ao verificar classe no banco de dados: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerUUID = player.getUniqueId();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            PlayerClassData data = playerDataCache.get(playerUUID);
            if (data != null && player.isOnline()) {
                applyClassPowers(player, data.getClassName());
            } else {
                getLogger().warning("O jogador " + player.getName() + " não possui dados em cache ou não está online.");
            }
        }, 40L);
    }

    public PlayerClassData getPlayerData(UUID playerUUID) {
        return playerDataCache.get(playerUUID);
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Obter os dados diretamente do cache
        PlayerClassData data = playerDataCache.get(playerUUID);
        if (data != null) {
            String className = data.getClassName();
            int classLevel = data.getClassLevel();

            // Definir a cor baseada no nível da classe
            String color;
            if (classLevel >= 1 && classLevel <= 10) {
                color = "§8";
            } else if (classLevel >= 11 && classLevel <= 30) {
                color = "§6";
            } else if (classLevel >= 31 && classLevel <= 60) {
                color = "§5";
            } else if (classLevel >= 61 && classLevel <= 100) {
                color = "§d";
            } else if (classLevel >= 101 && classLevel <= 999) {
                color = "§b";
            } else {
                color = "§f"; // Cor padrão caso o nível esteja fora do intervalo
            }

            // Formatar o nome da classe
            className = className.replaceAll("([A-Z])", " $1").trim();
            String formattedClassName = " §f[§e" + classLevel + "§f] §f[" + color + className + "§f]";

            // Configurar o formato do chat
            String displayName = player.getDisplayName().replace("%", "%%");
            String message = event.getMessage().replace("%", "%%");
            event.setFormat(formattedClassName.replace("%", "%%") + " §7" + displayName + ": §f" + message);
        } else {
            getLogger().severe("Dados de classe não encontrados no cache para o jogador " + player.getName());
        }
    }

    public FileConfiguration getRunasConfig() {
        return RunasConfig;
    }
    private void saveDefaultRunasConfig() {
        RunasFile = new File(getDataFolder(), "runas.yml");
        if (!RunasFile.exists()) {
            saveResource("runas.yml", false);
        }
        RunasConfig = YamlConfiguration.loadConfiguration(RunasFile);
    }


}
