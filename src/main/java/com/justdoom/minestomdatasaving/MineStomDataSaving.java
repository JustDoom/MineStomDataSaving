package com.justdoom.minestomdatasaving;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.storage.systems.FileStorageSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MineStomDataSaving {


    /**
     *
     * @param savePath - path to save the world to
     */
    public static void saveWorld(String savePath){
        MinecraftServer.getStorageManager().defineDefaultStorageSystem(FileStorageSystem::new);

        InstanceContainer instanceContainer
                = MinecraftServer.getInstanceManager().createInstanceContainer(MinecraftServer.getStorageManager().getLocation(savePath));

        instanceContainer.saveChunksToStorage();
    }

    /**
     *
     * @param loadPath - path to load the world from
     * @return - returns the loaded world
     */
    public static InstanceContainer loadWorld(String loadPath){

        return MinecraftServer.getInstanceManager().createInstanceContainer(MinecraftServer.getStorageManager().getLocation(loadPath));
    }

    /**
     *
     * @param directory - the directory to create
     * @throws IOException
     */
    public static void createDirectory(String directory) throws IOException {
        Files.createDirectory(Path.of(directory));
    }

    /**
     *
     * @param directory - the directory the file should be searched for in
     * @param filename - the name of the file
     * @return - returns false if file doesn't exist, true if it does
     */
    public static boolean doesFileExist(String directory, String filename) {
        Path path = Path.of(directory + "/" + filename);
        if (!Files.exists(path)) {
            return false;
        }
        return true;
    }
}