package com.mbach231.spawnlimiter.mobspawn.custommobs.skeleton;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class BountyHunter extends CustomMob {

    final double chanceBlindness;
    final double chanceSpawnOnHorse;
    final double chanceUndeadHorse;

    public BountyHunter() {
        entityType_ = EntityType.SKELETON;
        name_ = "Bounty Hunter";
        xpScaleAmount_ = 1.0;

        this.chanceBlindness = 0.5;
        this.chanceSpawnOnHorse = 0.25;
        this.chanceUndeadHorse = 0.1;
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        entity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
        entity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
        entity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_LEGGINGS, 1));
        entity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_BOOTS, 1));

        /*
        // If on surface, chance to spawn on horse
        if (EffectFunctions.canSpawnOnHorse(entity, chanceSpawnOnHorse)) {
            Horse horse = (Horse) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.HORSE);

            if (EffectFunctions.chance(chanceUndeadHorse)) {
                horse.setVariant(Horse.Variant.SKELETON_HORSE);
            }

            EffectFunctions.applyRandomHorseArmor(horse);
            
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            horse.setTamed(true);
            horse.setPassenger(entity);

        }
         */
    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {
        if (EffectFunctions.chance(chanceBlindness)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.BLINDNESS, 5, 1);
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
        customDrops_.add(new MaterialDrop(Material.BONE, 1));
        customDrops_.add(new MaterialDrop(Material.ARROW, 1, 3));
    }

}
