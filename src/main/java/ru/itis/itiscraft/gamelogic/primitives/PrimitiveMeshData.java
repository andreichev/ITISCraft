package ru.itis.itiscraft.gamelogic.primitives;

import ru.itis.itiscraft.renderer.Vertex;

public class PrimitiveMeshData {
    private final Vertex[] vertices;
    private final int[] indices;

    public PrimitiveMeshData(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
