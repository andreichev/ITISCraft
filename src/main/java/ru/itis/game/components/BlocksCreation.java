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
            if (events.isMouseButtonJustClicked(MouseButton.LEFT)) {
                int x = v.end.x + v.normal.x;
                int y = v.end.y + v.normal.y;
                int z = v.end.z + v.normal.z;
                setVoxel(x, y, z, (byte) 11);
            } else if (events.isMouseButtonJustClicked(MouseButton.RIGHT)) {
                int x = v.end.x;
                int y = v.end.y;
                int z = v.end.z;
                setVoxel(x, y, z, (byte) 0);
            }
        }
    }

    private void setVoxel(int x, int y, int z, byte id) {
        if (x < 0 || y < 0 || z < 0 || x >= ChunksStorage.WORLD_SIZE_X || y >= ChunksStorage.WORLD_SIZE_Y || z >= ChunksStorage.WORLD_SIZE_Z)
            return;

        chunks.setVoxel(x, y, z, id);
        int chunkIndexX = x / Chunk.SIZE_X;
        int chunkIndexY = y / Chunk.SIZE_Y;
        int chunkIndexZ = z / Chunk.SIZE_Z;
        updateChunk(chunkIndexX, chunkIndexY, chunkIndexZ);

        updateNeigbourChunkIfNeeded(
                x, chunkIndexX,
                Chunk.SIZE_X, ChunksStorage.SIZE_X,
                chunkIndexX, chunkIndexY, chunkIndexZ,
                -1, 0, 0
        );
        updateNeigbourChunkIfNeeded(
                y, chunkIndexY,
                Chunk.SIZE_Y, ChunksStorage.SIZE_Y,
                chunkIndexX, chunkIndexY, chunkIndexZ,
                0, -1, 0
        );
        updateNeigbourChunkIfNeeded(
                z, chunkIndexZ,
                Chunk.SIZE_Z, ChunksStorage.SIZE_Z,
                chunkIndexX, chunkIndexY, chunkIndexZ,
                0, 0, -1
        );

//        if (x % Chunk.SIZE_X == 0 && chunkIndexX > 0) {
//            updateChunk(chunkIndexX - 1, chunkIndexY, chunkIndexZ);
//        }
//        if (x % Chunk.SIZE_X == Chunk.SIZE_X - 1 && chunkIndexX < ChunksStorage.SIZE_X - 1) {
//            updateChunk(chunkIndexX + 1, chunkIndexY, chunkIndexZ);
//        }
//        if (z % Chunk.SIZE_Z == 0 && chunkIndexZ > 0) {
//            updateChunk(chunkIndexX, chunkIndexY, chunkIndexZ - 1);
//        }
//        if (z % Chunk.SIZE_Z == Chunk.SIZE_Z - 1 && chunkIndexZ < ChunksStorage.SIZE_Z - 1) {
//            updateChunk(chunkIndexX, chunkIndexY, chunkIndexZ + 1);
//        }
//        if (y % Chunk.SIZE_Y == 0 && chunkIndexY > 0) {
//            updateChunk(chunkIndexX, chunkIndexY - 1, chunkIndexZ);
//        }
//        if (y % Chunk.SIZE_Y == Chunk.SIZE_Y - 1 && chunkIndexY < ChunksStorage.SIZE_Y - 1) {
//            updateChunk(chunkIndexX, chunkIndexY + 1, chunkIndexZ);
//        }
    }

    private void updateNeigbourChunkIfNeeded(
            int coordinateDirected, int chunkDirectedIndex,
            int chunkDirectedSize, int chunkDirectedCount,
            int chunkIndexX, int chunkIndexY, int chunkIndexZ,
            int shiftX, int shiftY, int shiftZ
    ) {
        if (coordinateDirected % chunkDirectedSize == 0 && chunkDirectedIndex > 0) {
            updateChunk(
                    chunkIndexX + shiftX,
                    chunkIndexY + shiftY,
                    chunkIndexZ + shiftZ
            );
        }
        if (coordinateDirected % chunkDirectedSize == chunkDirectedSize - 1 && chunkDirectedIndex < chunkDirectedCount - 1) {
            updateChunk(
                    chunkIndexX - shiftX,
                    chunkIndexY - shiftY,
                    chunkIndexZ - shiftZ
            );
        }
    }

    private void updateChunk(int chunkIndexX, int chunkIndexY, int chunkIndexZ) {
        MeshData primitiveMeshData = VoxelMeshGenerator.makeOneChunkMesh(chunks, chunkIndexX, chunkIndexY, chunkIndexZ);
        chunks.chunks[chunkIndexX][chunkIndexY][chunkIndexZ].mesh.updateBuffer(primitiveMeshData);
    }
}
