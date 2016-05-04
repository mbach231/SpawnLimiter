/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbach231.spawnlimiter;

import com.mbach231.spawnlimiter.mobspawn.MobSpawnManager;
import com.mbach231.spawnlimiter.playerspawn.PlayerSpawnManager;
import com.mbach231.spawnlimiter.playerspawn.PlayerSpawnManager.AreaTypeEn;
import java.io.File;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 *
 */
public class SpawnLimiter extends JavaPlugin {

    File configFile_;
    FileConfiguration config_;

    PlayerSpawnManager playerSpawnManager_;
    MobSpawnManager mobSpawnManager_;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config_ = this.getConfig();
        

        playerSpawnManager_ = new PlayerSpawnManager(config_);
        
        
        mobSpawnManager_ = new MobSpawnManager();
        mobSpawnManager_.loadConfig(config_);
        
        getServer().getPluginManager().registerEvents(playerSpawnManager_, this);
        getServer().getPluginManager().registerEvents(mobSpawnManager_, this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            World world = player.getWorld();

            if (cmd.getName().equalsIgnoreCase("slreload")) {
                playerSpawnManager_.loadConfig(getConfig());
            }

            if (cmd.getName().equalsIgnoreCase("slspawn")) {
                AreaTypeEn areaType;

                String areaTypeStr = args[0];

                if ((areaType = AreaTypeEn.valueOf(args[0].toUpperCase())) != null) {

                    if (areaType == AreaTypeEn.CIRCLE) {

                        if (args.length != 4) {
                            return false;
                        }

                        int x;
                        int z;
                        int radius;

                        try {
                            x = Integer.parseInt(args[1]);
                            z = Integer.parseInt(args[2]);
                            radius = Integer.parseInt(args[3]);

                            playerSpawnManager_.updateSpawnArea(world, x, z, radius);
                            // is an integer!
                        } catch (NumberFormatException e) {
                            return false;
                        }

                    }

                }
            }

        } else {
            sender.sendMessage("Must be player to use this command!");
        }

        return true;
    }

}
