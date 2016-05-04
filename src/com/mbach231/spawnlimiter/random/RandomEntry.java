/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.random;

import com.mbach231.spawnlimiter.mobspawn.SpawnInfo;

/**
 *
 *
 */
public class RandomEntry {

    double start_;
    double end_;
    SpawnInfo info_;

    public RandomEntry(SpawnInfo info, double start, double end) {
        this.info_ = info;
        this.start_ = start;
        this.end_ = end;
    }

    public boolean inRange(double n) {
        return (n >= start_ && n <= end_);
    }
    
    public SpawnInfo getSpawnInfo() {
        return info_;
    }
    
    public void setPoints(double start, double end) {
        start_ = start;
        end_ = end;
    }

}
