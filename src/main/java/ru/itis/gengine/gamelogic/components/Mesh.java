package ru.itis.gengine.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.primitives.PrimitiveMeshData;
import ru.itis.gengine.renderer.*;

public class Mesh extends Component implements TransformDelegate {
    private final IndexBuffer indexBuffer;
    private final VertexBuffer vertexBuffer;
    private final Texture texture;
    private final Shader shader;
    private final Matrix4f model;
    private Transform transform;
    private Renderer renderer;

    // MARK: - Init

    public Mesh(PrimitiveMeshData primitiveMeshData, Texture texture, Shader shader) {
        this.texture = texture;
        this.shader = shader;
        model = new Matrix4f();
        indexBuffer = new IndexBuffer(primitiveMeshData.getIndices());
        vertexBuffer = new VertexBuffer(primitiveMeshData.getVertices());
    }

    public Mesh(Vertex[] vertices, int[] indices, Texture texture, Shader shader) {
        this.texture = texture;
        this.shader = shader;
        model = new Matrix4f();
        indexBuffer = new IndexBuffer(indices);
        vertexBuffer = new VertexBuffer(vertices);
    }

    // MARK: - Overridden methods

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        renderer = getEntity().getRenderer();
        getEntity().getTransform().addDelegate(this);
    }

    @Override
    public void update(long deltaTime) {
        shader.use();
        shader.use();
        shader.setUniform("model", model);
        texture.bind();
        indexBuffer.bind();
        vertexBuffer.bind();
        renderer.drawCall(indexBuffer.getSize());
    }

    @Override
    public void terminate() {
        texture.delete();
        indexBuffer.delete();
        vertexBuffer.delete();
        transform.removeDelegate(this);
    }

    // MARK: - Private methods

    private void updateModelMatrix() {
        model.identity();
        Vector4f position = transform.getPosition();
        model.translate(position.x, position.y, position.z);
        Vector3f rotation = transform.getRotation();
        model.rotate(rotation.x, 1.f, 0.f, 0.f);
        model.rotate(rotation.y, 0.f, 1.f, 0.f);
        model.rotate(rotation.z, 0.f, 0.f, 1.f);
    }

    // MARK: - TransformDelegate

    @Override
    public void transformChanged(Vector4f position, Vector3f rotation) {
        updateModelMatrix();
    }
}
