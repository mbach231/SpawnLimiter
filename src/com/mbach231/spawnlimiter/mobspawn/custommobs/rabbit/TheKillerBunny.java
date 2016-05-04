package com.mbach231.spawnlimiter.mobspawn.custommobs.rabbit;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class TheKillerBunny extends CustomMob {

    public TheKillerBunny() {
        entityType_ = EntityType.RABBIT;
        name_ = "The Killer Bunny";
        xpScaleAmount_ = 1.0;

    }

    @Override
    public void convertEntity(LivingEntity entity) {
        
        Rabbit rabbit = (Rabbit) entity;
        rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        
        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

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

    @Override
    public void initializeMobDrops() {

    }

}
