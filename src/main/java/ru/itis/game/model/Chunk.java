package ru.itis.game.model;

public class Chunk {
    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 20;
    public static final int SIZE_Z = 20;
    public final Voxel[][][] data = new Voxel[SIZE_X][SIZE_Y][SIZE_Z];

    public Chunk() {
        for (int x = 0; x < Chunk.SIZE_X; x++) {
            for (int y = 0; y < Chunk.SIZE_Y; y++) {
                for (int z = 0; z < Chunk.SIZE_Z; z++) {
                    byte id;
                    if (y <= (Math.sin(x * 0.3f) * 0.5f + 1.5f) * 5.f) {
                        id = 1;
                    } else {
                        id = 0;
                    }
                    if (y <= 2) {
                        id = 8;
                    }
                    data[x][y][z] = new Voxel(id);
                }
            }
        }
    }
}
