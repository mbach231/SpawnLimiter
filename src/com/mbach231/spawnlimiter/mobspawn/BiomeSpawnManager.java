/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob.CustomMobEn;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMobManager;
import com.mbach231.spawnlimiter.random.EntityRandomizer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 *
 */
public class BiomeSpawnManager {

    private final Biome biome_;

    private final EntityRandomizer entityRandomizer_;

    private boolean isEmpty_;

    BiomeSpawnManager(Biome biome) {
        biome_ = biome;
        isEmpty_ = true;

        entityRandomizer_ = new EntityRandomizer();
    }

    public Biome getBiome() {
        return biome_;
    }

    public void addSpawn(SpawnInfo info) {
        entityRandomizer_.addEntity(info);
        isEmpty_ = false;
    }

    public boolean isEmpty() {
        return isEmpty_;
    }

    public void modifySpawnEvent(CreatureSpawnEvent event) {


        // Otherwise cancel creature spawn
        event.setCancelled(true);

        Location location = event.getEntity().getLocation();
        Biome biome = location.getBlock().getBiome();
        int y = location.getBlock().getY();
        int lightLevel = location.getBlock().getLightLevel();

        Object type = entityRandomizer_.getRandomEntity(biome, y, lightLevel);

        // If valid entity chosen
        if (type != null) {

            // If normal entity
            if (type instanceof EntityType) {
                log("Spawning vanilla type: " + location.toString());
                //LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, (EntityType) type);
                location.getWorld().spawnEntity(location, (EntityType) type);
            } // If a custom mob
            else if (type instanceof CustomMobEn) {
                
                log("Spawning custom type: " + location.toString());
                CustomMobEn customMobType = (CustomMobEn) type;
                CustomMob customMob = CustomMobManager.getCustomMob(customMobType);
                
                if(customMob != null) {
                    customMob.spawn(location);
                }
                
            }

        }


    }

    private void log(String string) {
       // Bukkit.getLogger().log(Level.INFO, "[SpawnLimiter::BiomeSpawnManager] {0}", string);
    }

}
