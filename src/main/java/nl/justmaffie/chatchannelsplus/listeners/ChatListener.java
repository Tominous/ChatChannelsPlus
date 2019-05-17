package nl.justmaffie.chatchannelsplus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import nl.justmaffie.chatchannelsplus.ChatChannelsPlus;
import nl.justmaffie.chatchannelsplus.Messages;
import nl.justmaffie.chatchannelsplus.api.IChatChannel;

public class ChatListener implements Listener {
	private ChatChannelsPlus plugin;

	public ChatListener(ChatChannelsPlus plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!this.plugin.getUserChannels().containsKey(player.getUniqueId())) {
			return;
		}
		IChatChannel playerChannel = this.plugin.getUserChannels().get(player.getUniqueId());
		if (playerChannel == null) {
			return;
		}
		for (IChatChannel channel : this.plugin.getRegisteredChannels()) {
			if (channel.getId().equalsIgnoreCase(playerChannel.getId())) {
				playerChannel = channel;
			}
		}
		event.setCancelled(true);
		String formattedMessage = playerChannel.formatMessage(player, event.getMessage());
		boolean hasPerm = false;
		for (String permission : playerChannel.getPermissions()) {
			if (player.hasPermission(permission)) {
				hasPerm = true;
			}
		}
		if (!hasPerm) {
			this.plugin.getUserChannels().remove(player.getUniqueId());
			player.sendMessage(
					Messages.LOST_PERM_MOVED_BACK.getMessage().replaceAll("%channel_name%", playerChannel.getName()));
			return;
		}
		for (Player allPlayer : Bukkit.getOnlinePlayers()) {
			boolean hasPermission = false;
			for (String permission : playerChannel.getPermissions()) {
				if (allPlayer.hasPermission(permission)) {
					hasPermission = true;
				}
			}
			if (hasPermission) {
				boolean isMuted = false;
				if (!player.getUniqueId().toString().equalsIgnoreCase(allPlayer.getUniqueId().toString())) {
					if (this.plugin.getMutedChannels().containsKey(allPlayer.getUniqueId())) {
						for (IChatChannel channel2 : this.plugin.getMutedChannels().get(allPlayer.getUniqueId())) {
							if (channel2.getId().equalsIgnoreCase(playerChannel.getId())) {
								isMuted = true;
							}
						}
					}
				}
				if (!isMuted) {
					allPlayer.sendMessage(formattedMessage);
				}
			}
		}

	}
}
