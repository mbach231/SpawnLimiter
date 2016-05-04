package com.mbach231.spawnlimiter.mobspawn.custommobs;

import com.mbach231.spawnlimiter.mobspawn.custommobs.CustomMob.CustomMobEn;
import com.mbach231.spawnlimiter.mobspawn.custommobs.cavespider.ForestSpiderMinion;
import com.mbach231.spawnlimiter.mobspawn.custommobs.creeper.IceAnomaly;
import com.mbach231.spawnlimiter.mobspawn.custommobs.guardian.StrongGuardian;
import com.mbach231.spawnlimiter.mobspawn.custommobs.rabbit.TheKillerBunny;
import com.mbach231.spawnlimiter.mobspawn.custommobs.skeleton.BountyHunter;
import com.mbach231.spawnlimiter.mobspawn.custommobs.skeleton.BountyHunterLeader;
import com.mbach231.spawnlimiter.mobspawn.custommobs.slime.DevelopedRadioactiveBlob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.slime.RadioactiveBlob;
import com.mbach231.spawnlimiter.mobspawn.custommobs.spider.ForestSpider;
import com.mbach231.spawnlimiter.mobspawn.custommobs.zombie.Acolyte;
import com.mbach231.spawnlimiter.mobspawn.custommobs.zombie.ExaltedAcolyte;
import com.mbach231.spawnlimiter.mobspawn.custommobs.zombie.Husk;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

public class CustomMobManager {

    private static Map<Entity, CustomMob> entityMap_;
    private static Map<CustomMobEn, CustomMob> customMobMap_;

    // This map exists for the purpose of re-adding mobs to lsit after server restart
    private static Map<String, CustomMob> customMobNameMap_;

    public static void initializeManager() {
        entityMap_ = new ConcurrentHashMap();
        customMobMap_ = new HashMap();

        customMobNameMap_ = new HashMap();
        

        customMobMap_.put(CustomMobEn.BOUNTY_HUNTER, new BountyHunter());
        customMobMap_.put(CustomMobEn.BOUNTY_HUNTER_LEADER, new BountyHunterLeader());
        
        customMobMap_.put(CustomMobEn.FOREST_SPIDER, new ForestSpider());
        customMobMap_.put(CustomMobEn.FOREST_SPIDER_MINION, new ForestSpiderMinion());
        
        customMobMap_.put(CustomMobEn.ICE_ANOMALY, new IceAnomaly());
        
        customMobMap_.put(CustomMobEn.THE_KILLER_BUNNY, new TheKillerBunny());
        
        customMobMap_.put(CustomMobEn.ACOLYTE, new Acolyte());
        customMobMap_.put(CustomMobEn.EXALTED_ACOLYTE, new ExaltedAcolyte());
        customMobMap_.put(CustomMobEn.HUSK, new Husk());
        
        customMobMap_.put(CustomMobEn.RADIOACTIVE_BLOB, new RadioactiveBlob());
        customMobMap_.put(CustomMobEn.DEVELOPED_RADIOACTIVE_BLOB, new DevelopedRadioactiveBlob());
        
        customMobMap_.put(CustomMobEn.STRONG_GUARDIAN, new StrongGuardian());

        for (Map.Entry<CustomMobEn, CustomMob> entry : customMobMap_.entrySet()) {
            customMobNameMap_.put(entry.getValue().getName(), entry.getValue());
        }

    }

    public static void addEntity(Entity entity, CustomMob customMob) {
        entityMap_.put(entity, customMob);
    }

    public static boolean entityIsCustomMob(Entity entity) {

        //System.out.println("entityIsCustomMob: " + customMobNameMap_.containsKey(entity.getCustomName()));
       // return customMobNameMap_.containsKey(entity.getCustomName());

        if (entity instanceof Projectile) {
            Projectile proj = (Projectile) entity;
            if (proj.getShooter() instanceof Entity) {

                //System.out.println("entityIsCustomMob: " + entityMap_.containsKey((Entity) proj.getShooter()));
                return customMobNameMap_.containsKey(((Entity) proj.getShooter()).getCustomName());
            }
        }

        return customMobNameMap_.containsKey(entity.getCustomName());

        /*
         if(entity instanceof Projectile) {
         Projectile proj = (Projectile)entity;
         if(proj.getShooter() instanceof Entity) {
         System.out.println("entityIsCustomMob: projectile was shot by entity!");
                
         System.out.println("entityIsCustomMob: " + entityMap_.containsKey((Entity)proj.getShooter()));
         return entityMap_.containsKey((Entity)proj.getShooter());
         }
         }
        
         System.out.println("entityIsCustomMob: " + entityMap_.containsKey(entity));
        
         return entityMap_.containsKey(entity);
         */
    }

    public static CustomMob getCustomMob(Entity entity) {

        if (entity instanceof Projectile) {
            Projectile proj = (Projectile) entity;
            if (proj.getShooter() instanceof Entity) {
                return customMobNameMap_.get(((Entity) proj.getShooter()).getCustomName());
            }
        }

        return customMobNameMap_.get(entity.getCustomName());
        //return entityMap_.get(entity);
    }

    public static CustomMob getCustomMob(CustomMobEn type) {
        return customMobMap_.get(type);
    }

    public static void removeEntity(Entity entity) {
        entityMap_.remove(entity);
    }

}
