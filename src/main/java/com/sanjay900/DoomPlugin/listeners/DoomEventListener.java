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
		DoomPlugin plugin;
		public DoomEventListener(DoomPlugin plugin) {
				plugin.getServer().getPluginManager().registerEvents(this, plugin);
				this.plugin = plugin;
	}
	/**Anything with an @EventHandler is automatically called by bukkit, as it is an event.*/
	
		@EventHandler
	public void onFrameBrake(HangingBreakEvent e) {
				if (e.getCause() == RemoveCause.ENTITY && e.getEntity().getType() == EntityType.PAINTING||e.getEntity().getType() == EntityType.ITEM_FRAME) {
						e.setCancelled(true);
		}
	}
				@EventHandler 
	public void onEntityTarget(EntityTargetEvent evt) {

	}
		@EventHandler
	public void onFrameBrake(HangingBreakByEntityEvent e) {
				if (e.getRemover().getType() != EntityType.PLAYER) {
						e.setCancelled(true);
		} else {
						if (!((Player)e.getRemover()).isOp()) {
								e.setCancelled(true);
			}
		}
	}
		@EventHandler
	public void onFrameBrakeByEntity(EntityDamageByEntityEvent event){
				if(event.getDamager() instanceof Projectile && event.getEntity() instanceof Hanging){
						Projectile proj = (Projectile) event.getDamager();
						if(proj instanceof Arrow || proj instanceof Snowball || proj instanceof Fireball){
								event.setCancelled(true);
								proj.remove();
			}
		}
	}
		@EventHandler
	public void playerDamage(EntityDamageEvent evt) {
				if (evt.getEntityType() == EntityType.PLAYER) {
						Player player = (Player)evt.getEntity();
						DoomPlayer pl = plugin.getPlayer(player);
						if (pl!=null) {
																evt.setDamage(plugin.doomDamageCalculator.calculateDamage(evt.getDamage(), pl));
								if (evt.isApplicable(DamageModifier.ARMOR))
										evt.setDamage(DamageModifier.ARMOR, 0);
			}
									resetArmour(player);
			
		}
	}
	/**
	 * Reset a players armour damage to 0.
	 * This means a players armour never runs out.
	 * @param player - the {@link Player}.
	 */
	private void resetArmour(final Player player) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
						@Override
			public void run() {
								ItemStack[] armourContents = player.getInventory().getArmorContents();
								for (ItemStack is: armourContents) {
															if (is != null && is.getType() != Material.AIR) {
												is.setDurability((short)0);
					}
				}
								player.getInventory().setArmorContents(armourContents);
			}
						}, 1L);
		
	}
		@EventHandler
	public void arrow(ProjectileHitEvent event) {
				if (event.getEntityType().equals(EntityType.ARROW)) {
						event.getEntity().remove();
		}
	}

		@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
				if (event.getEntity() instanceof Fireball) {
						event.setCancelled(true);
		}
	}
		@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
				if (event.getEntity() instanceof Fireball) {
			event.setFire(false); 			event.setRadius(1); 		}
	}
		@EventHandler
	public void togglesneak (PlayerToggleSneakEvent e) {
				if (e.isSneaking() && Cooldown.tryCooldown(e.getPlayer(), "door", 1000)) {
			
		}
	}

}
