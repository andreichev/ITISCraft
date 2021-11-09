package ru.itis.game.other;

import ru.itis.game.model.Chunk;
import ru.itis.game.model.Voxel;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;
import ru.itis.gengine.renderer.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VoxelMeshGenerator {
    public Mesh generate(Chunk chunk, Shader shader) {
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
                    light = 1.0f;
                    verticesList.add(new Vertex(x, y, z + 1.0f, u, v + uvSize, light));
                    verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 1
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 2
                    verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u, v, light));  // 3
                    // Back
                    light = 0.75f;
                    verticesList.add(new Vertex(x, y, z, u + uvSize, v + uvSize, light)); // 4
                    verticesList.add(new Vertex(x, y + 1.0f, z, u + uvSize, v, light));  // 5
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));   // 6
                    verticesList.add(new Vertex(x + 1.0f, y, z, u, v + uvSize, light)); // 7
                    // Top
                    light = 0.95f;
                    verticesList.add(new Vertex(x, y + 1.0f, z, u, v + uvSize, light)); // 8
                    verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v + uvSize, light));  // 11
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 10
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u, v, light));  // 9
                    // Bottom
                    light = 0.85f;
                    verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 12
                    verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light));  // 13
                    verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u + uvSize, v, light));   // 14
                    verticesList.add(new Vertex(x, y, z + 1.0f, u, v, light));  // 15
                    // Right
                    light = 0.9f;
                    verticesList.add(new Vertex(x, y, z, u, v + uvSize, light)); // 16
                    verticesList.add(new Vertex(x, y, z + 1.0f, u + uvSize, v + uvSize, light));  // 17
                    verticesList.add(new Vertex(x, y + 1.0f, z + 1.0f, u + uvSize, v, light));   // 18
                    verticesList.add(new Vertex(x, y + 1.0f, z, u, v, light));  // 19
                    // Left
                    light = 0.8f;
                    verticesList.add(new Vertex(x + 1.0f, y, z, u + uvSize, v + uvSize, light)); // 20
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z, u + uvSize, v, light));  // 23
                    verticesList.add(new Vertex(x + 1.0f, y + 1.0f, z + 1.0f, u, v, light));   // 22
                    verticesList.add(new Vertex(x + 1.0f, y, z + 1.0f, u, v + uvSize, light));   // 21

                    int indexOffset = verticesList.size();

                    // Front
                    indicesList.add(indexOffset);
                    indicesList.add(indexOffset + 1);
                    indicesList.add(indexOffset + 2);
                    indicesList.add(indexOffset + 2);
                    indicesList.add(indexOffset + 3);
                    indicesList.add(indexOffset);

                    // Back
                    indicesList.add(indexOffset + 4);
                    indicesList.add(indexOffset + 5);
                    indicesList.add(indexOffset + 6);
                    indicesList.add(indexOffset + 6);
                    indicesList.add(indexOffset + 7);
                    indicesList.add(indexOffset + 4);

                    // Top
                    indicesList.add(indexOffset + 8);
                    indicesList.add(indexOffset + 9);
                    indicesList.add(indexOffset + 10);
                    indicesList.add(indexOffset + 10);
                    indicesList.add(indexOffset + 11);
                    indicesList.add(indexOffset + 8);

                    // Bottom
                    indicesList.add(indexOffset + 12);
                    indicesList.add(indexOffset + 13);
                    indicesList.add(indexOffset + 14);
                    indicesList.add(indexOffset + 14);
                    indicesList.add(indexOffset + 15);
                    indicesList.add(indexOffset + 12);

                    // Left
                    indicesList.add(indexOffset + 16);
                    indicesList.add(indexOffset + 17);
                    indicesList.add(indexOffset + 18);
                    indicesList.add(indexOffset + 18);
                    indicesList.add(indexOffset + 19);
                    indicesList.add(indexOffset + 16);

                    // Right
                    indicesList.add(indexOffset + 20);
                    indicesList.add(indexOffset + 21);
                    indicesList.add(indexOffset + 22);
                    indicesList.add(indexOffset + 22);
                    indicesList.add(indexOffset + 23);
                    indicesList.add(indexOffset + 20);
                }
            }
        }
        Texture texture = new Texture("resources/textures/Texture.png");
        int[] indices = new int[indicesList.size()];
        for(int i = 0; i < indicesList.size(); i++) {
            indices[i] = indicesList.get(i);
        }
        Vertex[] vertices = new Vertex[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++) {
            vertices[i] = verticesList.get(i);
        }
        return new Mesh(vertices, indices, texture, shader);
    }
}
