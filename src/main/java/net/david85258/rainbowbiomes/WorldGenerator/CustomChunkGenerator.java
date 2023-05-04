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
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                for (int y = -63; y <=0; y++){
                    if (y < -3) chunkData.setBlock(x, y, z, Material.STONE);
                    else chunkData.setBlock(x, y, z, Material.GRASS_BLOCK);
                }
                if (chunkX == 1 && x == 8){
                    chunkData.setBlock(x, 1, z, Material.OAK_LEAVES);
                } else if (chunkX == 2 && x == 8) {
                    chunkData.setBlock(x, 1, z, Material.WATER);
                }
                for (int y = worldInfo.getMinHeight(); y <= worldInfo.getMaxHeight(); y++){
                    ((CraftChunkData)chunkData).getHandle().setBiome(x, y, z, BiomeManager.registryWriteable.getHolderOrThrow(getKey((chunkX * 16) + x, (chunkZ * 16) + z)));
                }
            }
    }

    @Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                chunkData.setBlock(x, -64, z, Material.BEDROCK);
            }
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
