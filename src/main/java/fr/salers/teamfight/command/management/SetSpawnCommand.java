package fr.salers.teamfight.command.management;

import fr.salers.teamfight.TFight;
import fr.salers.teamfight.config.Config;
import fr.salers.teamfight.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only players are able to use this command.");
            return false;
        }

        final Player player = (Player) sender;

        if(!player.hasPermission("teamfight.admin.setspawn")) {
            player.sendMessage(getNoPermissionmessage());
        }

        if (args.length > 0) {
            player.sendMessage(getHelpMessage());
            return false;
        }

        final double x = player.getLocation().getX();
        final double y = player.getLocation().getY();
        final double z = player.getLocation().getZ();

        final float yaw = player.getLocation().getYaw();
        final float pitch = player.getLocation().getPitch();
        final Location lobby = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
        final FileConfiguration config = TFight.INSTANCE.getPlugin().getConfig();
        config.set("teamfight.lobby.world", "world");
        config.set("teamfight.lobby.x", x);
        config.set("teamfight.lobby.y", y);
        config.set("teamfight.lobby.z", z);
        config.set("teamfight.lobby.yaw", yaw);
        config.set("teamfight.lobby.pitch", pitch);
        TFight.INSTANCE.getPlugin().saveConfig();
        player.sendMessage(CC.translate("&aTu as bien setup le lobby !"));
        return true;
    }

    private String getHelpMessage() {
        return CC.translate(
                "&aTeamFight - \n&r" +
                        CC.getLineBreak("&7") +
                        "&e/setspawn &7(set the lobby spawn)\n" +
                        CC.getLineBreak("&7")
        );
    }

    private String getNoPermissionmessage() {
        return CC.translate("&cI'm sorry, but you do not have permission to perform this command. " +
                "Please contact the server administrators if you believe that this is in error.");
    }

}
