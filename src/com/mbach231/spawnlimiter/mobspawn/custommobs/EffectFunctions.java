package com.mbach231.spawnlimiter.mobspawn.custommobs;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EffectFunctions {

    public static boolean chance(double chance) {
        return Math.random() <= chance;
    }

    public static void applyPotionEffect(Entity entity, PotionEffectType type, int level) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.addPotionEffect(new PotionEffect(type, 20 * 360000, level));
        }
    }

    public static void applyPotionEffect(Entity entity, PotionEffectType type, int time, int level) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.addPotionEffect(new PotionEffect(type, 20 * time, level));
        }
    }

    public static void setHealth(Entity entity, double newHealth) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setMaxHealth(newHealth);
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    public static void scaleHealth(Entity entity, double scale) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setMaxHealth(livingEntity.getMaxHealth() * scale);
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }

    public static void applyKnockUp(Entity entity, double height) {
        entity.setVelocity(new Vector(0, 1.5, 0));
    }

    public static ItemStack createDyedArmor(Material material, Color color) {
        if (material != Material.LEATHER_BOOTS
                && material != Material.LEATHER_CHESTPLATE
                && material != Material.LEATHER_HELMET
                && material != Material.LEATHER_LEGGINGS) {
            return null;
        }

        ItemStack leatherArmor = new ItemStack(material, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) leatherArmor.getItemMeta();
        meta.setColor(color);
        leatherArmor.setItemMeta(meta);
        
        return leatherArmor;
    }
    
    public static boolean canSpawnOnMount(Entity entity, double chance) {
        
        if(chance(chance)) {
            
            System.out.println("canSpawnOnHorse chance met!");
            
            System.out.println("Highest block: " + entity.getWorld().getHighestBlockAt(entity.getLocation()));
            System.out.println("Entity block: " + entity.getLocation().getBlock().getRelative(BlockFace.DOWN).toString());
            
            
           // Block highestBlock = entity.getWorld().getHighestBlockAt(entity.getLocation());
            
            //Block entityBlock = entity.getLocation().getBlock().getRelative(BlockFace.DOWN).toString();
            
            return entity.getWorld().getHighestBlockAt(entity.getLocation()).equals(entity.getLocation().getBlock());
            //return entity.getWorld().getHighestBlockAt(entity.getLocation()).equals(entity.getLocation().getBlock().getRelative(BlockFace.DOWN));
        }
        
        return false;
        

    }
    
    public static void applyRandomHorseArmor(Horse horse) {
        
        double chance = Math.random();
        
        if(chance < 0.1) {
            horse.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING));
        } else if(chance < 0.3) {
            horse.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING));
        } else if(chance < 0.6) {
            horse.getInventory().setArmor(new ItemStack(Material.IRON_BARDING));
        }
    }

}
