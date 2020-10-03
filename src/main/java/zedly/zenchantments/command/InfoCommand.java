package zedly.zenchantments.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zedly.zenchantments.Config;
import zedly.zenchantments.Zenchantment;
import zedly.zenchantments.ZenchantmentsPlugin;
import zedly.zenchantments.player.PlayerData;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class InfoCommand extends ZenchantmentsCommand {
    public InfoCommand(ZenchantmentsPlugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "You must be a player to do this!");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("zenchantments.command.info")) {
            player.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "You do not have permission to do this!");
            return;
        }

        Config config = Config.get(player.getWorld());
        PlayerData playerData = this.plugin.getPlayerDataProvider().getDataForPlayer(player);

        if (args.length > 0) {
            Zenchantment zenchantment = config.enchantFromString(args[0]);
            if (zenchantment != null) {
                player.sendMessage(
                    ZenchantmentsCommand.MESSAGE_PREFIX
                        + zenchantment.getName()
                        + ": "
                        + (playerData.isDisabled(zenchantment.getId()) ? ChatColor.RED + "**Disabled** " : "")
                        + ChatColor.AQUA + zenchantment.getDescription()
                );
            }
            return;
        }

        Set<Zenchantment> zenchantments = Zenchantment.getEnchants(
            player.getInventory().getItemInMainHand(),
            true,
            config.getWorld()
        ).keySet();

        if (zenchantments.isEmpty()) {
            player.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "There are no zenchantments on this tool!");
            return;
        }

        player.sendMessage(ZenchantmentsCommand.MESSAGE_PREFIX + "Enchantment Info:");
        for (Zenchantment zenchantment : zenchantments) {
            player.sendMessage(
                ChatColor.DARK_AQUA
                    + zenchantment.getName()
                    + ": "
                    + (playerData.isDisabled(zenchantment.getId()) ? ChatColor.RED + "**Disabled** " : "")
                    + ChatColor.AQUA
                    + zenchantment.getDescription()
            );
        }
    }

    @Override
    public List<String> getTabCompleteOptions(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}