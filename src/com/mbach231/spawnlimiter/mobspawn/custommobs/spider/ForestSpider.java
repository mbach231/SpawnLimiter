package com.mbach231.spawnlimiter.mobspawn.custommobs.spider;

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

public class ForestSpider extends CustomMob {

    public ForestSpider() {
        entityType_ = EntityType.SPIDER;
        name_ = "Forest Spider";
        xpScaleAmount_ = 1.0;

    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);
        
        EffectFunctions.scaleHealth(entity, 1.5);
       

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {
        Location deathLoc = event.getEntity().getLocation();
        
        // Spawn 2 minions
        CustomMob mob = CustomMobManager.getCustomMob(CustomMobEn.FOREST_SPIDER_MINION);
        mob.spawn(deathLoc);
        mob.spawn(deathLoc);
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.STRING, 1, 2));
        customDrops_.add(new MaterialDrop(Material.WEB, 0, 1));
    }

}
