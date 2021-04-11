package me.Tiernanator.MickTagger.Events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.Tiernanator.MickTagger.Main;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class OnTagEntityDie implements Listener {

	private Main plugin;

	public OnTagEntityDie(Main main) {
		plugin = main;
	}

	@EventHandler
	public void onEntityDie(EntityDamageEvent event) {

		Entity entity = event.getEntity();

		Object isTagSlime = MetaData.getMetadata(entity, "TagEntity", plugin);
		if (isTagSlime == null) {
			return;
		}
		event.setCancelled(true);
	}

}
