package fr.salers.teamfight.scoreboard.adapter;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.manager.PlayerManager;
import fr.salers.teamfight.player.TFPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamfightCache {

    public static int getPlayersInMatch() {
        List<TFPlayer> tfPlayerList = new ArrayList<>();
        int size = 0;
        for(Player player : Bukkit.getOnlinePlayers()) {
            tfPlayerList.add(TFight.INSTANCE.getPlayerManager().get(player));
        }

        for(TFPlayer tfPlayer : tfPlayerList) {
            if(tfPlayer.isFighting()) {
                size ++;
            }
        }
        return size;
    }

    public static int getPlayersOnline() {
        return Bukkit.getOnlinePlayers().size();
    }
}
