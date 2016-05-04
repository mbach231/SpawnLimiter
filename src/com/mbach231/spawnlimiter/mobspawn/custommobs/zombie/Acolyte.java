package com.mbach231.spawnlimiter.mobspawn.custommobs.zombie;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import org.bukkit.Color;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;



public class Acolyte extends CustomMob {

    
    public Acolyte() {
        super();
        entityType_ = EntityType.ZOMBIE;
        name_ = "Acolyte";
        xpScaleAmount_ = 1.0;
        

    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        entity.getEquipment().setChestplate(EffectFunctions.createDyedArmor(Material.LEATHER_CHESTPLATE, Color.BLACK));

        
        ((Zombie)entity).setVillager(true);

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
        customDrops_.add(new MaterialDrop(Material.ROTTEN_FLESH, 1, 2));
        customDrops_.add(new MaterialDrop(Material.PUMPKIN_SEEDS, 0, 1));
    }

}
