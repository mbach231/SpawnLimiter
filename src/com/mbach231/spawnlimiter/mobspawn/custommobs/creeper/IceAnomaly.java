package com.mbach231.spawnlimiter.mobspawn.custommobs.creeper;

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

public class IceAnomaly extends CustomMob {

    public IceAnomaly() {
        super();
        entityType_ = EntityType.CREEPER;
        name_ = "Ice Anamoly";
        xpScaleAmount_ = 1.75;
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);
        
        

        EffectFunctions.applyPotionEffect(entity, PotionEffectType.INVISIBILITY, 0);
        EffectFunctions.scaleHealth(entity, 2);

        ((Creeper) entity).setPowered(true);
        
        

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
        customDrops_.add(new MaterialDrop(Material.SULPHUR, 1, 3));
        customDrops_.add(new PotionDrop(PotionType.INVISIBILITY, 1, false));
    }

}
