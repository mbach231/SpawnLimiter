package com.mbach231.spawnlimiter.mobspawn.custommobs.slime;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;

public class DevelopedRadioactiveBlob extends CustomMob {

    private final double chanceEffected_;
    
    public DevelopedRadioactiveBlob() {
        super();
        entityType_ = EntityType.SLIME;
        name_ = "Developed Radioactive Blob";
        xpScaleAmount_ = 1.0;
        
        this.chanceEffected_ = 0.75;
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        
        

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 2);
        
        if(EffectFunctions.chance(chanceEffected_)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.CONFUSION, 20, 1);
        }
    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {
        event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 1.2f);
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.SLIME_BALL, 0, 2));
    }

}
