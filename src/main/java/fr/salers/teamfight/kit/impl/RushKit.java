package fr.salers.teamfight.kit.impl;

import com.samjakob.spigui.item.ItemBuilder;
import fr.salers.teamfight.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Salers
 * made on fr.salers.teamfight.kit.impl
 */
public class RushKit implements Kit {

    @Override
    public void giveToPlayer(Player player) {
        final ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .enchant(Enchantment.DAMAGE_ALL, 1).enchant(Enchantment.DURABILITY, 3).build();
        final ItemStack pickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE).
                enchant(Enchantment.DIG_SPEED, 3).enchant(Enchantment.DURABILITY, 3).build();
        final ItemStack gapples = new ItemBuilder(Material.GOLDEN_APPLE).amount(32).build();
        final ItemStack blocks = new ItemBuilder(Material.SANDSTONE).data((short) 2).amount(64).build();
        final ItemStack helmet = new ItemBuilder(Material.IRON_HELMET)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 3).build();
        final ItemStack chestplate = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).enchant(Enchantment.DURABILITY, 3).build();
        final ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 3).build();
        final ItemStack boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).enchant(Enchantment.DURABILITY, 3).build();

        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, pickaxe);
        player.getInventory().setItem(2, gapples);
        for (int i = 3; i < 17; i++)
            player.getInventory().setItem(i, blocks);
        
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
    }
}
