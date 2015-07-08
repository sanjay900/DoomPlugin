package com.sanjay900.DoomPlugin.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ConeView {
	   
    /**
    *
    * @param players
    *  List of nearby players
    * @param StartLoc
    *  starting location
    * @param Radius
    *  distance cone travels
    * @param Degrees
    *  angle of cone
    * @param direction
    *  angle in degrees from north
    * @return All players inside the cone
    */
    public static List<Player> getDamagedPlayersInCone(List<Player> players, Location startLoc, int radius, int degrees, int direction) {
        List<Player> newPlayers = new ArrayList<Player>();
       
        int[] startPos = new int[] { (int)startLoc.getX(), (int)startLoc.getZ() };
       
        int[] endA = new int[] { (int)(radius * Math.cos(direction - (degrees/2))), (int)(radius * Math.sin(direction - (degrees/2))) };
        int[] endB = new int[] { (int)(radius * Math.cos(direction + (degrees/2))), (int)(radius * Math.sin(direction + (degrees/2))) };
       
        for(Player p :players) {
            Location l = p.getLocation();
            if (!isPointInCircle(startPos[0], startPos[1], radius, l.getBlockX(), l.getBlockZ())) {
                continue;
            }
            int [] playerVector = getVectorForPoints(startPos[0], startPos[1], l.getBlockX(), l.getBlockZ());
           
            double angle = getAngleBetweenVectors(endA, playerVector) + getAngleBetweenVectors(endB, playerVector);
            if (Math.abs(angle) * 10 - 2 < degrees) {
                newPlayers.add(p);
            }
        }
        return newPlayers;
    }
   
    private static int[] getVectorForPoints(int x1, int y1, int x2, int y2) {
        return new int[] { Math.abs(x2-x1), Math.abs(y2-y1) };
    }
 
    private static boolean isPointInCircle(int cx, int cy, int radius, int px, int py) {
        double dist = (px-cx)^2 + (py-cy)^2;
        return dist < (radius^2);
    }
   
    private static double getAngleBetweenVectors(int[] vector1, int[] vector2){
        return Math.atan2(vector2[1], vector2[0]) - Math.atan2(vector1[1], vector1[0]);
    }
}