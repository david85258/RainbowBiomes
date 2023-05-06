package net.david85258.rainbowbiomes.WorldGenerator;

import net.david85258.rainbowbiomes.Biomes.BiomeManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R2.generator.CraftChunkData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class CustomChunkGenerator extends ChunkGenerator {

    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        super.generateSurface(worldInfo, random, chunkX, chunkZ, chunkData);
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = worldInfo.getMinHeight(); y <= worldInfo.getMaxHeight(); y++){
                    ((CraftChunkData)chunkData).getHandle().setBiome(x, y, z, BiomeManager.registryWriteable.getHolderOrThrow(getKey((chunkX * 16) + x, (chunkZ * 16) + z)));
                }
    }

    @Override
    public boolean shouldGenerateNoise() {
        return true;
    }

    @Override
    public boolean shouldGenerateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public boolean shouldGenerateSurface() {
        return true;
    }

    @Override
    public boolean shouldGenerateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return true;
    }

    @Override
    public boolean shouldGenerateDecorations(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return true;
    }

    @Override
    public boolean shouldGenerateMobs(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
        return true;
    }

    private ResourceKey<Biome> getKey(int x, int z){
        ResourceLocation RESOURCE_LOCATION = new ResourceLocation("rainbowbiomes", a(Math.abs(circulo(x, z))+1)+"");
        return ResourceKey.create(Registries.BIOME, RESOURCE_LOCATION);
    }

    private int circulo(int x, int z){
        return (int) Math.sqrt(x*x+z*z);
    }

    private int a(int a){
        if (a > 1530) {
            a = a - 1530;
            return a(a);
        }
        return a;
    }
}
