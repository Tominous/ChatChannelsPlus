package nl.justmaffie.chatchannelsplus.api;

import java.util.List;

import org.bukkit.entity.Player;

public interface IChatChannel {

	public String getId();
	
	public String getName();

	public String getFormat();

	public String formatMessage(Player player, String message);

	public List<String> getPermissions();

	public String getPermissionMessage();

}
