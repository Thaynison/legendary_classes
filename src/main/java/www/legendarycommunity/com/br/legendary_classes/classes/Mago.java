package www.legendarycommunity.com.br.legendary_classes.classes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.Connection;

public class Mago {

    private final Connection connection;

    public Mago(Connection connection) {
        this.connection = connection;
    }

    public void applyPowers(Player player) {
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 2, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true, false));

        player.sendMessage(ChatColor.GREEN + "Você recebeu os poderes da classe Mago!");
    }
}
