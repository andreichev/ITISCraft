package ru.itis.itiscraft.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.itiscraft.gamelogic.Component;
import ru.itis.itiscraft.gamelogic.primitives.PrimitiveMeshData;
import ru.itis.itiscraft.renderer.*;

public class Mesh extends Component {
    private final IndexBuffer indexBuffer;
    private final VertexBuffer vertexBuffer;
    private final Texture texture;
    private final Shader shader;
    private final Matrix4f model;
    private Transform transform;
    private Renderer renderer;

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

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        renderer = getEntity().getRenderer();
    }

    @Override
    public void terminate() {
        texture.delete();
        indexBuffer.delete();
        vertexBuffer.delete();
    }

    @Override
    public void update(long deltaTime) {
        texture.bind();
        indexBuffer.bind();
        vertexBuffer.bind();
        shader.setUniform("model", getModelMatrix());
        renderer.drawCall(indexBuffer.getSize());
    }

    private Matrix4f getModelMatrix() {
        model.identity();
        Vector4f position = transform.getPosition();
        model.translate(position.x, position.y, position.z);
        Vector3f rotation = transform.getRotation();
        model.rotate(rotation.x, 1.f, 0.f, 0.f);
        model.rotate(rotation.y, 0.f, 1.f, 0.f);
        model.rotate(rotation.z, 0.f, 0.f, 1.f);
        return  model;
    }
}
