package nl.justmaffie.chatchannelsplus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import nl.justmaffie.chatchannelsplus.api.ChatChannel;
import nl.justmaffie.chatchannelsplus.api.IChatChannel;
import nl.justmaffie.chatchannelsplus.commands.CCPCommand;
import nl.justmaffie.chatchannelsplus.commands.ChannelCommand;
import nl.justmaffie.chatchannelsplus.listeners.ChatListener;
import nl.justmaffie.chatchannelsplus.listeners.JoinLeaveListener;
import nl.justmaffie.pluginlibrary.CustomPlugin;
import nl.justmaffie.pluginlibrary.storage.YmlFile;

public class ChatChannelsPlus extends CustomPlugin {
	private HashMap<UUID, IChatChannel> users;
	private List<IChatChannel> registeredChannels;
	private HashMap<UUID, List<IChatChannel>> mutedChannels;
	private YmlFile messagesFile;
	private YmlFile configFile;

	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
			getLogger().info("PlaceholderAPI is not installed");
			getLogger().info("This is a required plugin");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		new CCPPAPI(this).register();
		this.messagesFile = new YmlFile(this, "messages.yml");
		for (Messages message : Messages.values()) {
			this.messagesFile.get().addDefault(message.getId(), message.getDefaultMessage());
			message.setPlugin(this);
		}
		this.messagesFile.get().options().copyDefaults(true);
		this.messagesFile.save();
		this.saveResource("config.yml", false);
		this.configFile = new YmlFile(this, "config.yml");
		this.reload();
		this.mutedChannels = new HashMap<UUID, List<IChatChannel>>();
		this.users = new HashMap<UUID, IChatChannel>();
		getCommand("chatchannelsplus").setExecutor(new CCPCommand(this));
		getCommand("channel").setExecutor(new ChannelCommand(this));
		Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
		Bukkit.getPluginManager().registerEvents(new JoinLeaveListener(this), this);
	}

	public HashMap<UUID, List<IChatChannel>> getMutedChannels() {
		return this.mutedChannels;
	}

	public YmlFile getMessagesFile() {
		return this.messagesFile;
	}

	public YmlFile getConfigFile() {
		return this.configFile;
	}

	public void reload() {
		this.messagesFile.reload();
		this.configFile.reload();
		this.registeredChannels = new ArrayList<IChatChannel>();
		ConfigurationSection section = this.getConfigFile().get().getConfigurationSection("channels");
		Collection<String> channels = section.getKeys(false);
		for (String channel : channels) {
			ConfigurationSection channelSection = section.getConfigurationSection(channel);
			this.registeredChannels.add(new ChatChannel(channel, channelSection.getString("name"),
					channelSection.getString("format"), channelSection.getStringList("permissions"),
					channelSection.getString("no_permission_message")));
		}
	}

	public HashMap<UUID, IChatChannel> getUserChannels() {
		return this.users;
	}

	public List<IChatChannel> getRegisteredChannels() {
		return this.registeredChannels;
	}
}
