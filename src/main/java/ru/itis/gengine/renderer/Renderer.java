package ru.itis.gengine.renderer;

import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void initialize() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void checkForErrors() {
        RendererErrorsHandler.checkForErrorsAndPrint();
    }

    public void drawIndexed(int indicesCount) {
        glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);
    }

    public void drawLines(int pointsCount) {
        glDrawArrays(GL_LINES, 0, pointsCount);
    }

    public void terminate() {}
}
