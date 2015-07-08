package com.sanjay900.DoomPlugin.entities.items;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.CSUtility;

public class DoomItem {
	//Global variables as they are all accessed by other objects. and functions.
	//The itemstack - e.g. the amount, data and type of item
	public ItemStack itemStack;
	//The location to spawn the item
	public Location location;
	//An instance of a currently spawned item - this is null as this item isnt spawned
	public Item item = null;
	//Create the doom item from the supplied arguments.
	public DoomItem (ItemStack itemStack, Location loc) {
		this.itemStack = itemStack;
		this.location = loc;
	}
	//Spawn the item.
	public void spawnItem() {
		//If the item is null, then there is no spawned item. this avoids duplicates
		if (item == null)
			//Spawn the item, there isnt one spawned already.
			item = location.getWorld().dropItem(location, itemStack);
	}
	//Despawn / remove the item
	public void despawnItem() {
		//Check an item exists before trying to despawn it. If it isnt null, it exists.
		if (item != null)
			//Despawn / remove the spawned item
			item.remove();
		//The item isnt spawned, make it null so that it can be spawned again.
		item = null;

	}
	//Get the name of this item for configuration storage
	public String itemName() {
		//Try and get the title of the weapon if it is a CrackShot weapon.
		String weaponTitle = new CSUtility().getWeaponTitle(itemStack);
		//If the weapon title isnt null, this is a legitimate weapon, so return its title to store
		if (weaponTitle!= null)
			return weaponTitle;
		//Since the weapon is null, create an item id based configuration code for saving.
		return itemStack.getType().name()+":"+String.valueOf(itemStack.getAmount())+":"+String.valueOf(itemStack.getDurability());
	}
}
