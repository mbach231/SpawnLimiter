/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn;

import org.bukkit.block.Biome;

/**
 *
 *
 * @param <Type>
 */
public class SpawnInfo<Type> {

    public static final int NOT_SET = -1;

    private final Type type_;
    private final Biome biome_;
    private final double chance_;
    private final int minY_;
    private final int maxY_;
    private final int minLightLevel_;
    private final int maxLightLevel_;

    public SpawnInfo(Type type, Biome biome, double chance) {
        type_ = type;
        biome_ = biome;
        chance_ = chance;
        minY_ = NOT_SET;
        maxY_ = NOT_SET;
        minLightLevel_ = NOT_SET;
        maxLightLevel_ = NOT_SET;
    }

    public SpawnInfo(Type type, Biome biome, double chance, int minY, int maxY) {
        type_ = type;
        biome_ = biome;
        chance_ = chance;
        minY_ = minY;
        maxY_ = maxY;
        minLightLevel_ = NOT_SET;
        maxLightLevel_ = NOT_SET;
    }

    public SpawnInfo(Type type, Biome biome, double chance, int minY, int maxY, int minLightLevel, int maxLightLevel) {
        type_ = type;
        biome_ = biome;
        chance_ = chance;
        minY_ = minY;
        maxY_ = maxY;
        minLightLevel_ = minLightLevel;
        maxLightLevel_ = maxLightLevel;
    }

    public Type getType() {
        return type_;
    }

    public Biome getBiome() {
        return biome_;
    }

    public double getChance() {
        return chance_;
    }

    public int getMinY() {
        return minY_;
    }

    public int getMaxY() {
        return maxY_;
    }

    public int getMinLightLevel() {
        return minLightLevel_;
    }

    public int getMaxLightLevel() {
        return maxLightLevel_;
    }

    public boolean canSpawn(Biome biome, int y, int lightLevel) {

        if (biome.equals(biome_)) {

            if ((minY_ == NOT_SET || y >= minY_)
                    && (maxY_ == NOT_SET || y <= maxY_)) {

                if ((minLightLevel_ == NOT_SET || lightLevel >= minLightLevel_)
                        && (maxLightLevel_ == NOT_SET || lightLevel <= maxLightLevel_)) {

                    return true;
                }
            }
        }

        return false;
    }

}
