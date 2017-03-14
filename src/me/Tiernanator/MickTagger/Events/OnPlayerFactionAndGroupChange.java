package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Factions.FactionEvents.CustomEvents.CustomFactionChangeEvent;
import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.MickTagger.PlayerTag;
import me.Tiernanator.Permissions.Events.CustomEvents.CustomGroupChangeEvent;

public class OnPlayerFactionAndGroupChange implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	
	public OnPlayerFactionAndGroupChange(Main main) {
		plugin = main;
	}

	//An event handler that changes the players display name to reflect their group
	@EventHandler
	public void onPlayerChangGroup(CustomGroupChangeEvent event) {
		
		// get the player
		Player player = event.getPlayer();
		
		PlayerTag playerTag = PlayerTag.getPlayerTag(player);
		
		if(playerTag != null) {
			playerTag.remove();
		}
		
		PlayerTag.setUp(player);
//		PlayerTag.setUpDisplayName(player);
//		
//		// get the player's group
//		Group playerGroup = Group.getPlayerGroup(player);
//		// get the players prefix from their group: it has a colour code so must
//		// be interpreted with the colour
//		// defaulting to white
//		String prefix = MessageColourer.parseMessage(playerGroup.getPrefix(),
//				ChatColor.WHITE);
//		// same with suffix
//		String suffix = MessageColourer.parseMessage(playerGroup.getSuffix(),
//				ChatColor.WHITE);
//		// get the faction
//		Faction playerFaction = Faction.getPlayerFaction(player);
//		String factionPrefix = MessageColourer.parseMessage(playerFaction.getPrefix(), ChatColor.WHITE);
//		String factionSuffix = MessageColourer.parseMessage(playerFaction.getSuffix(), ChatColor.WHITE);
//		// get the faction's specific colour
////		ChatColor factionColour = playerFaction.chatColour();
//		// combine all factors
//		String tagString = prefix + suffix + factionPrefix
//				+ playerFaction.getName() + factionSuffix + ChatColor.WHITE + " "
//				+ player.getName();
//		
//		PlayerTag playerTag = new PlayerTag(player, tagString);
	}
		
	//An event handler that changes the players display name to reflect their faction
	@EventHandler
	public void onPlayerChangFaction(CustomFactionChangeEvent event) {
	
		// get the player
		Player player = event.getPlayer();

		PlayerTag playerTag = PlayerTag.getPlayerTag(player);
		
		if(playerTag != null) {
			playerTag.remove();
		}
		
		PlayerTag.setUp(player);
//		PlayerTag.setUpDisplayName(player);
//		
//		// get the player's group
//		Group playerGroup = Group.getPlayerGroup(player);
//		// get the players prefix from their group: it has a colour code so must
//		// be interpreted with the colour
//		// defaulting to white
//		String prefix = MessageColourer.parseMessage(playerGroup.getPrefix(),
//				ChatColor.WHITE);
//		// same with suffix
//		String suffix = MessageColourer.parseMessage(playerGroup.getSuffix(),
//				ChatColor.WHITE);
//		// get the faction
//		Faction playerFaction = Faction.getPlayerFaction(player);
//		String factionPrefix = MessageColourer.parseMessage(playerFaction.getPrefix(), ChatColor.WHITE);
//		String factionSuffix = MessageColourer.parseMessage(playerFaction.getSuffix(), ChatColor.WHITE);
//		// get the faction's specific colour
////		ChatColor factionColour = playerFaction.chatColour();
//		// combine all factors
//		String tagString = prefix + suffix + factionPrefix
//				+ playerFaction.getName() + factionSuffix + ChatColor.WHITE + " "
//				+ player.getName();
//		
//		PlayerTag playerTag = new PlayerTag(player, tagString);
	}
	
}
