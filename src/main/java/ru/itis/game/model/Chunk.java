package ru.itis.game.model;

import org.jetbrains.annotations.Nullable;
import ru.itis.gengine.gamelogic.components.Mesh;

public class Chunk {
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20;
    public static final int SIZE_Z = 20;

    public final Voxel[][][] data;
    public Mesh mesh;

    public Chunk() {
        data = new Voxel[SIZE_X][SIZE_Y][SIZE_Z];
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void set(int x, int y, int z, byte id) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE_X || y >= SIZE_Y || z >= SIZE_Z)
            return;
        data[x][y][z].id = id;
    }

    @Nullable
    public Voxel get(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE_X || y >= SIZE_Y || z >= SIZE_Z)
            return null;
        return data[x][y][z];
    }
}
