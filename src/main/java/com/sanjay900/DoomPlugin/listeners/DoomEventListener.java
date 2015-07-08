package com.sanjay900.DoomPlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import com.sanjay900.DoomPlugin.Cooldown;
import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.player.DoomPlayer;

/**
 * This class handles listening to events thrown by bukkit. These range from a player moving to TNT exploding. 
 */
public class DoomEventListener implements Listener {
	//Store an instance of the plugin
	DoomPlugin plugin;
	//Create an instance of this class, call with a reference to the DoomPlugin class.
	public DoomEventListener(DoomPlugin plugin) {
		//Tell bukkit that this class handles events thrown by bukkit
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		//store the called instance in our plugin variable
		this.plugin = plugin;
	}
	/**Anything with an @EventHandler is automatically called by bukkit, as it is an event.*/
	
	//Stop ITEM_FRAMES or PAINTINGS being broken by players or guns
	@EventHandler
	public void onFrameBrake(HangingBreakEvent e) {
		//Was the Hanging entity removed by another entity?
		if (e.getCause() == RemoveCause.ENTITY && e.getEntity().getType() == EntityType.PAINTING||e.getEntity().getType() == EntityType.ITEM_FRAME) {
			//It was, but cancel the event so it doesn't break.
			e.setCancelled(true);
		}
	}
	//this event is called when a entity targets another
	//Used to make entities that see players shoot fireballs at them,
	//Without delving into NMS(net.minecraft.server) and creating a custom entity.
	@EventHandler 
	public void onEntityTarget(EntityTargetEvent evt) {

	}
	//Stop ITEM_FRAMES or PAINTINGS being broken by players
	@EventHandler
	public void onFrameBrake(HangingBreakByEntityEvent e) {
		//Did a player break this?
		if (e.getRemover().getType() != EntityType.PLAYER) {
			//No, so don't let the Hanging entity break
			e.setCancelled(true);
		} else {
			//Yes, are they an Operator?
			if (!((Player)e.getRemover()).isOp()) {
				//No? don't let them break hanging entities
				e.setCancelled(true);
			}
		}
	}
	//Stop projectiles damaging paintings or itemframes
	@EventHandler
	public void onFrameBrakeByEntity(EntityDamageByEntityEvent event){
		//Was the damaging entity a projectile? Is the damaged entity a hanging entity?
		if(event.getDamager() instanceof Projectile && event.getEntity() instanceof Hanging){
			//Yes, get a Projectile based version of the damaging entity 
			Projectile proj = (Projectile) event.getDamager();
			//Is this projectile an Arrow or Snowball or a Fireball?
			if(proj instanceof Arrow || proj instanceof Snowball || proj instanceof Fireball){
				//yes, don't damage the hanging entity
				event.setCancelled(true);
				//kill the entity so it doesn't stick to the painting, mainly for arrows.
				proj.remove();
			}
		}
	}
	//Calculate the damage to a player when they are hit
	@EventHandler
	public void playerDamage(EntityDamageEvent evt) {
		//this even is called when any entity is damaged. We only want to calculate if a player is hit.
		if (evt.getEntityType() == EntityType.PLAYER) {
			//Cast from the Entity object to a player object;
			Player player = (Player)evt.getEntity();
			//Get a version of the doom player object for this player
			DoomPlayer pl = plugin.getPlayer(player);
			//If the doom player is null, they aren't playing.
			if (pl!=null) {
				//The doom player isn't null, so this player is playing doom.
				//Calculate the damage with respect to doom armour and then damage the player with the 
				//new values.
				evt.setDamage(plugin.doomDamageCalculator.calculateDamage(evt.getDamage(), pl));
				//Is the player affected by an armour value?
				if (evt.isApplicable(DamageModifier.ARMOR))
					//They are, set their Armour Scale to 0 so that armour isnt taken into account
					evt.setDamage(DamageModifier.ARMOR, 0);
			}
			//call the reset armour function, which takes care of making sure a players armour
			//doesn't wear out because of minecraft's calculations
			resetArmour(player);
			
		}
	}
	/**
	 * Reset a players armour damage to 0.
	 * This means a players armour never runs out.
	 * @param player - the {@link Player}.
	 */
	private void resetArmour(final Player player) {
		//After 1 tick, after all the damage is done, reset the armour values
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
			//This function is automatically called by bukkit.
			@Override
			public void run() {
				//Get all the contents of a players armour slots in an array
				ItemStack[] armourContents = player.getInventory().getArmorContents();
				//Loop through all the armour
				for (ItemStack is: armourContents) {
					//If the itemstack is null, or has the type of air
					//It is an empty slot.
					if (is != null && is.getType() != Material.AIR) {
						//This slot isn't empty. Reset its damage counter. 
						is.setDurability((short)0);
					}
				}
				//Set the players armour contents to our changed version.
				player.getInventory().setArmorContents(armourContents);
			}
			//Tell bukkit to call the above function after one tick.
			}, 1L);
		
	}
	//Remove arrows stuck in entities
	@EventHandler
	public void arrow(ProjectileHitEvent event) {
		//Is this entity an arrow?
		if (event.getEntityType().equals(EntityType.ARROW)) {
			//Remove this arrow, otherwise it will stay stuck in a wall or an entity
			event.getEntity().remove();
		}
	}

	//Stop fireballs causing damage
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		//Is this a fireball thats attempting to explode?
		if (event.getEntity() instanceof Fireball) {
			//Yes it is, remove block damage.
			event.setCancelled(true);
		}
	}
	//Change fireball entity damage radius
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		//Is this a fireball thats attempting to explode?
		if (event.getEntity() instanceof Fireball) {
			event.setFire(false); //Don't set the world on fire
			event.setRadius(1); //Hurt less entities around the fireball
		}
	}
	//When you sneak, call the scripting plugin and open a door. Might be replaced by an actual plugin later though.
	@EventHandler
	public void togglesneak (PlayerToggleSneakEvent e) {
		//Check the player is sneaking, and they aren't spamming the sneak key
		if (e.isSneaking() && Cooldown.tryCooldown(e.getPlayer(), "door", 1000)) {
			
		}
	}

}
