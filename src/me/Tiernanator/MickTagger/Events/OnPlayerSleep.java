package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.MickTagger.PlayerTag;

public class OnPlayerSleep implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public OnPlayerSleep(Main main) {
		plugin = main;
	}

	//An event handler that shows the player's faction and group when they log in.
	@EventHandler
	public void onPlayerLeaveBed(PlayerBedLeaveEvent event) {
		
		// get the player
//		Player player = event.getPlayer();
//		PlayerTag.setUp(player);
		
//		PlayerTag.setUpDisplayName(player);
	}
	
	@EventHandler
	public void onPlayerEnterBed(PlayerBedEnterEvent event) {
		
		event.setCancelled(true);
		
		// get the player
		Player player = event.getPlayer();
		PlayerTag playerTag = PlayerTag.getPlayerTag(player);
		
		if(playerTag != null) {
			playerTag.remove();
		}
		
		PlayerTag.setUp(player);
		
//		plugin.getServer().broadcastMessage(player.getName() + " slept");
		
		event.setCancelled(false);
		
//		PlayerTag.setUpDisplayName(player);
	}
	
}
