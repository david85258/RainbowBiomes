package net.david85258.rainbowbiomes.Biomes;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;

public class BiomeCreator {

    public static void createBiome(int id, int color){
        DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();

        ResourceKey<Biome> oldKey = ResourceKey.create(Registries.BIOME, new ResourceLocation("minecraft", "plains"));
        WritableRegistry<Biome> registryWriteable = (WritableRegistry<Biome>) dedicatedServer.registries().compositeAccess().registryOrThrow(Registries.BIOME);
        Biome plainsBiome = registryWriteable.get(oldKey);
        Biome.BiomeBuilder biomeBuilder = new Biome.BiomeBuilder();
        biomeBuilder.precipitation(plainsBiome.getPrecipitation());
        biomeBuilder.mobSpawnSettings(plainsBiome.getMobSettings());
        MobSpawnSettings.Builder settings = new MobSpawnSettings.Builder();
        settings.addMobCharge(EntityType.BLAZE, 5, 5);
        settings.creatureGenerationProbability(100);
        biomeBuilder.generationSettings(plainsBiome.getGenerationSettings());
        biomeBuilder.temperature(0.7F);
        biomeBuilder.downfall(0.8F);
        BiomeSpecialEffects.Builder effectBuilder = new BiomeSpecialEffects.Builder();
        effectBuilder.grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE);
        effectBuilder.fogColor(color);
        effectBuilder.waterColor(color);
        effectBuilder.waterFogColor(color);
        effectBuilder.skyColor(color);
        effectBuilder.foliageColorOverride(color);
        effectBuilder.grassColorOverride(color);
        biomeBuilder.specialEffects(effectBuilder.build());
        ResourceLocation RESOURCE_LOCATION = new ResourceLocation("rainbowbiomes", id+"");
        ResourceKey<Biome> newKey = ResourceKey.create(Registries.BIOME, RESOURCE_LOCATION);
        Biome biome = biomeBuilder.build();
        registryWriteable.createIntrusiveHolder(biome);
        registryWriteable.register(newKey, biome, Lifecycle.stable());
    }
}
