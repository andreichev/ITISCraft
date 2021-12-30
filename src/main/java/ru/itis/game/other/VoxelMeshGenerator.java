package ru.itis.game.other;

import ru.itis.game.model.Chunk;
import ru.itis.game.model.ChunksStorage;
import ru.itis.game.model.Voxel;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.renderer.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VoxelMeshGenerator {
    static final float ambientOcclusionFactor = 0.1f;

    public static MeshData makeOneChunkMesh(ChunksStorage chunks, int chunkIndexX, int chunkIndexY, int chunkIndexZ, boolean ambientOcclusion) {
        Chunk chunk = chunks.chunks[chunkIndexX][chunkIndexY][chunkIndexZ];
        List<Vertex> verticesList = new ArrayList<>();
        List<Integer> indicesList = new ArrayList<>();
        for (int voxelIndexX = 0; voxelIndexX < Chunk.SIZE_X; voxelIndexX++) {
            for (int voxelIndexY = 0; voxelIndexY < Chunk.SIZE_Y; voxelIndexY++) {
                for (int voxelIndexZ = 0; voxelIndexZ < Chunk.SIZE_Z; voxelIndexZ++) {
                    int x = voxelIndexX + chunkIndexX * Chunk.SIZE_X;
                    int y = voxelIndexY + chunkIndexY * Chunk.SIZE_Y;
                    int z = voxelIndexZ + chunkIndexZ * Chunk.SIZE_Z;

                    Voxel currentVoxel = chunk.get(voxelIndexX, voxelIndexY, voxelIndexZ);
                    if(currentVoxel == null || currentVoxel.id == 0) { continue; }

                    // Размер одной текстуры на карте uv
                    float uvSize = 1.f / 16.f;
                    float u = currentVoxel.id % 16.f * uvSize;
                    float v = currentVoxel.id / 16 * uvSize;

                    // Небольшой сдвиг от краев текстуры для того,
                    // чтобы при mipmapping не было сливания с соседними текстурами
                    uvSize -= 0.001f;
                    u += 0.0005f;
                    v += 0.0005f;

                    float light;
                    float a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;

                    // Front
                    if(isAir(x, y, z + 1, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 1.0f;
                        if(ambientOcclusion) {
                            // top
                            a = (isAir(x, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom
                            b = (isAir(x, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left
                            c = (isAir(x + 1, y, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right
                            d = (isAir(x - 1, y, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top left
                            e = (isAir(x + 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top right
                            f = (isAir(x - 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom left
                            g = (isAir(x + 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom right
                            h = (isAir(x - 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v + uvSize, light * (1f - b - d - h)));
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v + uvSize, light * (1f - b - c - g)));  // 1
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light * (1f - a - c - e)));   // 2
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u, v, light * (1f - a - d - f)));  // 3
                    }
                    // Back
                    if(isAir(x, y, z - 1, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.75f;
                        if(ambientOcclusion) {
                            // top
                            a = (isAir(x, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom
                            b = (isAir(x, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right
                            c = (isAir(x - 1, y, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left
                            d = (isAir(x + 1, y, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top right
                            e = (isAir(x - 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom right
                            f = (isAir(x - 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top left
                            g = (isAir(x + 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom left
                            h = (isAir(x + 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x, y, z, u + uvSize, v + uvSize, light * (1f - b - c - f))); // 4
                        verticesList.add(new Vertex(x, y + 1.0f, z, u + uvSize, v, light * (1f - a - c - e)));  // 5
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light * (1f - a - d - g)));   // 6
                        verticesList.add(new Vertex(x + 1.0f, y, z, u, v + uvSize, light * (1f - b - d - h))); // 7
                    }
                    // Top
                    if(isAir(x, y + 1, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.95f;
                        if(ambientOcclusion) {
                            // left
                            a = (isAir(x + 1, y + 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right
                            b = (isAir(x - 1, y + 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // back
                            c = (isAir(x, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // front
                            d = (isAir(x, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left back
                            e = (isAir(x + 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left front
                            f = (isAir(x + 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right back
                            g = (isAir(x - 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right front
                            h = (isAir(x - 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v + uvSize, light * (1f - b - d - h))); // 8
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v + uvSize, light * (1f - b - c - g)));  // 11
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light * (1f - a - c - e)));   // 10
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v,  light * (1f - a - d - f)));  // 9
                    }
                    // Bottom
                    if(isAir(x, y - 1, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.85f;
                        if(ambientOcclusion) {
                            // left
                            a = (isAir(x + 1, y - 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right
                            b = (isAir(x - 1, y - 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // back
                            c = (isAir(x, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // front
                            d = (isAir(x, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left back
                            e = (isAir(x + 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // left front
                            f = (isAir(x + 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right back
                            g = (isAir(x - 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // right front
                            h = (isAir(x - 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light * (1f - b - d - h))); // 12
                        verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light * (1f - a - d - f)));  // 13
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v, light * (1f - a - c - e)));   // 14
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v, light * (1f - b - c - g)));  // 15
                    }
                    // Right
                    if(isAir(x - 1, y, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.9f;
                        if(ambientOcclusion) {
                            // top
                            a = (isAir(x - 1, y + 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom
                            b = (isAir(x - 1, y - 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // front
                            c = (isAir(x - 1, y, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // back
                            d = (isAir(x - 1, y, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top front
                            e = (isAir(x - 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom front
                            f = (isAir(x - 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top back
                            g = (isAir(x - 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom back
                            h = (isAir(x - 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light * (1f - b - d - h))); // 16
                        verticesList.add(new Vertex(x, y, z + 1.0f, u + uvSize, v + uvSize, light * (1f - b - c - f)));  // 17
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v, light * (1f - a - c - e)));   // 18
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v, light * (1f - a - d - g)));  // 19
                    }
                    // Left
                    if(isAir(x + 1, y, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.8f;
                        if(ambientOcclusion) {
                            // top
                            a = (isAir(x + 1, y + 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom
                            b = (isAir(x + 1, y - 1, z, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // front
                            c = (isAir(x + 1, y, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // back
                            d = (isAir(x + 1, y, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top front
                            e = (isAir(x + 1, y + 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // top back
                            f = (isAir(x + 1, y + 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom front
                            g = (isAir(x + 1, y - 1, z + 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                            // bottom back
                            h = (isAir(x + 1, y - 1, z - 1, chunks) ? 0.0f : 1.0f) * ambientOcclusionFactor;
                        }
                        verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light * (1f - b - d - h))); // 20
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u + uvSize, v, light * (1f - a - d - f)));  // 23
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u, v, light * (1f - a - c - e)));   // 22
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u, v + uvSize, light * (1f - b - c - g)));   // 21
                    }
                }
            }
        }
        int[] indices = indicesList.stream().mapToInt(i -> i).toArray();
        Vertex[] vertices = verticesList.toArray(new Vertex[0]);
        return new MeshData(vertices, indices);
    }

    private static void addFaceIndices(int offset, List<Integer> indicesList) {
        indicesList.add(offset);
        indicesList.add(offset + 1);
        indicesList.add(offset + 2);
        indicesList.add(offset + 2);
        indicesList.add(offset + 3);
        indicesList.add(offset);
    }

    private static boolean isAir(int x, int y, int z, ChunksStorage chunks) {
        Voxel voxel = chunks.getVoxel(x, y, z);
        if (voxel == null) { return true; }
        return voxel.id == 0;
    }
}
