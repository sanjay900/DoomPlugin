package com.sanjay900.DoomPlugin;
import java.util.UUID;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import org.bukkit.entity.Player;

//I decided to copy this class from another plugin i had created, as i needed cooldowns in my plugin
//to test if events have been spammed, for example so that you can't create a gliched door by spamming the
//open and close key.
/** 
 * 
 * This class represents a Cooldown, that lets you test if something has happened within a given time period.
 * @see #getCooldown(Player, String)
 * @see #setCooldown(Player, String, long)
 * @see #tryCooldown(Player, String, long)
 */
public class Cooldown {
	//A table containing all cooldowns, tied to player UUID's, and a key
    private static Table<UUID, String, Long> cooldowns = HashBasedTable.create();
 
    /**
     * Retrieve the number of milliseconds left until a given {@link cooldown} expires.
     * <p>
     * Check for a negative value to determine if a given {@link cooldown} has expired. <br>
     * A {@link cooldown} that isn't defined will return {@link Long#MIN_VALUE}.
     * @param player - the {@link Player}.
     * @param key - {@link cooldown} to locate.
     * @return Number of milliseconds until the cooldown expires.
     * @see Cooldown
     */
    public static long getCooldown(Player player, String key) {
        return calculateRemainder(cooldowns.get(player.getUniqueId(), key));
    }
 
    /**
     * Update a cooldown for the specified player.
     * @param player - the {@link Player}.
     * @param key - cooldown to update.
     * @param delay - number of milliseconds until the cooldown will expire again.
     * @return The previous number of milliseconds until expiration.
     */
    public static long setCooldown(Player player, String key, long delay) {
        return calculateRemainder(
                cooldowns.put(player.getUniqueId(), key, System.currentTimeMillis() + delay));
    }
 
    /**
     * Determine if a given cooldown has expired. If it has, refresh the cooldown. If not, do nothing.
     * @param player - the {@link Player}.
     * @param key - cooldown to update. 
     * @param delay - number of milliseconds until the cooldown will expire again.
     * @return TRUE if the cooldown was expired/unset and has now been reset, FALSE otherwise.
     */
    public static boolean tryCooldown(Player player, String key, long delay) {
        if (getCooldown(player, key) <= 0) {
            setCooldown(player, key, delay);
            return true;
        }
        return false;
    }
    /**
     * Calculate how long is left on this cooldown
     * @param expireTime - the time in milliseconds this cooldown expires at.
     * @return the time in milliseconds that is left till this countdown expires
     */
    private static long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}