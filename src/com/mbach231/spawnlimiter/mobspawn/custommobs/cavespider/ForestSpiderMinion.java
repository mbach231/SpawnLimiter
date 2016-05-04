package com.mbach231.spawnlimiter.mobspawn.custommobs.cavespider;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMobManager;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;


public class ForestSpiderMinion extends CustomMob {

    private final double chanceSlowness_;
    
    public ForestSpiderMinion() {
        entityType_ = EntityType.CAVE_SPIDER;
        name_ = "Forest Spider Minion";
        xpScaleAmount_ = 1.0;
        
        this.chanceSlowness_ = 0.4;

    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);
        
        EffectFunctions.scaleHealth(entity, 0.75);
       

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.5);
        if(EffectFunctions.chance(chanceSlowness_)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.SLOW, 5, 0);
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
        customDrops_.add(new MaterialDrop(Material.STRING, 1, 2));
        customDrops_.add(new MaterialDrop(Material.WEB, 0, 1));
    }

}
