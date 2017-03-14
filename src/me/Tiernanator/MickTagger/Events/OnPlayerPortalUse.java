package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.MickTagger.PlayerTag;

public class OnPlayerPortalUse implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public OnPlayerPortalUse(Main main) {
		plugin = main;
	}

	//An event handler that shows the player's faction and group when they log in.
	@EventHandler
	public void onPlayerTeleport(PlayerPortalEvent event) {
		
		// get the player
		Player player = event.getPlayer();
		PlayerTag playerTag = PlayerTag.getPlayerTag(player);
		
		if(playerTag != null) {
			playerTag.remove();
		}
		
		PlayerTag.setUp(player);
	}
	
}
