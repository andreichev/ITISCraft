package ru.itis.itiscraft.renderer;

import org.lwjgl.opengl.GL;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private Shader baseShader;
    private Mesh mesh;

    private Matrix4f view = new Matrix4f();
    private Matrix4f model = new Matrix4f();
    private Matrix4f projection = new Matrix4f();

    public void initialize() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);

        baseShader = new Shader(
                getClass().getClassLoader().getResource("vertex.glsl").getPath(),
                getClass().getClassLoader().getResource("fragment.glsl").getPath()
        );

        Texture texture = new Texture(
                getClass().getClassLoader().getResource("arbuz1.png").getPath()
        );
        
        final float SIZE = 8.f;
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

        mesh = new Mesh(vertices, indices, texture);

        model = model.translate(0.f, 0.f, 0.f);
        view.identity();
        projection = projection.perspective(45.f, 1.0f, 0.1f, 1000.0f);
        glClearColor(0.07f, 0.13f, 0.17f, 1.0f);
    }

    public void setView(Matrix4f view) {
        this.view = view;
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void render() {
        baseShader.use();
        baseShader.setUniform("texture1", 0);
        baseShader.setUniform("view", view);
        baseShader.setUniform("model", model);
        baseShader.setUniform("projection", projection);
        mesh.update(1);
        RendererErrorsHandler.checkForErrorsAndPrint();
    }

    public void terminate() {
        baseShader.delete();
        mesh.delete();
    }
}
