package net.david85258.rainbowbiomes;

import net.david85258.rainbowbiomes.Biomes.BiomeManager;
import net.david85258.rainbowbiomes.WorldGenerator.CustomBiomeProvider;
import net.david85258.rainbowbiomes.WorldGenerator.CustomChunkGenerator;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RainbowBiomes extends JavaPlugin implements Listener {
    World world;

    @Override
    public void onLoad() {
        try {
            log(ChatColor.WHITE + "Se registraron " + BiomeManager.createAndRegistAllBiomes() + " biomas");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        world = new WorldCreator("testRainbowBiomes").generator(new CustomChunkGenerator()).biomeProvider(new CustomBiomeProvider()).createWorld();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
        log(ChatColor.WHITE + this.getName() + " se a inicado correctamente.");
    }

    @Override
    public void onDisable() {
        log(ChatColor.WHITE + this.getName() + "se a desactivado correctamente.");
    }

    public void log(String text){
        Bukkit.getLogger().info(text);
    }

    @EventHandler
    public void a(LeavesDecayEvent e){
        if (world != null && world.equals(e.getBlock().getWorld())) e.setCancelled(true);
    }
}
