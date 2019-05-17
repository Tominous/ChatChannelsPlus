package nl.justmaffie.chatchannelsplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import nl.justmaffie.chatchannelsplus.ChatChannelsPlus;
import nl.justmaffie.chatchannelsplus.Messages;
import nl.justmaffie.chatchannelsplus.api.IChatChannel;
import nl.justmaffie.pluginlibrary.CustomPlugin;
import nl.justmaffie.pluginlibrary.command.AbstractCommand;
import nl.justmaffie.pluginlibrary.command.CommandResult;
import nl.justmaffie.pluginlibrary.command.EmptyTabCompleter;
import nl.justmaffie.pluginlibrary.utils.Colors;

public class ChannelCommand extends AbstractCommand {

	public ChannelCommand(CustomPlugin plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "channel";
	}

	@Override
	public TabCompleter getTabCompleter() {
		return new EmptyTabCompleter();
	}

	@Override
	public CommandResult onConsoleSender(CommandSender sender, Command cmd, String label, String[] args) {
		return CommandResult.get("You cannot use this command in console");
	}

	@Override
	public CommandResult onPlayerSender(Player player, Command cmd, String label, String[] args) {
		ChatChannelsPlus plugin = (ChatChannelsPlus) this.plugin;
		if (!player.hasPermission("chatchannelsplus.access")) {
			return CommandResult.get(Messages.NO_PERMISSION_CHANNEL.getMessage());
		}
		if (args.length < 1) {
			return CommandResult.get(Messages.CHANNEL_COMMAND_USAGE.getMessage().replaceAll("%label%", label));
		}
		String arg0 = args[0];
		if (arg0.equalsIgnoreCase("help")) {
			return CommandResult.get(Messages.CHANNEL_COMMAND_HELP.getMessage().replaceAll("%label%", label));
		}
		if (arg0.equalsIgnoreCase("muted")) {
			if (!plugin.getMutedChannels().containsKey(player.getUniqueId())) {
				return CommandResult.get(Messages.NO_MUTED_CHANNELS.getMessage());
			}
			List<IChatChannel> mutedChannels = plugin.getMutedChannels().get(player.getUniqueId());
			StringBuilder b = new StringBuilder();
			mutedChannels.forEach((channel) -> {
				b.append(channel.getName() + ", ");
			});
			String muted = b.toString().substring(0, b.toString().length() - 2);
			return CommandResult.get(Messages.LIST_MUTED_CHANNELS.getMessage().replaceAll("%muted_channels%", muted));
		}
		if (arg0.equalsIgnoreCase("access")) {
			List<IChatChannel> accessedChannels = new ArrayList<IChatChannel>();
			plugin.getRegisteredChannels().forEach((channel) -> {
				boolean hasPerm = false;
				for (String permission : channel.getPermissions()) {
					if (!hasPerm) {
						if (player.hasPermission(permission)) {
							hasPerm = true;
							accessedChannels.add(channel);
						}
					}
				}
			});
			if (accessedChannels.size() < 1) {
				return CommandResult.get(Messages.NO_ACCESS_CHANNELS.getMessage());
			}
			StringBuilder b = new StringBuilder();
			accessedChannels.forEach((channel) -> {
				b.append(channel.getName() + ", ");
			});
			String accessed = b.toString().substring(0, b.toString().length() - 2);
			return CommandResult
					.get(Messages.LIST_ACCESSED_CHANNELS.getMessage().replaceAll("%accessed_channels%", accessed));
		}
		if (arg0.equalsIgnoreCase("mute"))

		{
			if (args.length < 2) {
				return CommandResult.get(Messages.CHANNEL_MUTE_COMMAND_USAGE.getMessage().replaceAll("%label%", label));
			}
			String arg1 = args[1];
			IChatChannel channel = null;
			for (IChatChannel channel1 : plugin.getRegisteredChannels()) {
				if (arg1.equalsIgnoreCase(channel1.getId())) {
					channel = channel1;
				}
			}
			if (channel == null) {
				return CommandResult.get(Messages.INVALID_CHANNEL.getMessage().replaceAll("%channel_name%", arg1));
			}
			List<IChatChannel> mutedChannels;
			if (plugin.getMutedChannels().containsKey(player.getUniqueId())) {
				mutedChannels = plugin.getMutedChannels().get(player.getUniqueId());
				plugin.getMutedChannels().remove(player.getUniqueId());
			} else {
				mutedChannels = new ArrayList<IChatChannel>();
			}
			for (IChatChannel channel1 : mutedChannels) {
				if (channel1.getId().equalsIgnoreCase(channel.getId())) {
					return CommandResult.get(
							Messages.ALREADY_MUTED.getMessage().replaceAll("%mute_channel_name%", channel.getName()));
				}
			}
			boolean hasPermission = false;
			for (String permission : channel.getPermissions()) {
				if (player.hasPermission(permission)) {
					hasPermission = true;
				}
			}
			if (!hasPermission) {
				return CommandResult.get(channel.getPermissionMessage());
			}
			mutedChannels.add(channel);
			plugin.getMutedChannels().put(player.getUniqueId(), mutedChannels);
			return CommandResult
					.get(Messages.MUTED_CHANNEL.getMessage().replaceAll("%mute_channel_name%", channel.getName()));
		}
		if (arg0.equalsIgnoreCase("unmute")) {
			if (args.length < 2) {
				return CommandResult
						.get(Messages.CHANNEL_UNMUTE_COMMAND_USAGE.getMessage().replaceAll("%label%", label));
			}
			String arg1 = args[1];
			IChatChannel channel = null;
			for (IChatChannel ichannel : plugin.getRegisteredChannels()) {
				if (arg1.equalsIgnoreCase(ichannel.getId())) {
					channel = ichannel;
				}
			}
			if (channel == null) {
				return CommandResult.get(Messages.INVALID_CHANNEL.getMessage().replaceAll("%channel_name%", arg1));
			}
			List<IChatChannel> mutedChannels = null;
			if (plugin.getMutedChannels().containsKey(player.getUniqueId())) {
				mutedChannels = plugin.getMutedChannels().get(player.getUniqueId());
				plugin.getMutedChannels().remove(player.getUniqueId());
			}
			if (mutedChannels == null) {
				return CommandResult
						.get(Messages.NOT_MUTED.getMessage().replaceAll("%mute_channel_name%", channel.getName()));
			}
			IChatChannel mutedChannel = null;
			for (IChatChannel ichannel : mutedChannels) {
				if (ichannel.getId().equalsIgnoreCase(channel.getId())) {
					mutedChannel = ichannel;
				}
			}
			if (mutedChannel == null) {
				return CommandResult
						.get(Messages.INVALID_CHANNEL.getMessage().replaceAll("%channel_name%", channel.getName()));
			}

			boolean hasMutedChannel = false;
			List<IChatChannel> valuesToRemove = new ArrayList<IChatChannel>();
			for (IChatChannel channell : mutedChannels) {
				if (channell.getId().equalsIgnoreCase(mutedChannel.getId())) {
					hasMutedChannel = true;
					valuesToRemove.add(channell);
				}
			}
			valuesToRemove.forEach(mutedChannels::remove);
			if (!hasMutedChannel) {
				return CommandResult
						.get(Messages.NOT_MUTED.getMessage().replaceAll("%mute_channel_name%", channel.getName()));
			}
			if (mutedChannels.size() > 0) {
				plugin.getMutedChannels().put(player.getUniqueId(), mutedChannels);
			}
			return CommandResult
					.get(Messages.UNMUTED_CHANNEL.getMessage().replaceAll("%mute_channel_name%", channel.getName()));
		}
		if (arg0.equalsIgnoreCase("default")) {
			if (!plugin.getUserChannels().containsKey(player.getUniqueId())) {
				return CommandResult.get(Messages.ALREADY_IN_CHANNEL.getMessage().replaceAll("%channel_name%",
						Colors.color("&7Default")));
			}
			plugin.getUserChannels().remove(player.getUniqueId());
			return CommandResult
					.get(Messages.CHANNEL_SWITCHED.getMessage().replaceAll("%channel_name%", Colors.color("&7Default")));
		}
		for (IChatChannel channel : plugin.getRegisteredChannels()) {
			if (channel.getId().equalsIgnoreCase(arg0)) {
				boolean hasPermission = false;
				for (String permission : channel.getPermissions()) {
					if (player.hasPermission(permission)) {
						hasPermission = true;
					}
				}

				if (!hasPermission) {
					return CommandResult.get(channel.getPermissionMessage());
				}
				if (plugin.getUserChannels().containsKey(player.getUniqueId())) {
					if (plugin.getUserChannels().get(player.getUniqueId()).getId().equalsIgnoreCase(channel.getId())) {
						return CommandResult.get(Messages.ALREADY_IN_CHANNEL.getMessage().replaceAll("%channel_name%",
								Colors.color(channel.getName())));
					}
				}
				plugin.getUserChannels().put(player.getUniqueId(), channel);

				return CommandResult
						.get(Messages.CHANNEL_SWITCHED.getMessage().replaceAll("%channel_name%", channel.getName()));
			}
		}
		return CommandResult.get(Messages.INVALID_CHANNEL.getMessage().replaceAll("%channel_name%", arg0));
	}

}
