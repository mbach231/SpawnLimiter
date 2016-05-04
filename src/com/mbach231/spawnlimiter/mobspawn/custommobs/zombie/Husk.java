package com.mbach231.spawnlimiter.mobspawn.custommobs.zombie;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;


public class Husk extends CustomMob {

    
    public Husk() {
        super();
        entityType_ = EntityType.ZOMBIE;
        name_ = "Husk";
        xpScaleAmount_ = 1.0;
        

    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        EffectFunctions.scaleHealth(entity, 0.75);
        
        entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
        entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE, 1));
        entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS, 1));
        entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
        
        

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.5);
    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {
        event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENDERMAN_HIT, 10, 1);
    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {
        
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.ROTTEN_FLESH, 1, 2));
        customDrops_.add(new MaterialDrop(Material.IRON_INGOT, 0, 1));
        customDrops_.add(new MaterialDrop(Material.REDSTONE, 0, 1));
    }

}
