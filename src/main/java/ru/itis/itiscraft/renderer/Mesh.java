package ru.itis.itiscraft.renderer;

import static org.lwjgl.opengl.GL11.*;

class Mesh {
    private final IndexBuffer indexBuffer;
    private final VertexBuffer vertexBuffer;
    private final Texture texture;

    Mesh(Vertex[] vertices, int[] indices, Texture texture) {
        this.texture = texture;
        indexBuffer = new IndexBuffer(indices);
        vertexBuffer = new VertexBuffer(vertices);
        vertexBuffer.bind();
    }

    void draw() {
        texture.bind();
        indexBuffer.bind();
        vertexBuffer.bind();
        glDrawElements(GL_TRIANGLES, indexBuffer.getSize(), GL_UNSIGNED_INT, 0);
    }

    void delete() {
        texture.delete();
        indexBuffer.delete();
        vertexBuffer.delete();
    }
}
