package nl.justmaffie.chatchannelsplus.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.justmaffie.chatchannelsplus.ChatChannelsPlus;

public class JoinLeaveListener implements Listener {

	private ChatChannelsPlus plugin;

	public JoinLeaveListener(ChatChannelsPlus plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (this.plugin.getMutedChannels().containsKey(player.getUniqueId())) {
			this.plugin.getMutedChannels().remove(player.getUniqueId());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (this.plugin.getMutedChannels().containsKey(player.getUniqueId())) {
			this.plugin.getMutedChannels().remove(player.getUniqueId());
		}
	}

}
