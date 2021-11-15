package ru.itis.game.model;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

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

    public void set(int x, int y, int z, byte id) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE_X || y >= SIZE_Y || z >= SIZE_Z)
            return;
        data[x][y][z].id = id;
    }

    @Nullable
    Voxel get(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= SIZE_X || y >= SIZE_Y || z >= SIZE_Z)
            return null;
        return data[x][y][z];
    }

    @Nullable
    public VoxelRaycastData bresenham3D(
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            int maximumDistance
    ) {
        float t = 0.0f;
        int ix = (int) Math.floor(x1);
        int iy = (int) Math.floor(y1);
        int iz = (int) Math.floor(z1);

        float stepx = (x2 > 0.0f) ? 1.0f : -1.0f;
        float stepy = (y2 > 0.0f) ? 1.0f : -1.0f;
        float stepz = (z2 > 0.0f) ? 1.0f : -1.0f;

        float infinity = Float.POSITIVE_INFINITY;

        float txDelta = (x2 == 0.0f) ? infinity : Math.abs(1.0f / x2);
        float tyDelta = (y2 == 0.0f) ? infinity : Math.abs(1.0f / y2);
        float tzDelta = (z2 == 0.0f) ? infinity : Math.abs(1.0f / z2);

        float xdist = (stepx > 0) ? (ix + 1 - x1) : (x1 - ix);
        float ydist = (stepy > 0) ? (iy + 1 - y1) : (y1 - iy);
        float zdist = (stepz > 0) ? (iz + 1 - z1) : (z1 - iz);

        float txMax = (txDelta < infinity) ? txDelta * xdist : infinity;
        float tyMax = (tyDelta < infinity) ? tyDelta * ydist : infinity;
        float tzMax = (tzDelta < infinity) ? tzDelta * zdist : infinity;

        int steppedIndex = -1;

        Vector3i end = new Vector3i();
        Vector3i normal = new Vector3i();

        while (t <= maximumDistance){
            Voxel voxel = get(ix, iy, iz);
            if (voxel != null && voxel.id != 0){
                end.x = ix;
                end.y = iy;
                end.z = iz;

                normal.x = 0;
                normal.y = 0;
                normal.z = 0;
                if (steppedIndex == 0) normal.x = (int) -stepx;
                if (steppedIndex == 1) normal.y = (int) -stepy;
                if (steppedIndex == 2) normal.z = (int) -stepz;
                return new VoxelRaycastData(voxel, end, normal);
            }
            if (txMax < tyMax) {
                if (txMax < tzMax) {
                    ix += stepx;
                    t = txMax;
                    txMax += txDelta;
                    steppedIndex = 0;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            } else {
                if (tyMax < tzMax) {
                    iy += stepy;
                    t = tyMax;
                    tyMax += tyDelta;
                    steppedIndex = 1;
                } else {
                    iz += stepz;
                    t = tzMax;
                    tzMax += tzDelta;
                    steppedIndex = 2;
                }
            }
        }
        return null;
    }
}
