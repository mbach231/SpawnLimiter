package com.mbach231.spawnlimiter.mobspawn.custommobs.skeleton;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.EffectFunctions;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.MaterialDrop;
import com.mbach231.spawnlimiter.mobspawn.custommobs.customdrops.PotionDrop;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class BountyHunterLeader extends CustomMob {

    final double chanceEffects;
    final double chanceSpawnOnHorse;
    final double chanceUndeadHorse;
    final double damageScalar;
    final double chanceEnchant;

    public BountyHunterLeader() {
        entityType_ = EntityType.SKELETON;
        name_ = "Bounty Hunter Leader";
        xpScaleAmount_ = 1.0;

        this.chanceEffects = 0.5;
        this.chanceSpawnOnHorse = 0.75;
        this.chanceUndeadHorse = 0.2;
        this.damageScalar = 1.5;
        this.chanceEnchant = 0.25;
    }

    @Override
    public void convertEntity(LivingEntity entity) {

        entity.setCustomName(name_);
        entity.setCustomNameVisible(false);

        // Set armor 
        ItemStack enchantableArmor;

        enchantableArmor = EffectFunctions.createDyedArmor(Material.LEATHER_HELMET, Color.BLACK);
        if (EffectFunctions.chance(chanceEnchant)) {
            enchantableArmor.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        entity.getEquipment().setHelmet(enchantableArmor);

        enchantableArmor = EffectFunctions.createDyedArmor(Material.LEATHER_CHESTPLATE, Color.BLACK);
        if (EffectFunctions.chance(chanceEnchant)) {
            enchantableArmor.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        entity.getEquipment().setChestplate(enchantableArmor);

        enchantableArmor = EffectFunctions.createDyedArmor(Material.LEATHER_LEGGINGS, Color.BLACK);
        if (EffectFunctions.chance(chanceEnchant)) {
            enchantableArmor.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        entity.getEquipment().setLeggings(enchantableArmor);

        enchantableArmor = EffectFunctions.createDyedArmor(Material.LEATHER_BOOTS, Color.BLACK);
        if (EffectFunctions.chance(chanceEnchant)) {
            enchantableArmor.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        entity.getEquipment().setBoots(enchantableArmor);

        // Set weapon
        ItemStack bow = new ItemStack(Material.BOW, 1);
        if (EffectFunctions.chance(chanceEnchant)) {
            bow.addEnchantment(Enchantment.DURABILITY, 2);
        }
        if (EffectFunctions.chance(chanceEnchant)) {
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        }

        entity.getEquipment().setItemInHand(bow);

        EffectFunctions.scaleHealth(entity, 1.50);

        // If on surface, chance to spawn on horse
        if (EffectFunctions.canSpawnOnMount(entity, chanceSpawnOnHorse)) {
            
            LivingEntity spiderMount = (LivingEntity)entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SPIDER);
            
            spiderMount.setPassenger(entity);
            /*
            Horse horse = (Horse) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.HORSE);

            if (EffectFunctions.chance(chanceUndeadHorse)) {
                horse.setVariant(Horse.Variant.SKELETON_HORSE);
            }

            EffectFunctions.applyRandomHorseArmor(horse);

            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            horse.setTamed(true);
            horse.setPassenger(entity);
            */
        }

    }

    @Override
    public void applyOnAttack(EntityDamageByEntityEvent event) {

        event.setDamage(event.getDamage() * damageScalar);

        if (EffectFunctions.chance(chanceEffects)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.BLINDNESS, 5, 1);
        }

        if (EffectFunctions.chance(chanceEffects)) {
            EffectFunctions.applyPotionEffect(event.getEntity(), PotionEffectType.SLOW, 5, 1);
        }
    }

    @Override
    public void applyOnDamaged(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyOnDeath(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();

        if (entity.isInsideVehicle()) {
            if (entity.getVehicle() instanceof Horse) {
                Location horseLoc = entity.getLocation();
                horseLoc.getWorld().createExplosion(horseLoc, 1f);
                ((Horse) entity.getVehicle()).setHealth(0);
            }
        }
    }

    @Override
    public void initializeMobDrops() {
        customDrops_.add(new MaterialDrop(Material.BONE, 1, 2));
        customDrops_.add(new MaterialDrop(Material.ARROW, 2, 4));
        customDrops_.add(new MaterialDrop(Material.EXP_BOTTLE, 1, 3));

        customDrops_.add(new PotionDrop(PotionType.SLOWNESS, 1, true));
    }

}
