package ru.itis.gengine.renderer;

import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private final int id;
    private final VertexBufferLayout layout;

    public VertexBuffer(Vertex[] vertices) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(vertices.length * 6);
            for (Vertex vertex : vertices) {
                buffer
                        .put(vertex.pos.x).put(vertex.pos.y).put(vertex.pos.z)
                        .put(vertex.texCoords.x).put(vertex.texCoords.y)
                        .put(vertex.light);
            }
            buffer.flip();
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        }

        layout = new VertexBufferLayout();
        layout.pushFloat(3);
        layout.pushFloat(2);
        layout.pushFloat(1);
        layout.initializeForRenderer();
    }

    public void bind() {
        layout.bind();
    }

    public void delete() {
        layout.delete();
        glDeleteBuffers(id);
    }
}
