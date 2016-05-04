/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.playerspawn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 *
 */
public class PlayerSpawnManager implements Listener {

    public enum AreaTypeEn {

        CIRCLE,
        RECTANGLE
    }

    Map<World, PlayerSpawnArea> worldSpawnMap_;

    AreaTypeEn areaType_;

    public PlayerSpawnManager(FileConfiguration config) {

        loadConfig(config);

    }
    
    
    @EventHandler
    public void handlePlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()
                && worldSpawnMap_.containsKey(player.getLocation().getWorld())) {
            player.teleport(worldSpawnMap_.get(player.getLocation().getWorld()).getRandomSpawnPoint());

        }
    }

    @EventHandler
    public void handlePlayerRespawn(PlayerRespawnEvent event) {

        log("Handling player respawn");
        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() != null) {
            return;
        }

        Location respawnLocation = event.getRespawnLocation();

        if (worldSpawnMap_.containsKey(respawnLocation.getWorld())) {
            event.setRespawnLocation(worldSpawnMap_.get(respawnLocation.getWorld()).getRandomSpawnPoint());
        }
    }

    public boolean loadConfig(FileConfiguration config) {
        worldSpawnMap_ = new HashMap();

        int minX;
        int maxX;
        int minZ;
        int maxZ;

        int radius;
        int centerX;
        int centerZ;

        Set<String> worldStrSet = config.getKeys(false);

        if (worldStrSet.isEmpty()) {
            log("worldStrSet empty");
            return false;
        }

        World world;
        /*
         spawn-area:
         area-type: RECTANGLE
         x-min: -5000
         x-max: 5000
         z-min: -5000
         z-max: 5000
         radius: 500
         center-x: 0
         center-z: 0
         */

        for (String worldStr : worldStrSet) {
            world = Bukkit.getWorld(worldStr);

            if (world == null) {
                log("World null");
                continue;
            }

            if (config.contains(worldStr + ".spawn-area")) {
                log("Contains spawn-area");
                if (config.contains(worldStr + ".spawn-area.area-type")) {
                    log("Contains area-type");
                    String areaTypeStr = config.getString(worldStr + ".spawn-area.area-type");

                    if ((areaType_ = AreaTypeEn.valueOf(areaTypeStr.toUpperCase())) == null) {
                        log("areaType_ null");
                        continue;
                    }

                    // Load blacklisted materials
                    Set<Material> blacklistSet = new HashSet();
                    if (config.contains(worldStr + ".spawn-blacklist")) {
                        log("Contains blacklist");
                        //Set<String> blacklistStrSet = config.getConfigurationSection(worldStr + ".spawn-blacklist").getKeys(false);
                        List<String> blacklistStrList = (List<String>) config.getList(worldStr + ".spawn-blacklist");

                        Material blacklistMaterial;
                        for (String blacklistStr : blacklistStrList) {
                            if ((blacklistMaterial = Material.valueOf(blacklistStr)) != null) {
                                log("Adding blacklist material: " + blacklistMaterial.name());
                                blacklistSet.add(blacklistMaterial);
                            }
                        }

                    }

                    if (areaType_ == AreaTypeEn.CIRCLE) {
                        log("Area type CIRCLE");
                        if (config.contains(worldStr + ".spawn-area.radius")
                                && config.contains(worldStr + ".spawn-area.center-x")
                                && config.contains(worldStr + ".spawn-area.center-z")) {
                            radius = config.getInt(worldStr + ".spawn-area.radius");
                            centerX = config.getInt(worldStr + ".spawn-area.center-x");
                            centerZ = config.getInt(worldStr + ".spawn-area.center-z");
                            log("Adding CIRCLE spawn area");
                            worldSpawnMap_.put(world, new PlayerSpawnArea(world, blacklistSet, radius, centerX, centerZ));
                        }
                    } // Area is RECTANGLE
                    else {
                        log("Area type RECTANGLE");

                        if (config.contains(worldStr + ".spawn-area.min-x")) {
                            log("Contains min-x");
                        } else {
                            log("Missing min-x");
                        }
                        if (config.contains(worldStr + ".spawn-area.max-x")) {
                            log("Contains max-x");
                        } else {
                            log("Missing max-x");
                        }
                        if (config.contains(worldStr + ".spawn-area.min-z")) {
                            log("Contains min-z");
                        } else {
                            log("Missing min-z");
                        }
                        if (config.contains(worldStr + ".spawn-area.max-z")) {
                            log("Contains max-z");
                        } else {
                            log("Missing max-z");
                        }

                        if (config.contains(worldStr + ".spawn-area.min-x")
                                && config.contains(worldStr + ".spawn-area.max-x")
                                && config.contains(worldStr + ".spawn-area.min-z")
                                && config.contains(worldStr + ".spawn-area.max-z")) {
                            minX = config.getInt(worldStr + ".spawn-area.min-x");
                            maxX = config.getInt(worldStr + ".spawn-area.max-x");
                            minZ = config.getInt(worldStr + ".spawn-area.min-z");
                            maxZ = config.getInt(worldStr + ".spawn-area.max-z");
                            log("Adding RECTANGLE spawn area");
                            worldSpawnMap_.put(world, new PlayerSpawnArea(world, blacklistSet, minX, maxX, minZ, maxZ));
                        }
                    }

                }
            }
        }

        // If no worlds added, did nothing
        if (worldSpawnMap_.isEmpty()) {
            log("No spawn areas added");
            return false;
        }

        return true;
    }

    public void updateSpawnArea(World world, int minX, int maxX, int minZ, int maxZ) {

        if (worldSpawnMap_.containsKey(world)) {
            PlayerSpawnArea spawnArea = worldSpawnMap_.get(world);
            spawnArea.updateSpawnPoint(minX, maxX, minZ, maxZ);
            worldSpawnMap_.put(world, spawnArea);
        } else {
            worldSpawnMap_.put(world, new PlayerSpawnArea(world, new HashSet(), minX, maxX, minZ, maxZ));
        }

    }

    public void updateSpawnArea(World world, int centerX, int centerZ, int radius) {

        if (worldSpawnMap_.containsKey(world)) {
            PlayerSpawnArea spawnArea = worldSpawnMap_.get(world);
            spawnArea.updateSpawnPoint(centerX, centerZ, radius);
            worldSpawnMap_.put(world, spawnArea);
        } else {
            worldSpawnMap_.put(world, new PlayerSpawnArea(world, new HashSet(), radius, centerX, centerZ));
        }

    }


    private void log(String str) {
        System.out.println("PlayerSpawnManager - " + str);
    }

}
