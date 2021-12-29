package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.VertexBuffer;
import ru.itis.gengine.renderer.VertexBufferLayout;

public class UICrosshair extends UINode {
    private final Shader crosshairShader;
    private final VertexBuffer vertexBuffer;

    public UICrosshair() {
        float[] vertices = {
                 0.0f,  0.1f,  0.0f, -0.1f,
                -0.1f,  0.0f,  0.1f,  0.0f
        };
        crosshairShader = new Shader(
                "resources/shaders/ui/crosshair/crosshair_vertex.glsl",
                "resources/shaders/ui/crosshair/crosshair_fragment.glsl"
        );
        VertexBufferLayout layout = new VertexBufferLayout();
        layout.pushFloat(2);
        vertexBuffer = new VertexBuffer(vertices, false, layout);
    }

    @Override
    public void draw() {
        crosshairShader.use();
        vertexBuffer.bind();
        renderer.setLineWidth(3f);
        renderer.drawLines(4);
        renderer.setLineWidth(1f);
        vertexBuffer.unbind();
    }

    @Override
    public void terminate() {
        vertexBuffer.delete();
        crosshairShader.delete();
    }
}
