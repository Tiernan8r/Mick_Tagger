package me.Tiernanator.MickTagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Colours.MessageColourer;
import me.Tiernanator.Factions.Factions.Faction;
import me.Tiernanator.Factions.Factions.FactionAccessor;
import me.Tiernanator.Packets.Packet;
import me.Tiernanator.Permissions.Group.Group;
import me.Tiernanator.Permissions.Group.GroupAccessor;
import me.Tiernanator.Utilities.MetaData.MetaData;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;

public class PlayerTag extends BukkitRunnable {
 /*
  * Want each player to have a tag, when chat, changes armourstand name, changes back after set time.
  * Sets up on player enter and removes on leave
  * 
  * Needs default text
  * Add text
  * get player
  * remove
  * setup
  * 
  * handler for displaying chat for 10 seconds
  * 
  */
	private String tagText;
	private String defaultText;
	private Player player;
	private List<Entity> tagEntities;
	private int duration;
	
	public PlayerTag(Player player, String tagText) {
		this.player = player;
		this.tagText = tagText;
		this.defaultText = tagText;
		this.duration = -1;
		setTag(player, tagText);
	}
	
	private static Main plugin;
	public PlayerTag(Main main) {
		plugin = main;
	}

	public String getTagText() {
		return this.tagText;
	}
	
	public void setTagText(String text) {
		this.tagText = text;
		List<Entity> tagEntities = getTagEntities();
		ArmorStand armourStand = (ArmorStand) tagEntities.get(tagEntities.size() - 1);
		armourStand.setCustomName(text);
	}
	
	public String getDefaultText() {
		return this.defaultText;
	}
	
	public void setDefaultText(String text) {
		this.defaultText = text;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public List<Entity> getTagEntities() {
		return this.tagEntities;
	}
	
	public void setTagEntities(List<Entity> tagEntities) {
		this.tagEntities = tagEntities;
	}
	
	public int getTagDuration() {
		return this.duration;
	}
	
	public void setTagMessage(String message, int duration) {
		setTagDuration(duration);
		setTagText(message);
	}
	
	public void setTagDuration(int duration) {
		
		if(duration < 0) {
			duration = -1;
		}
		this.duration = duration;
	}
	
//	public static void setPlugin(Main main) {
//		plugin = main;
//	}
	
	@SuppressWarnings("deprecation")
	private void setTag(Player player, String tagText) {
		
		//get the player's location
		Location location = player.getLocation();

		//Potion effects to make the silverfish(ies) invisible and invunerable
		PotionEffect invisibility = new PotionEffect(
				PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false,
				false);
		PotionEffect invincibility = new PotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, false,
				false);
		
		//The reference ist for all entities used to create the tag
		List<Entity> textEntities = new ArrayList<Entity>();
		//Only one silverfish is used
		int numberSubEntities = 1;
		//The total number of entities is #silverfish + one armourstand,
		//The array stores their entity ids for use in a packet later
		int[] idArray = new int[numberSubEntities + 1];
		//Iterate over the number of silverfish needed
		for (int i = 0; i < numberSubEntities; i++) {

			//Create an entity
			Entity entity = player.getWorld().spawnEntity(location,
					EntityType.CHICKEN);
			Chicken chicken = (Chicken) entity;
			chicken.setAgeLock(true);
			chicken.setAge(-1);
			chicken.setBreed(false);
			chicken.setCanPickupItems(false);
			chicken.setPortalCooldown(0);

//			LivingEntity livingEntity = (LivingEntity) entity;
			chicken.setCollidable(false);
			chicken.setAI(false);
			chicken.addPotionEffect(invisibility, true);
			chicken.addPotionEffect(invincibility, true);
			
			//Add it to the tageEntity array
			textEntities.add(entity);
			//Give it the metadata so we can tell it is used for a tag and is not naturally spawned
			MetaData.setMetadata(entity, "TagEntity", true, plugin);
			//Add it's id to the id array
			idArray[i] = entity.getEntityId();

		}
		//generate an armourstand at the player
		ArmorStand armourStand = (ArmorStand) location.getWorld()
				.spawnEntity(location, EntityType.ARMOR_STAND);
		armourStand.setVisible(false);
		armourStand.setCustomName(getTagText());
		armourStand.setCustomNameVisible(true);
		armourStand.setInvulnerable(true);
		armourStand.setMarker(true);
		armourStand.setCollidable(false);
		//Add it to the tagEntity array aswell, it is in the last slot
		textEntities.add(armourStand);
		//Also add it's id to the id array
		idArray[numberSubEntities] = armourStand.getEntityId();

		//Iterate over all the text entites
		for (Entity entity : textEntities) {

			//set all the tag entities to be invulnerable, floating, and silent
			entity.setGravity(false);
			entity.setInvulnerable(true);
			entity.setSilent(true);
		}
		//Save the list of the entities
		setTagEntities(textEntities);
		
		//A tiered passenger applier, where the silverfish rides the player
		//add the armourstand rides the silverfish
		player.setPassenger(textEntities.get(0));
		for (int i = 0; i < textEntities.size() - 1; i++) {
			textEntities.get(i).setPassenger(textEntities.get(i + 1));
		}
		//Save this tag to the player for later reference
		MetaData.setMetadata(player, "PlayerTag", this, plugin);

		//This is to fix a display error where the client doesn't display their own tag properly
//		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
//		PacketPlayOutMount packet = new PacketPlayOutMount(entityPlayer);
//		Packet.sendPacket(player, packet);
		//Create a visual scenario where the player can't see their own name tag
		PacketPlayOutEntityDestroy destroyEntity = new PacketPlayOutEntityDestroy(
				idArray);
		Packet.sendPacket(player, destroyEntity);
		
	}
	
	public static PlayerTag getPlayerTag(Player player) {
		return (PlayerTag) MetaData.getMetadata(player, "PlayerTag", plugin);
	}
	
	public void resetTagText() {

		List<Entity> tagEntities = getTagEntities();
		ArmorStand armourStand = (ArmorStand) tagEntities.get(tagEntities.size() - 1);
		armourStand.setCustomName(getDefaultText());
		setTagDuration(-1);
		
	}
	
	//Each time a player chats, the message stays for 10 seconds in the chat window
	/**
	 * This function is called consecutively by the server and just changes the player's tag name, based
	 * off of durations
	 */
	@Override
	public void run() {
		
		Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
		for(Player player : onlinePlayers) {
			
			PlayerTag playerTag = getPlayerTag(player);
			if(playerTag == null) {
				continue;
			}
			int duration = playerTag.getTagDuration();
			if(duration == -1) {
				continue;
			}
			duration--;
			if(duration == 0) {
				playerTag.resetTagText();
			}
			playerTag.setTagDuration(duration);
//			if(hasTextEntities(player)) {
//				int i = playerEntityTextDuration.get(player);
//				int stayDuration = playerEntityTextStayDuration.get(player);
//				if(stayDuration == -1) {
//					continue;
//				}
//				if(i > stayDuration) {
//					PlayerTag playerTag = (PlayerTag) MetaData.getMetadata(player, "PlayerTag", plugin);
//					playerTag.resetText();
//				} else {
//					incrementTextEntitiesDuration(player);
//				}
//			}
//			
//			
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void setUp(Player player) {
		
		// get the player's group
		GroupAccessor groupAccessor = new GroupAccessor(player);
		Group playerGroup = groupAccessor.getPlayerGroup();
//		Group playerGroup = Group.getPlayerGroup(player);
		// get the players prefix from their group: it has a colour code so must
		// be interpreted with the colour
		// defaulting to white
		String prefix = MessageColourer.parseMessage(playerGroup.getPrefix(),
				ChatColor.WHITE);
		// same with suffix
		String suffix = MessageColourer.parseMessage(playerGroup.getSuffix(),
				ChatColor.WHITE);

		// get the faction
		FactionAccessor factionAccessor = new FactionAccessor(player);
		Faction playerFaction = factionAccessor.getPlayerFaction();
//		Faction playerFaction = Faction.getPlayerFaction(player);
		String factionPrefix = MessageColourer.parseMessage(playerFaction.getPrefix(), ChatColor.WHITE);
		String factionSuffix = MessageColourer.parseMessage(playerFaction.getSuffix(), ChatColor.WHITE);
		// get the faction's specific colour
//		ChatColor factionColour = playerFaction.chatColour();
		// combine all factors
		String tagString = prefix + suffix + factionPrefix
				+ playerFaction.getName() + factionSuffix + ChatColor.WHITE + " "
				+ player.getName();
		
		PlayerTag playerTag = new PlayerTag(player, tagString);
	}
	
	@SuppressWarnings("deprecation")
	public void remove() {
		
		List<Entity> tagEntities = getTagEntities();
		Player player = getPlayer();
		//Setting passenger for an entity that is a passenger, unsets it as passenger...
		player.setPassenger(tagEntities.get(0));
		for (int i = 0; i < tagEntities.size() - 1; i++) {
			tagEntities.get(i).setPassenger(tagEntities.get(i + 1));
		}
		//kill all the entities
		for(Entity entity : tagEntities) {
			entity.remove();
		}
	}
	
}
