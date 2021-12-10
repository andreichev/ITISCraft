package ru.itis.game.components;

import org.joml.Vector4f;
import ru.itis.game.model.Chunk;
import ru.itis.game.model.ChunksStorage;
import ru.itis.game.model.VoxelRaycastData;
import ru.itis.game.other.VoxelMeshGenerator;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.MouseButton;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.gamelogic.primitives.MeshData;

public class BlocksCreation extends Component {
    private Transform transform;
    private ChunksStorage chunks;
    private Events events;
    private final int maximumDistance = 10;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    public void setChunks(ChunksStorage chunks) {
        this.chunks = chunks;
    }

    @Override
    public void update(float deltaTime) {
        Vector4f position = transform.getPosition();
        Vector4f target = transform.getFront();
        VoxelRaycastData v = chunks.bresenham3D(
                position.x, position.y, position.z,
                target.x, target.y, target.z,
                maximumDistance
        );
        if (v != null && v.voxel != null) {
            if(events.isMouseButtonJustClicked(MouseButton.LEFT)) {
                int x = v.end.x + v.normal.x;
                int y = v.end.y + v.normal.y;
                int z = v.end.z + v.normal.z;
                chunks.setVoxel(x, y, z, (byte) 7);
                int chunkIndexX = x / Chunk.SIZE_X;
                int chunkIndexY = y / Chunk.SIZE_Y;
                int chunkIndexZ = z / Chunk.SIZE_Z;
                MeshData primitiveMeshData = VoxelMeshGenerator.makeOneChunkMesh(chunks, chunkIndexX, chunkIndexY ,chunkIndexZ);
                chunks.chunks[chunkIndexX][chunkIndexY][chunkIndexZ].mesh.updateBuffer(primitiveMeshData);
            } else if(events.isMouseButtonJustClicked(MouseButton.RIGHT)) {
                chunks.setVoxel(v.end.x, v.end.y, v.end.z, (byte) 0x0);
                int chunkIndexX = v.end.x / Chunk.SIZE_X;
                int chunkIndexY = v.end.y / Chunk.SIZE_Y;
                int chunkIndexZ = v.end.z / Chunk.SIZE_Z;
                MeshData primitiveMeshData = VoxelMeshGenerator.makeOneChunkMesh(chunks, chunkIndexX, chunkIndexY ,chunkIndexZ);
                chunks.chunks[chunkIndexX][chunkIndexY][chunkIndexZ].mesh.updateBuffer(primitiveMeshData);
            }
        }
    }
}
