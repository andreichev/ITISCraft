package ru.itis.itiscraft.renderer;

import static org.lwjgl.opengl.GL15.*;

class IndexBuffer {
    private final int id;
    private final int size;

    IndexBuffer(int[] indices) {
        id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
        size = indices.length;
    }

    void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    int getSize() {
        return size;
    }

    void delete() {
        glDeleteBuffers(id);
    }
}
