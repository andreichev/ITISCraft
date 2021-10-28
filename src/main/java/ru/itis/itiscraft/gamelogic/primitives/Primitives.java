package ru.itis.itiscraft.gamelogic.primitives;

import ru.itis.itiscraft.renderer.Vertex;

public class Primitives {
    public static PrimitiveMeshData createCube(float SIZE) {
        Vertex[] vertices = {
                // Front
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 1.0f, 1.f), // 0
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 1.f),  // 1
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 1.f),   // 2
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 1.f),  // 3
                // Back
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.75f), // 4
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.75f),  // 5
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 0.0f, 0.75f),   // 6
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.75f),  // 7
                // Top
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.95f), // 8
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 0.95f),  // 11
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.95f),   // 10
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.95f),  // 9
                // Bottom
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.85f), // 12
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.85f),  // 13
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.85f),   // 14
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 0.85f),  // 15
                // Left
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.9f), // 16
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f, 0.9f),  // 17
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.9f),   // 18
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f, 0.9f),  // 19
                // Right
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f, 0.8f), // 20
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f, 0.8f),  // 23
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f, 0.8f),   // 22
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f, 0.8f)   // 21
        };

        int[] indices = {
                2, 1, 0, 0, 3, 2,       // Front
                6, 5, 4, 4, 7, 6,       // Back
                10, 9, 8, 8, 11, 10,    // Top
                14, 13, 12, 12, 15, 14, // Bottom
                18, 17, 16, 16, 19, 18, // Left
                22, 21, 20, 20, 23, 22  // Right
        };

        return new PrimitiveMeshData(vertices, indices);
    }
}
