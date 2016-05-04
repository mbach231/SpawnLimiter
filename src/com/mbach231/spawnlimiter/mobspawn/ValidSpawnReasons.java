/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbach231.spawnlimiter.mobspawn;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

/**
 *
 * 
 */
public class ValidSpawnReasons {

    public static Set<SpawnReason> spawnReasonSet_;
    
    public static void initializeSpawnReasons() {
        spawnReasonSet_ = new HashSet();
        
        spawnReasonSet_.add(SpawnReason.NATURAL);
        spawnReasonSet_.add(SpawnReason.NETHER_PORTAL);
        spawnReasonSet_.add(SpawnReason.VILLAGE_DEFENSE);
        spawnReasonSet_.add(SpawnReason.VILLAGE_INVASION);
        spawnReasonSet_.add(SpawnReason.CHUNK_GEN);
        
    }
    
    public static boolean validSpawnReason(SpawnReason reason) {
        
        log("Spawn Reason: " + reason.toString());
        
        return spawnReasonSet_.contains(reason);
    }
    
    private static void log(String string) {
       //Bukkit.getLogger().log(Level.INFO, "[SpawnLimiter - ValidSpawnReasons] {0}", string);
    }
}
