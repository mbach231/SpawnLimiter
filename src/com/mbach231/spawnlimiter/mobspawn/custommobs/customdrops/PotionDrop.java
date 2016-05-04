/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 *
 *
 */
public class PotionDrop extends CustomDrop {

    private final Potion potion_;

    public PotionDrop(PotionType type, int level, boolean splash) {
        this.minDropAmount_ = 0;
        this.maxDropAmount_ = 1;
        
        potion_ = new Potion(type, level);
        potion_.setSplash(splash);
    }

    @Override
    ItemStack createItemStack(int amount) {
        
        if (amount <= 0) {
            return null;
        }

        // Potions not stackable, max of 1 can drop
        ItemStack item = new ItemStack(Material.POTION, 1);
        potion_.apply(item);
        return item;
    }

}
