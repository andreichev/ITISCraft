package ru.itis.gengine.renderer;

import static org.lwjgl.opengl.GL15.*;

public class IndexBuffer {
    private final int id;
    private final int size;

    public IndexBuffer(int[] indices) {
        id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        size = indices.length;
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public int getSize() {
        return size;
    }

    public void delete() {
        glDeleteBuffers(id);
    }
}
