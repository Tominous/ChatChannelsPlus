package nl.justmaffie.chatchannelsplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import nl.justmaffie.chatchannelsplus.ChatChannelsPlus;
import nl.justmaffie.chatchannelsplus.Messages;
import nl.justmaffie.pluginlibrary.CustomPlugin;
import nl.justmaffie.pluginlibrary.command.AbstractCommand;
import nl.justmaffie.pluginlibrary.command.CommandResult;
import nl.justmaffie.pluginlibrary.command.EmptyTabCompleter;

public class CCPCommand extends AbstractCommand {

	public CCPCommand(CustomPlugin plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "chatchannelsplus";
	}

	@Override
	public TabCompleter getTabCompleter() {
		return new EmptyTabCompleter();
	}

	@Override
	public CommandResult onConsoleSender(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			return CommandResult.get("&8&m+-----------------+",
					"&b&lChatChannelsPlus &r&o" + this.plugin.getDescription().getVersion(),
					"&7Created by &r&oJustMaffie", "&8&m+-----------------+");
		}
		String arg0 = args[0];
		if (arg0.equalsIgnoreCase("reload")) {
			ChatChannelsPlus plugin = (ChatChannelsPlus) this.plugin;
			plugin.reload();
			return CommandResult.get(Messages.CONFIG_RELOADED.getMessage());
		}
		if (arg0.equalsIgnoreCase("help")) {
			return CommandResult.get(Messages.MAIN_COMMAND_HELP.getMessage().replaceAll("%label%", label));
		}
		return CommandResult.get(Messages.MAIN_COMMAND_USAGE.getMessage().replaceAll("%label%", label));
	}

	@Override
	public CommandResult onPlayerSender(Player player, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			return CommandResult.get("&8&m+-----------------+",
					"&b&lChatChannelsPlus &r&o" + this.plugin.getDescription().getVersion(),
					"&7Created by &r&oJustMaffie", "&8&m+-----------------+");
		}
		String arg0 = args[0];
		if (arg0.equalsIgnoreCase("reload")) {
			if (!player.hasPermission("chatchannelplus.reload")) {
				return CommandResult.get(Messages.NO_PERMISSION_RELOAD.getMessage());
			}
			ChatChannelsPlus plugin = (ChatChannelsPlus) this.plugin;
			plugin.reload();
			return CommandResult.get(Messages.CONFIG_RELOADED.getMessage());
		}
		if (arg0.equalsIgnoreCase("help")) {
			if (!player.hasPermission("chatchannelsplus.help")) {
				return CommandResult.get(Messages.NO_PERMISSION_MAIN_HELP.getMessage());
			}
			return CommandResult.get(Messages.MAIN_COMMAND_HELP.getMessage().replaceAll("%label%", label));
		}
		return CommandResult.get(Messages.MAIN_COMMAND_USAGE.getMessage().replaceAll("%label%", label));
	}

}
