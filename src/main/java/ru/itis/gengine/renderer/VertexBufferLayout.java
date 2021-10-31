package ru.itis.gengine.renderer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

class VertexBufferLayout {
    private static class Element {
        int count;
        int type;

        Element(int count, int type) {
            this.count = count;
            this.type = type;
        }

        static int getSizeOfType(int type) {
            switch (type) {
                case GL_FLOAT:
                case GL_UNSIGNED_INT:
                    return 4;
                case GL_UNSIGNED_BYTE:
                    return 1;
            }
            assert(false);
            return 0;
        }
    }

    private final List<Element> elements = new ArrayList<>();
    private int stride = 0;
    private int id;

    void pushFloat(int count) {
        elements.add(new Element(count, GL_FLOAT));
        stride += count * Element.getSizeOfType(GL_FLOAT);
    }

    void pushUnsignedInt(int count) {
        elements.add(new Element(count, GL_UNSIGNED_INT));
        stride += count * Element.getSizeOfType(GL_UNSIGNED_INT);
    }

    void pushUnsignedByte(int count) {
        elements.add(new Element(count, GL_UNSIGNED_BYTE));
        stride += count * Element.getSizeOfType(GL_UNSIGNED_BYTE);
    }

    void initializeForRenderer() {
        id = glGenVertexArrays();
        glBindVertexArray(id);
        int pointer = 0;
        for(int i = 0; i < elements.size(); i++) {
            glEnableVertexAttribArray(i);
            glVertexAttribPointer(i, elements.get(i).count, elements.get(i).type, false, stride, pointer);
            pointer += elements.get(i).count * Element.getSizeOfType(elements.get(i).type);
        }
    }

    void bind() {
        glBindVertexArray(id);
    }

    void delete() {
        glDeleteVertexArrays(id);
    }
}
