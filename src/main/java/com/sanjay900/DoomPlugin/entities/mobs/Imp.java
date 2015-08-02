package com.sanjay900.DoomPlugin.entities.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.sanjay900.DoomPlugin.DoomPlugin;
import com.sanjay900.DoomPlugin.attacks.FireballWeapon;
import com.sanjay900.DoomPlugin.attacks.Melee;
/**
 * A Doom Imp.
 * An instance of a {@link DoomEntity}
 * @see #isDead()
 */
public class Imp extends DoomEntity{
		public Imp(DoomPlugin plugin, Location location) {
				super(plugin, location, EntityType.ZOMBIE, "Imp", 20, 9, new FireballWeapon(), new Melee());

	}
		@Override
	public void entityLogic() {
		
	}

}
