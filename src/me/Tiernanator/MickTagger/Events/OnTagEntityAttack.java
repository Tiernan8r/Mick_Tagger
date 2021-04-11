package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class OnTagEntityAttack implements Listener {
	
	private Main plugin;

	public OnTagEntityAttack(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onTagEntityAttack(EntityDamageByEntityEvent event) {
		

		Entity attacker = event.getDamager();

		Object isTagEntity = MetaData.getMetadata(attacker, "TagEntity", plugin);
		if(isTagEntity == null) {
			return;
		} else {
			event.setCancelled(true);
		}

	}
}
