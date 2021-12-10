package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.VertexBuffer;
import ru.itis.gengine.renderer.VertexBufferLayout;

public class UICrosshair extends UINode {
    private Shader crosshairShader;
    private VertexBuffer vertexBuffer;
    private Renderer renderer;

    @Override
    public void initialize() {
        float[] vertices = {
                 0.0f,  0.1f,  0.0f, -0.1f,
                -0.1f,  0.0f,  0.1f,  0.0f
        };
        this.renderer = getRoot().getRenderer();
        crosshairShader = new Shader(
                "resources/shaders/base/crosshair_vertex.glsl",
                "resources/shaders/base/crosshair_fragment.glsl"
        );
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        vertexBuffer = new VertexBuffer(vertices, false, layout);
    }

    @Override
    public void render() {
        crosshairShader.use();
        vertexBuffer.bind();
        renderer.drawLines(4);
        vertexBuffer.unbind();
    }

    @Override
    public void terminate() {
        crosshairShader.delete();
    }
}
