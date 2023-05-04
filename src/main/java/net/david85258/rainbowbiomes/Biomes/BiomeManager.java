package net.david85258.rainbowbiomes.Biomes;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

public class BiomeManager {

    public static final WritableRegistry<Biome> registryWriteable = (WritableRegistry<Biome>) ((CraftServer) Bukkit.getServer()).getServer().registries().compositeAccess().registryOrThrow(Registries.BIOME);

    @SuppressWarnings("JavaReflectionMemberAccess")
    public static int createAndRegistAllBiomes() throws NoSuchFieldException, IllegalAccessException {
        ResourceLocation RESOURCE_LOCATION = new ResourceLocation("rainbowbiomes", "1");
        ResourceKey<Biome> newKey = ResourceKey.create(Registries.BIOME, RESOURCE_LOCATION);
        if (registryWriteable.containsKey(newKey)){
            Bukkit.getConsoleSender().sendMessage(newKey + " was already registered. Was there a plugin/server reload?");
            return 0;
        }

        Bukkit.getConsoleSender().sendMessage("Unfreezing...");
        //Unfreeze Biome Registry
        Field frozen = MappedRegistry.class.getDeclaredField("l");
        frozen.setAccessible(true);
        frozen.set(registryWriteable, false);

        //Inject unregisteredIntrusiveHolders with a new map to allow intrusive holders
        //m is unregisteredIntrusiveHolders
        Field unregisteredIntrusiveHolders = MappedRegistry.class.getDeclaredField("m");
        unregisteredIntrusiveHolders.setAccessible(true);
        unregisteredIntrusiveHolders.set(registryWriteable, new IdentityHashMap<>());

        int r = 255, g = 0, b = 0;
        for (int x = 1; x < Integer.MAX_VALUE; x++) {
            if (x <= 255){
                g++;
            }else if (x <= 255*2){
                r--;
            }else if (x <= 255*3){
                b++;
            }else if (x <= 255*4){
                g--;
            }else if (x <= 255*5){
                r++;
            }else if (x <= 255*6){
                b--;
            }else {
                //Make unregisteredIntrusiveHolders null again to remove potential for undefined behaviour
                unregisteredIntrusiveHolders.set(registryWriteable, null);

                //Refreeze biome registry
                frozen.setAccessible(true);
                frozen.set(registryWriteable, true);
                Bukkit.getConsoleSender().sendMessage("Freezed.");
                return x-1;
            }
            int color = (r << 16) | (g << 8) | b;
            BiomeCreator.createBiome(x, color);
        }

        //Make unregisteredIntrusiveHolders null again to remove potential for undefined behaviour
        unregisteredIntrusiveHolders.set(registryWriteable, null);

        //Refreeze biome registry
        frozen.setAccessible(true);
        frozen.set(registryWriteable, true);
        Bukkit.getConsoleSender().sendMessage("Freezed.");

        return 0;
    }
}
