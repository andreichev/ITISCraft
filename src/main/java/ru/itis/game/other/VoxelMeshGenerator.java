package ru.itis.game.other;

import ru.itis.game.model.Chunk;
import ru.itis.game.model.Voxel;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.renderer.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VoxelMeshGenerator {
    public static MeshData generate(Chunk chunk) {
        List<Vertex> verticesList = new ArrayList<>();
        List<Integer> indicesList = new ArrayList<>();
        for (int x = 0; x < Chunk.SIZE_X; x++) {
            for (int y = 0; y < Chunk.SIZE_Y; y++) {
                for (int z = 0; z < Chunk.SIZE_Z; z++) {
                    Voxel currentVoxel = chunk.data[x][y][z];
                    if(currentVoxel.id == 0) { continue; }

                    // Размер одной текстуры на карте uv
                    float uvSize = 1.f / 16.f;
                    float u = currentVoxel.id % 16.f * uvSize;
                    float v = currentVoxel.id / 16 * uvSize;

                    float light;

                    // Front
                    if(isAir(x, y, z + 1, chunk)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 1.0f;
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v + uvSize, light));
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 1
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 2
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u, v, light));  // 3
                    }
                    // Back
                    if(isAir(x, y, z - 1, chunk)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.75f;
                        verticesList.add(new Vertex(x, y, z, u + uvSize, v + uvSize, light)); // 4
                        verticesList.add(new Vertex(x, y + 1.0f, z, u + uvSize, v, light));  // 5
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));   // 6
                        verticesList.add(new Vertex(x + 1.0f, y, z, u, v + uvSize, light)); // 7
                    }
                    // Top
                    if(isAir(x, y + 1, z, chunk)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.95f;
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v + uvSize, light)); // 8
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v + uvSize, light));  // 11
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 10
                        verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));  // 9
                    }
                    // Bottom
                    if(isAir(x, y - 1, z, chunk)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.85f;
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 12
                        verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light));  // 13
                        verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v, light));   // 14
                        verticesList.add(new Vertex(x, y, z + 1.0f, u, v, light));  // 15
                    }
                    // Right
                    if(isAir(x - 1, y, z, chunk)) {
                        addFaceIndices(verticesList.size(), indicesList);
                        light = 0.9f;
                        verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 16
                        verticesList.add(new Vertex(x, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 17
                        verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 18
                        verticesList.add(new Vertex(x, y + 1.0f, z, u, v, light));  // 19
                    }
                    // Left
                    if(isAir(x + 1, y, z, chunk)) {
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
        int[] indices = new int[indicesList.size()];
        for(int i = 0; i < indicesList.size(); i++) {
            indices[i] = indicesList.get(i);
        }
        Vertex[] vertices = new Vertex[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++) {
            vertices[i] = verticesList.get(i);
        }
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

    private static boolean isAir(int x, int y, int z, Chunk chunk) {
        if(x < 0 || y < 0 || z < 0) { return true; }
        if(x >= chunk.data.length) { return true; }
        if(y >= chunk.data[x].length) { return true; }
        if(z >= chunk.data[x][y].length) { return true; }
        return chunk.data[x][y][z].id == 0;
    }
}
