package ru.itis.game.other;

import ru.itis.game.model.Chunk;
import ru.itis.game.model.ChunksStorage;
import ru.itis.game.model.Voxel;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.renderer.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VoxelMeshGenerator {
    public static MeshData makeOneChunkMesh(ChunksStorage chunks, int chunkIndexX, int chunkIndexY, int chunkIndexZ) {
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

                    float light;

                    // Front
                    if(isAir(x, y, z + 1, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 1.0f;
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v + uvSize, light));
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 1
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 2
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u, v, light));  // 3
                    }
                    // Back
                    if(isAir(x, y, z - 1, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.75f;
                        verticesList.add(new Vertex(x, y, z, u + uvSize, v + uvSize, light)); // 4
                        verticesList.add(new Vertex(x, y + 1.0f, z, u + uvSize, v, light));  // 5
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));   // 6
                        verticesList.add(new Vertex(x + 1.0f, y, z, u, v + uvSize, light)); // 7
                    }
                    // Top
                    if(isAir(x, y + 1, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.95f;
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v + uvSize, light)); // 8
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v + uvSize, light));  // 11
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 10
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));  // 9
                    }
                    // Bottom
                    if(isAir(x, y - 1, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.85f;
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 12
                        verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light));  // 13
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v, light));   // 14
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v, light));  // 15
                    }
                    // Right
                    if(isAir(x - 1, y, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.9f;
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 16
                        verticesList.add(new Vertex(x, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 17
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 18
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v, light));  // 19
                    }
                    // Left
                    if(isAir(x + 1, y, z, chunks)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.8f;
                        verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light)); // 20
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u + uvSize, v, light));  // 23
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u, v, light));   // 22
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u, v + uvSize, light));   // 21
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
