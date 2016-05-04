package com.mbach231.spawnlimiter.mobspawn.custommobs.slime;

import com.mbach231.spawnlimiter.mobspawn.custommobs.creeper.*;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.PotionDrop;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class RadioactiveBlob extends CustomMob {

    private final double chanceEffected_;
    
    public RadioactiveBlob() {
        super();
        entityType_ = EntityType.SLIME;
        name_ = "Radioactive Blob";
        xpScaleAmount_ = 1.0;
        
        this.chanceEffected_ = 0.5;
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        
        

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.5);
        
        if(EffectFunctions.chance(chanceEffected_)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.CONFUSION, 20, 0);
        }
    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {
        
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.SLIME_BALL, 0, 2));
    }

}
