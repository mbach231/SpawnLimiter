/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 *
 *
 */
public class EnchantedItemDrop extends CustomDrop {

    Material material_;
    Enchantment enchantment_;
    int level_;
    
    public EnchantedItemDrop(Material material, Enchantment enchantment, int level) {
        this.minDropAmount_ = 0;
        this.maxDropAmount_ = 1;
        
        this.material_ = material;
        this.enchantment_ = enchantment;
        this.level_ = level;
    }
    
    @Override
    ItemStack createItemStack(int amount) {
        
        if (amount <= 0) {
            return null;
        }
        
        // Regardless of input, always set 1 of the item. There are no enchanted
        // items that can be stacked
        ItemStack item = new ItemStack(material_, 1);
        
        item.addEnchantment(enchantment_, level_);
        
        return item;
    }

}
