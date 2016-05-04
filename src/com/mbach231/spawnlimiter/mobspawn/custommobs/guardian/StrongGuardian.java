package com.mbach231.spawnlimiter.mobspawn.custommobs.guardian;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;



public class StrongGuardian extends CustomMob {

    
    public StrongGuardian() {
        super();
        entityType_ = EntityType.GUARDIAN;
        name_ = "Strong Guardian";
        xpScaleAmount_ = 1.5;
        
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);
        
        EffectFunctions.scaleHealth(entity, 1.5);

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.5);
        EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.SLOW, 8, 0);
    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {
    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {
        
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.PRISMARINE_CRYSTALS, 0, 2));
        customDrops_.add(new MaterialDrop(Material.PRISMARINE_SHARD, 1, 3));
        customDrops_.add(new MaterialDrop(Material.RAW_FISH, 0, 1));
        customDrops_.add(new MaterialDrop(Material.SPONGE, 0, 1));
    }

}
