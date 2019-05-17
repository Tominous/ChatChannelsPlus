package nl.justmaffie.chatchannelsplus;

import nl.justmaffie.pluginlibrary.utils.Colors;

public enum Messages {
	INVALID_CHANNEL("invalid_channel", "&cChannel &7%channel_name% &cdoes not exist!"),
	SWITCHED_CHANNEL("switched_channel", "&cSwitched to chat channel %name%&c!"),
	NO_PERMISSION_RELOAD("no_permission.reload",
			"&cYou must have at least staff rank &7[&4&lAdmin&7] &cfor this command"),
	NO_PERMISSION_MAIN_HELP("no_permission.main.help",
			"&cYou must have at least staff rank &7[&4&lAdmin&7] &cfor this command"),
	NO_PERMISSION_CHANNEL("no_permission.channels.main",
			"&cYou must have at least staff rank &7[&e&lHelper&7] &cfor this command"),
	CONFIG_RELOADED("reload_done", "&cConfiguration successfully reloaded"),
	LOST_PERM_MOVED_BACK("no_permission.moved_back", "&cYou no longer have permission to talk in the channel %channel_name%&c, you've been moved back to the channel &7Default&c!"),
	CHANNEL_COMMAND_USAGE("usage.channel.main",
			"&cUse &7/%label% <name> &cto switch channels. Or, use &7/%label% help &cto view a list of commands"),
	CHANNEL_MUTE_COMMAND_USAGE("usage.channel.mute",
			"&cUsage: &7/%label% mute <channel>"),
	CHANNEL_UNMUTE_COMMAND_USAGE("usage.channel.unmute",
			"&cUsage: &7/%label% unmute <channel>"),
	CHANNEL_SWITCHED("channel.switched",
			"&cSuccessfully changed your channel to %channel_name%&c!"),
	LIST_ACCESSED_CHANNELS("channel.list_accessed_channels",
			"&cYou have access to the following channels: %accessed_channels%&c!"),
	ALREADY_IN_CHANNEL("channel.already_in",
			"&cYou are already in the channel %channel_name%&c!"),
	NO_MUTED_CHANNELS("channel.muted_channels.none",
			"&cYou have not muted any channels!"),
	ALREADY_MUTED("channel.muted_channels.already_muted", "&cYou have already muted channel %mute_channel_name%&c!"),
	NOT_MUTED("channel.muted_channels.not_muted", "&cYou haven't muted channel %mute_channel_name%&c!"),
	MUTED_CHANNEL("channel.muted_channels.muted_channel", "&cYou have successfully muted channel %mute_channel_name%&c!"),
	UNMUTED_CHANNEL("channel.muted_channels.unmuted_channel", "&cYou have successfully unmuted channel %mute_channel_name%&c!"),
	LIST_MUTED_CHANNELS("channel.muted_channels.list", "&cYou have muted the following channels: &7%muted_channels%&c!"),
	MAIN_COMMAND_USAGE("usage.chatchannelsplus", "&cUse &7/%label% help &cfor a list of commands."),
	MAIN_COMMAND_HELP("main.help",
					"&8&m+-----------------+\n"
					+ "&c/%label% help &7- Shows this page\n"
					+ "&c/%label% reload &7- Reloads the ChatChannelsPlus Configuration\n"
					+ "&8&m+-----------------+"),
	CHANNEL_COMMAND_HELP("channel.help",
					"&8&m+-----------------+\n"
					+ "&c/%label% <name> &7- Switch to the selected channel\n"
					+ "&c/%label% help &7- Shows this page\n"
					+ "&c/%label% muted &7- Get a list of channels you've muted\n"
					+ "&c/%label% access &7- Get a list of all available channels you have access to\n"
					+ "&c/%label% mute <name> &7- Mute the selected channel\n"
					+ "&c/%label% unmute <name> &7- Unmute the selected channel\n"
					+ "&8&m+-----------------+"),
	NO_ACCESS_CHANNELS("no_access_channels", "&cYou do not have access to any channels!");

	private String id;
	private String defaultMsg;
	private ChatChannelsPlus plugin;

	Messages(String id, String defaultMsg) {
		this.id = id;
		this.defaultMsg = defaultMsg;
	}

	public String getId() {
		return this.id;
	}

	public String getDefaultMessage() {
		return this.defaultMsg;
	}


	public void setPlugin(ChatChannelsPlus plugin) {
		this.plugin = plugin;
	}
	public String getMessage() {
		return Colors.color(this.plugin.getMessagesFile().get().getString(this.getId(),
				this.getDefaultMessage()));
	}
}
