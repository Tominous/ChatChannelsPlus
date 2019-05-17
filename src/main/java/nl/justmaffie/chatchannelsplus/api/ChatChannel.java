package nl.justmaffie.chatchannelsplus.api;

import java.util.List;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import nl.justmaffie.pluginlibrary.utils.Colors;

public class ChatChannel implements IChatChannel {

	private String id;
	private String name;
	private String format;
	private List<String> permissions;
	private String permissionMessage;

	public ChatChannel(String id, String name, String format, List<String> permissions, String permissionMessage) {
		this.id = id;
		this.name = name;
		this.format = format;
		this.permissions = permissions;
		this.permissionMessage = permissionMessage;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return Colors.color(this.name);
	}

	@Override
	public String getFormat() {
		return this.format;
	}

	@Override
	public String formatMessage(Player player, String message) {
		return Colors.color(PlaceholderAPI.setPlaceholders(player, this.getFormat())).replaceAll("%message%", message);
	}

	@Override
	public List<String> getPermissions() {
		return this.permissions;
	}

	@Override
	public String getPermissionMessage() {
		return Colors.color(this.permissionMessage);
	}

}
