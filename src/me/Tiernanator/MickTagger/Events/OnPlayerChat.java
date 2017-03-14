package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.MickTagger.PlayerTag;

public class OnPlayerChat implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public OnPlayerChat(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		
		PlayerTag playerTag = PlayerTag.getPlayerTag(player);
		if(playerTag == null) {
			return;
		}
		
		playerTag.setTagMessage(event.getMessage(), 8);
		
	}
	
}
