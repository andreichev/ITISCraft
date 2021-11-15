package ru.itis.game.model;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3i;

public class VoxelRaycastData {
    @Nullable
    public Voxel voxel;
    public Vector3i end;
    public Vector3i normal;

    public VoxelRaycastData(Voxel voxel, Vector3i end, Vector3i normal) {
        this.voxel = voxel;
        this.end = end;
        this.normal = normal;
    }
}
