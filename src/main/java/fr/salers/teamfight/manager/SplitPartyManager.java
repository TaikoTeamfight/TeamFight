package fr.salers.teamfight.manager;

import com.alessiodp.parties.api.interfaces.Party;
import com.samjakob.spigui.item.ItemBuilder;
import fr.salers.teamfight.fight.sub.SplitParty;
import fr.salers.teamfight.player.TFPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SplitPartyManager {

    private final Map<Party, SplitParty> splitPartys = new HashMap<>();

    public void add(final Party party) {
        splitPartys.put(party, new SplitParty(party));
    }

    public void remove(final Party party) {
        splitPartys.remove(party);
    }

    public SplitParty get(final Party party) {
        return splitPartys.get(party);
    }

    public void giveSplitPartyItems(Player player) {
        final ItemBuilder item = new ItemBuilder(Material.SLIME_BALL)
                .name("&cSplit Party")
                .flag(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

        player.getInventory().setItem(1, item.build());
    }

}
