package nl.justmaffie.chatchannelsplus;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.justmaffie.pluginlibrary.utils.Colors;

public class CCPPAPI extends PlaceholderExpansion{
	private ChatChannelsPlus plugin;

	public CCPPAPI(ChatChannelsPlus plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getAuthor() {
		return "JustMaffie";
	}

	@Override
	public String getIdentifier() {
		return "ChatChannelsPlus";
	}

	@Override
	public String getVersion() {
		return this.plugin.getDescription().getVersion();
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		if (player == null) {
 			return null;
		}
		if (identifier.equalsIgnoreCase("channel")) {
			if (!this.plugin.getUserChannels().containsKey(player.getUniqueId())) {
				return Colors.color("&7Default");
			}
			return this.plugin.getUserChannels().get(player.getUniqueId()).getName();
		}
		return null;
	}

}
