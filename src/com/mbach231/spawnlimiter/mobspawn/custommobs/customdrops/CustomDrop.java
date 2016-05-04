/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops;


import org.bukkit.inventory.ItemStack;


/**
 *
 *
 */
public abstract class CustomDrop {

    final int NOT_SET = -1;


    int minDropAmount_;
    int maxDropAmount_;

    abstract ItemStack createItemStack(int amount);


    public ItemStack getRandomizedItemStack() {

        //ItemStack item = new ItemStack(material_, minDropAmount_);
        // If max not set, simply return
        if (maxDropAmount_ == NOT_SET) {
            return createItemStack(minDropAmount_);
        }

        for (int i = minDropAmount_; i < maxDropAmount_; i++) {
            if (Math.random() > 0.5) {

                return createItemStack(i);
            }
        }

        return createItemStack(maxDropAmount_);

    }

}
