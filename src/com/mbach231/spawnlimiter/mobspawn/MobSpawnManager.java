/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob.CustomMobEn;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMobManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 *
 */
public class MobSpawnManager implements Listener {

    Map<World, Map<Biome, BiomeSpawnManager>> worldSpawnManagerMap_;


    public MobSpawnManager() {
        worldSpawnManagerMap_ = new HashMap();
        ValidSpawnReasons.initializeSpawnReasons();
        CustomMobManager.initializeManager();
    }

    @EventHandler
    public void handleCreateSpawnEvent(CreatureSpawnEvent event) {

        log("Handling spawn event: " + event.getEntity().getName() + " , " + event.getSpawnReason().toString());
        

        if (ValidSpawnReasons.validSpawnReason(event.getSpawnReason())) {
            
            LivingEntity entity = event.getEntity();
            World world = entity.getLocation().getWorld();
            Biome biome = entity.getLocation().getBlock().getBiome();
            
            entity.remove();

            if (worldSpawnManagerMap_.containsKey(world)) {
                Map<Biome, BiomeSpawnManager> biomeSpawnManagerMap = worldSpawnManagerMap_.get(world);
                

                if (biomeSpawnManagerMap.containsKey(biome)) {
                    biomeSpawnManagerMap.get(biome).modifySpawnEvent(event);
                }
            }
        }
    }

    public void loadConfig(FileConfiguration config) {

        SpawnInfo info;

        EntityType entityType;
        CustomMobEn customType;
        Biome biome;
        double chance;
        int minY;
        int maxY;
        int minLightLevel;
        int maxLightLevel;
        float earliestTime;
        float latestTime;

        Map<Biome, BiomeSpawnManager> biomeSpawnManagerMap;
        BiomeSpawnManager biomeSpawnManager;

        Set<String> worldStrSet = config.getKeys(false);

        for (String worldStr : worldStrSet) {
            World world = Bukkit.getWorld(worldStr);

            if (world != null) {

                biomeSpawnManagerMap = new HashMap();

                Set<String> biomeStrSet = config.getConfigurationSection(worldStr + ".mobs").getKeys(false);

                if (biomeStrSet != null) {
                    for (String biomeStr : biomeStrSet) {

                        biome = Biome.valueOf(biomeStr);

                        if (biome != null) {

                            biomeSpawnManager = new BiomeSpawnManager(biome);

                            Set<String> entityStrSet = config.getConfigurationSection(worldStr + ".mobs." + biomeStr).getKeys(false);

                            if (entityStrSet != null) {
                                for (String entityStr : entityStrSet) {
                                    
                                    entityType = null;
                                    customType = null;
                                    
                                    try {
                                        entityType = EntityType.valueOf(entityStr);
                                    } catch (IllegalArgumentException exception) {

                                    }

                                    try {
                                        customType = CustomMobEn.valueOf(entityStr);
                                    } catch (IllegalArgumentException exception) {

                                    }

                                    if (entityType != null || customType != null) {

                                        if (config.contains(worldStr + ".mobs." + biomeStr + "." + entityStr + ".chance")) {

                                            chance = config.getDouble(worldStr + ".mobs." + biomeStr + "." + entityStr + ".chance");

                                            if (config.contains(worldStr + ".mobs." + biomeStr + "." + entityStr + ".y-min")) {
                                                minY = config.getInt(worldStr + ".mobs." + biomeStr + "." + entityStr + ".min-y");
                                            } else {
                                                minY = SpawnInfo.NOT_SET;
                                            }

                                            if (config.contains(worldStr + ".mobs." + biomeStr + "." + entityStr + ".y-max")) {
                                                maxY = config.getInt(worldStr + ".mobs." + biomeStr + "." + entityStr + ".max-y");
                                            } else {
                                                maxY = SpawnInfo.NOT_SET;
                                            }

                                            if (config.contains(worldStr + ".mobs." + biomeStr + "." + entityStr + ".light-level-min")) {
                                                minLightLevel = config.getInt(worldStr + ".mobs." + biomeStr + "." + entityStr + ".light-level-min");
                                            } else {
                                                minLightLevel = SpawnInfo.NOT_SET;
                                            }

                                            if (config.contains(worldStr + ".mobs." + biomeStr + "." + entityStr + ".light-level-max")) {
                                                maxLightLevel = config.getInt(worldStr + ".mobs." + biomeStr + "." + entityStr + ".light-level-max");
                                            } else {
                                                maxLightLevel = SpawnInfo.NOT_SET;
                                            }

                                            if (entityType != null) {
                                                info = new SpawnInfo(entityType, biome, chance, minY, maxY, minLightLevel, maxLightLevel);
                                            } else {
                                                info = new SpawnInfo(customType, biome, chance, minY, maxY, minLightLevel, maxLightLevel);
                                            }

                                            biomeSpawnManager.addSpawn(info);
                                            log("Adding MobSpawnInfo - " + biome.name() + " " + entityStr + " " + chance + " " + minY + " " + maxY);

                                        }

                                    }
                                }
                            }

                            if (!biomeSpawnManager.isEmpty()) {
                                biomeSpawnManagerMap.put(biome, biomeSpawnManager);
                            }
                        } // if biome != null
                    } // for each biomeStr
                } // if biomeStrSet != null

                worldSpawnManagerMap_.put(world, biomeSpawnManagerMap);

            } // if world != null
        } // for each worldStr
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (CustomMobManager.entityIsCustomMob(event.getEntity())) {
            //log("CustomMob died!");
            
            CustomMob customMob = CustomMobManager.getCustomMob(event.getEntity());
            
            // Modify mob drops
            event.getDrops().clear();
            Set<ItemStack> customItems = customMob.getMobDrops();
            for(ItemStack item : customItems) {
                event.getDrops().add(item);
            }
            
            // Modify XP drop
            event.setDroppedExp((int) (event.getDroppedExp() * customMob.getXpScalar()));
            
            customMob.applyOnDeath(event);
            CustomMobManager.removeEntity(event.getEntity());
        }
    }

    @EventHandler
    public void entityDamageEntityEvent(EntityDamageByEntityEvent event) {
        log("Entity damage entity event!");
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        
        log("Attacker: " + damager.getName());
        log("Victim: " + damaged.getName());
        
        if (CustomMobManager.entityIsCustomMob(damager)) {
            CustomMobManager.getCustomMob(damager).applyOnAttack(event);
            log("CustomMob attacking entity!");
        }

        if (CustomMobManager.entityIsCustomMob(damaged)) {
            CustomMobManager.getCustomMob(damaged).applyOnDamaged(event);
            log("CustomMob attacked by entity!");
        }
    }

    private void log(String string) {
       // Bukkit.getLogger().log(Level.INFO, "SpawnLimiter - MobSpawnManager] {0}", string);
    }

}
