package com.justdoom.minestomdatasaving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.binary.BinaryReader;
import net.minestom.server.utils.chunk.ChunkCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 5, 1},
        k = 1,
        d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\f\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0006H\u0016J\b\u0010\u0014\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
        d2 = {"LNamedInstanceChunkLoader;", "Lnet/minestom/server/instance/IChunkLoader;", "regionFolder", "", "(Ljava/lang/String;)V", "loadChunk", "", "instance", "Lnet/minestom/server/instance/Instance;", "chunkX", "", "chunkZ", "callback", "Lnet/minestom/server/utils/chunk/ChunkCallback;", "saveChunk", "", "chunk", "Lnet/minestom/server/instance/Chunk;", "Ljava/lang/Runnable;", "supportsParallelLoading", "supportsParallelSaving", "WorldSaving"}
)
public final class NamedInstanceChunkLoader implements IChunkLoader {
    private final String regionFolder;

    public boolean loadChunk(@NotNull Instance instance, int chunkX, int chunkZ, @Nullable ChunkCallback callback) {
        Intrinsics.checkNotNullParameter(instance, "instance");
        (new File(this.regionFolder)).mkdirs();

        boolean var5;
        try {
            FileInputStream is = new FileInputStream(this.regionFolder + "/chunk." + chunkX + '.' + chunkZ + ".save");
            ObjectInputStream ois = new ObjectInputStream((InputStream)is);
            Object var10000 = ois.readObject();
            if (var10000 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.ByteArray");
            }

            byte[] chunkData = (byte[])var10000;
            Chunk var13 = ((InstanceContainer)instance).getChunkSupplier().createChunk(instance, null, chunkX, chunkZ);
            Intrinsics.checkNotNullExpressionValue(var13, "(instance as InstanceCon…ce, null, chunkX, chunkZ)");
            Chunk chunk = var13;
            BinaryReader reader = new BinaryReader(chunkData);
            chunk.readChunk(reader, callback);
            ois.close();
            var5 = true;
        } catch (IOException | ClassNotFoundException var10) {
            var5 = false;
        }

        return var5;
    }

    public void saveChunk(@NotNull Chunk chunk, @Nullable Runnable callback) {
        Intrinsics.checkNotNullParameter(chunk, "chunk");
        int chunkX = chunk.getChunkX();
        int chunkZ = chunk.getChunkZ();
        String fileName = this.regionFolder + "chunk." + chunkX + '.' + chunkZ + ".save";
        (new File(this.regionFolder)).mkdirs();

        try {
            File file = new File(fileName);
            if (file.isFile()) {
                file.delete();
            }

            file.createNewFile();
            FileOutputStream os = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(chunk.getSerializedData());
            oos.close();
        } catch (IOException ignored) {
        }

    }

    public boolean supportsParallelSaving() {
        return true;
    }

    public boolean supportsParallelLoading() {
        return true;
    }

    public NamedInstanceChunkLoader(@NotNull String regionFolder) {
        super();
        Intrinsics.checkNotNullParameter(regionFolder, "regionFolder");
        this.regionFolder = regionFolder;
    }
}
