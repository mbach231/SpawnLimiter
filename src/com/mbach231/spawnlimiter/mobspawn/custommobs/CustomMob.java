package com.mbach231.spawnlimiter.mobspawn.custommobs;

import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.CustomDrop;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

abstract public class CustomMob {

    public CustomMob() {
        this.xpScaleAmount_ = 1.0;
        this.customDrops_ = new HashSet();
        this.mobDrops_ = new HashSet();
        this.initializeMobDrops();
    }

    public enum CustomMobEn {
        
        BOUNTY_HUNTER,
        BOUNTY_HUNTER_LEADER,
        FOREST_SPIDER,
        FOREST_SPIDER_MINION,
        ICE_ANOMALY,
        THE_KILLER_BUNNY,
        RADIOACTIVE_BLOB,
        DEVELOPED_RADIOACTIVE_BLOB,
        ACOLYTE,
        EXALTED_ACOLYTE,
        HUSK,
        STRONG_GUARDIAN
    }

    public double xpScaleAmount_;
    public Set<CustomDrop> customDrops_;
    public Set<ItemStack> mobDrops_;

    public EntityType entityType_;
    public String name_;

    public void spawn(Location location) {
        Entity entity = location.getWorld().spawn(location, entityType_.getEntityClass());
        convertEntity((LivingEntity) entity);

        CustomMobManager.addEntity(entity, this);
    }

    public String getName() {
        return name_;
    }

    public Set<ItemStack> getMobDrops() {

        mobDrops_.clear();

        ItemStack itemDrop;

        for (CustomDrop drop : customDrops_) {

            itemDrop = drop.getRandomizedItemStack();

            if (itemDrop != null) {
                mobDrops_.add(drop.getRandomizedItemStack());
            }

        }

        return mobDrops_;
    }
    
    public double getXpScalar() {
        return xpScaleAmount_;
    }
    
    abstract public void initializeMobDrops();

    abstract public void convertEntity(LivingEntity entity);

    abstract public void applyOnAttack(EntityDamageByEntityEvent event);

    abstract public void applyOnDamaged(EntityDamageByEntityEvent event);

    abstract public void applyOnDeath(EntityDeathEvent event);

    /*
     @Override
     public void convertEntity(LivingEntity entity) {
        
     }

     @Override
     public void applyOnAttack(EntityDamageByEntityEvent event) {
        
     }

     @Override
     public void applyOnDamaged(EntityDamageByEntityEvent event) {
        
     }

     @Override
     public void applyOnDeath(EntityDeathEvent event) {
        
     }
     */
}
