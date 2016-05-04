/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.playerspawn;

import com.mbach231.spawnlimiter.playerspawn.PlayerSpawnManager.AreaTypeEn;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 *
 */
public class PlayerSpawnArea {

    private final int NUM_RANDOM_SPAWN_ATTEMPTS = 1000;

    private World world_;
    private AreaTypeEn areaType_;
    private Set<Material> blacklistSet_;

    private int minX_;
    private int maxX_;
    private int minZ_;
    private int maxZ_;

    private int radius_;
    private int centerX_;
    private int centerZ_;

    PlayerSpawnArea(World world, Set<Material> blacklistSet, int minX, int maxX, int minZ, int maxZ) {
        world_ = world;
        blacklistSet_ = blacklistSet;
        areaType_ = AreaTypeEn.RECTANGLE;
        minX_ = minX;
        maxX_ = maxX;
        minZ_ = minZ;
        maxZ_ = maxZ;
    }

    PlayerSpawnArea(World world, Set<Material> blacklistSet, int radius, int centerX, int centerZ) {
        world_ = world;
        blacklistSet_ = blacklistSet;
        areaType_ = AreaTypeEn.CIRCLE;
        radius_ = radius;
        centerX_ = centerX;
        centerZ_ = centerZ;
    }

    public void updateSpawnPoint(int minX, int maxX, int minZ, int maxZ) {
        areaType_ = AreaTypeEn.RECTANGLE;
        minX_ = minX;
        maxX_ = maxX;
        minZ_ = minZ;
        maxZ_ = maxZ;
    }

    public void updateSpawnPoint(int centerX, int centerZ, int radius) {
        areaType_ = AreaTypeEn.CIRCLE;
        radius_ = radius;
        centerX_ = centerX;
        centerZ_ = centerZ;
    }

    public Location getRandomSpawnPoint() {
        if (areaType_ == AreaTypeEn.CIRCLE) {
            for (int i = 0; i < NUM_RANDOM_SPAWN_ATTEMPTS; i++) {
                int x = (int) (Math.random() * radius_) * (Math.random() > 0.5 ? 1 : -1);
                int z = (int) (Math.random() * Math.sqrt(Math.pow(radius_, 2) - Math.pow(x, 2))) * (Math.random() > 0.5 ? 1 : -1);

                Block spawnLocBlock = world_.getHighestBlockAt(x, z);
                Block belowSpawnLocBlock = spawnLocBlock.getLocation().add(0, -1, 0).getBlock();

                log("Random spawn block: " + spawnLocBlock.getType().name());
                log("Below random spawn block: " + belowSpawnLocBlock.getType().name());
                if (!blacklistSet_.contains(spawnLocBlock.getType())
                        && !blacklistSet_.contains(belowSpawnLocBlock.getType())) {
                    return spawnLocBlock.getLocation();
                }
            }
        } else {
            for (int i = 0; i < NUM_RANDOM_SPAWN_ATTEMPTS; i++) {
                int xDist = maxX_ - minX_;
                int zDist = maxZ_ - minZ_;

                int x = (int) (Math.random() * xDist) + minX_;
                int z = (int) (Math.random() * zDist) + minZ_;

                Block spawnLocBlock = world_.getHighestBlockAt(x, z);
                Block belowSpawnLocBlock = spawnLocBlock.getLocation().add(0, -1, 0).getBlock();;
                log("Random spawn block: " + spawnLocBlock.getType().name());
                log("Below random spawn block: " + belowSpawnLocBlock.getType().name());
                if (!blacklistSet_.contains(spawnLocBlock.getType())
                        && !blacklistSet_.contains(belowSpawnLocBlock.getType())) {
                    return spawnLocBlock.getLocation();
                }
            }
        }

        return world_.getSpawnLocation();
    }

    private void log(String str) {
        System.out.println("PlayerSpawnArea - " + str);
    }

}
