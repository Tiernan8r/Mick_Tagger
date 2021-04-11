package me.Tiernanator.MickTagger;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import me.Tiernanator.MickTagger.Events.OnPlayerChat;
import me.Tiernanator.MickTagger.Events.OnPlayerDeath;
import me.Tiernanator.MickTagger.Events.OnPlayerFactionAndGroupChange;
import me.Tiernanator.MickTagger.Events.OnPlayerPortalUse;
import me.Tiernanator.MickTagger.Events.OnPlayerServerJoinAndExit;
import me.Tiernanator.MickTagger.Events.OnPlayerSleep;
import me.Tiernanator.MickTagger.Events.OnPlayerTeleport;
import me.Tiernanator.MickTagger.Events.OnTagEntityAttack;
import me.Tiernanator.MickTagger.Events.OnTagEntityDie;

public class Main extends JavaPlugin implements Listener {
		
	@Override
	public void onEnable() {
		
		registerTasks();
		registerEvents();
		
		for(Player player : getServer().getOnlinePlayers()) {
			PlayerTag.setUp(player);
		}
	}

	@Override
	public void onDisable() {
		
		for(Player player : getServer().getOnlinePlayers()) {
			PlayerTag playerTag = PlayerTag.getPlayerTag(player);
			if(playerTag == null) {
				continue;
			}
			playerTag.remove();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void registerTasks() {
		// in runTaskTimer() first number is how long you wait the first time to
		// start it
		// the second is how long between iterations
		BukkitTask tagRenamer = new PlayerTag(this).runTaskTimer(this, 0, 20);

	}
	
	public void registerEvents() {
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new OnPlayerServerJoinAndExit(this), this);
		pm.registerEvents(new OnPlayerFactionAndGroupChange(this), this);
		pm.registerEvents(new OnTagEntityDie(this), this);
		pm.registerEvents(new OnPlayerTeleport(this), this);
		pm.registerEvents(new OnPlayerDeath(this), this);
		pm.registerEvents(new OnTagEntityAttack(this), this);
		pm.registerEvents(new OnPlayerSleep(this), this);
		pm.registerEvents(new OnPlayerPortalUse(this), this);
		pm.registerEvents(new OnPlayerChat(this), this);
		
	}
}
