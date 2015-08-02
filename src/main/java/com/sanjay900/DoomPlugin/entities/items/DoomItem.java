package com.sanjay900.DoomPlugin.entities.items;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.CSUtility;

public class DoomItem {
			public ItemStack itemStack;
		public Location location;
		public Item item = null;
		public DoomItem (ItemStack itemStack, Location loc) {
		this.itemStack = itemStack;
		this.location = loc;
	}
		public void spawnItem() {
				if (item == null)
						item = location.getWorld().dropItem(location, itemStack);
	}
		public void despawnItem() {
				if (item != null)
						item.remove();
				item = null;

	}
		public String itemName() {
				String weaponTitle = new CSUtility().getWeaponTitle(itemStack);
				if (weaponTitle!= null)
			return weaponTitle;
				return itemStack.getType().name()+":"+String.valueOf(itemStack.getAmount())+":"+String.valueOf(itemStack.getDurability());
	}
}
