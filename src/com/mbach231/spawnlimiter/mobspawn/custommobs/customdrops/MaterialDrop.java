/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 *
 */
public class MaterialDrop extends CustomDrop {

    private final Material material_;

    public MaterialDrop(Material material, int dropAmount) {
        this.minDropAmount_ = dropAmount;
        this.maxDropAmount_ = NOT_SET;
        this.material_ = material;
    }
    
    public MaterialDrop(Material material, int minDropAmount, int maxDropAmount) {
        this.minDropAmount_ = minDropAmount;
        this.maxDropAmount_ = maxDropAmount;
        this.material_ = material;
    }

    @Override
    ItemStack createItemStack(int amount) {
        if (amount <= 0) {
            return null;
        }
        return new ItemStack(material_, amount);
    }

}
