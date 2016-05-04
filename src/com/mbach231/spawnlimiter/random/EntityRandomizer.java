/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.random;

import com.mbach231.spawnlimiter.mobspawn.SpawnInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

/**
 *
 *
 */
public class EntityRandomizer<Type> {



    Map<EntityType, Double> chanceMap_;
    EntityType[] typeArray_;

    List<RandomEntry> randomizedList_;
    List<RandomEntry> fullRandomList_;

    boolean randomizerInitialized_;

    double realMax_;
    double currentMax_;

    public EntityRandomizer() {
        chanceMap_ = new HashMap();

        randomizedList_ = new ArrayList();
        fullRandomList_ = new ArrayList();

        randomizerInitialized_ = false;
        realMax_ = 0;
        currentMax_ = 0;

        /*
        typeArray_ = new EntityType[ARRAY_SIZE];
        for (int i = 0; i < typeArray_.length; i++) {
            typeArray_[i] = null;
        }*/
    }

    public void addEntity(EntityType type, double chance) {
        if (!randomizerInitialized_) {
            chanceMap_.put(type, chance);
        }
    }

    public void addEntity(SpawnInfo info) {
        if (!randomizerInitialized_) {
            fullRandomList_.add(new RandomEntry(info, realMax_, realMax_ + info.getChance()));
            realMax_ += info.getChance();
        }
    }


    public Type getRandomEntity(Biome biome, int yPos, int lightLevel) {
        
        // Setup randomizer list
        currentMax_ = 0;
        randomizedList_.clear();
        SpawnInfo<Type> info;
        
        for (RandomEntry entry : fullRandomList_) {
            info = entry.getSpawnInfo();
            
            if(info.canSpawn(biome, yPos, lightLevel)) {
                entry.setPoints(currentMax_, currentMax_ + info.getChance());
                randomizedList_.add(entry);
                currentMax_ += info.getChance();
            }
        }
        
        double rand = Math.random() * currentMax_;
        
        if(rand <= currentMax_) {
            for(RandomEntry entry : randomizedList_) {
                if(entry.inRange(rand)) {
                    info = entry.getSpawnInfo();
                    return info.getType();
                }
            }
        }
        
        return null;
    }

}
