package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.model.Chunk;
import ru.itis.game.model.VoxelRaycastData;
import ru.itis.game.other.VoxelMeshGenerator;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.MouseButton;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.gamelogic.primitives.MeshData;

public class BlocksCreation extends Component {
    private Transform transform;
    private Chunk chunk;
    private Mesh mesh;
    private Events events;
    private final int maximumDistance = 10;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void update(float deltaTime) {
        Vector4f position = transform.getPosition();
        Vector4f target = transform.getFront();
        VoxelRaycastData v = chunk.bresenham3D(
                position.x, position.y, position.z,
                target.x, target.y, target.z,
                maximumDistance
        );
        if (v != null && v.voxel != null) {
            if(events.isMouseButtonJustClicked(MouseButton.LEFT)) {
                chunk.set(v.end.x + v.normal.x, v.end.y + v.normal.y, v.end.z + v.normal.z, (byte) 7);
                MeshData primitiveMeshData = VoxelMeshGenerator.generate(chunk);
                mesh.updateBuffer(primitiveMeshData);
            } else if(events.isMouseButtonJustClicked(MouseButton.RIGHT)) {
                chunk.set(v.end.x, v.end.y, v.end.z, (byte) 0);
                MeshData primitiveMeshData = VoxelMeshGenerator.generate(chunk);
                mesh.updateBuffer(primitiveMeshData);
            }
        }
    }
}
