package ru.itis.itiscraft.gamelogic.primitives;

import ru.itis.itiscraft.renderer.Vertex;

public class Primitives {
    public static PrimitiveMeshData createCube(float SIZE) {
        Vertex[] vertices = {
                // Front
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 1.0f), // 0
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f),  // 1
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f),   // 2
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f),  // 3
                // Back
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f), // 4
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f),  // 5
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 0.0f),   // 6
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f),  // 7
                // Top
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f), // 8
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f),  // 11
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f),   // 10
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f),  // 9
                // Bottom
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f), // 12
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f),  // 13
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f),   // 14
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f),  // 15
                // Left
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f), // 16
                new Vertex(-SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 1.0f, 1.0f),  // 17
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f),   // 18
                new Vertex(-SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 0.0f),  // 19
                // Right
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, -SIZE / 2.0f, 0.0f, 1.0f), // 20
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, -SIZE / 2.0f, 1.0f, 1.0f),  // 23
                new Vertex(SIZE / 2.0f, SIZE / 2.0f, SIZE / 2.0f, 1.0f, 0.0f),   // 22
                new Vertex(SIZE / 2.0f, -SIZE / 2.0f, SIZE / 2.0f, 0.0f, 0.0f)   // 21
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
