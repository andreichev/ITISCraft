package ru.itis.game.levels;

import ru.itis.game.components.BlocksCreation;
import ru.itis.game.components.CameraMove;
import ru.itis.game.components.FullScreenToggle;
import ru.itis.game.components.ui.UICrosshair;
import ru.itis.game.model.Chunk;
import ru.itis.game.model.ChunksStorage;
import ru.itis.game.other.VoxelMeshGenerator;
import ru.itis.gengine.base.GRect;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
import ru.itis.gengine.gamelogic.ui.UINode;
import ru.itis.gengine.gamelogic.ui.UIView;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

public class GameLevel extends LevelBase {
    Shader baseShader;

    @Override
    public void start(World world) {
        world.getRenderer().setClearColor(0.07f, 0.13f, 0.17f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/base/base_vertex.glsl",
                "resources/shaders/base/base_fragment.glsl"
        );

        Chunk[][][] chunks = new Chunk[ChunksStorage.SIZE_X][ChunksStorage.SIZE_Y][ChunksStorage.SIZE_Z];
        for (int indexX = 0; indexX < ChunksStorage.SIZE_X; indexX++) {
            for (int indexY = 0; indexY < ChunksStorage.SIZE_Y; indexY++) {
                for (int indexZ = 0; indexZ < ChunksStorage.SIZE_Z; indexZ++) {
                    chunks[indexX][indexY][indexZ] = new Chunk();
                }
            }
        }
        ChunksStorage chunksStorage = new ChunksStorage(chunks);

        Entity cameraEntity = world.instantiateEntity();
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);
        camera.setFieldOfView(60.f);
        camera.setShader(baseShader);
        cameraEntity.addComponent(new CameraMove());
        BlocksCreation blocksCreation = new BlocksCreation();
        blocksCreation.setChunks(chunksStorage);
        cameraEntity.addComponent(blocksCreation);
        cameraEntity.getTransform().translate(ChunksStorage.WORLD_SIZE_X / 2, ChunksStorage.WORLD_SIZE_Y / 2, ChunksStorage.WORLD_SIZE_Z / 2);
        cameraEntity.getTransform().rotate((float) (Math.PI / 4f), (float) Math.PI, 0f);
        cameraEntity.addComponent(new FullScreenToggle());

        Texture texture = new Texture("resources/textures/Texture.png");
        for (int indexX = 0; indexX < ChunksStorage.SIZE_X; indexX++) {
            for (int indexY = 0; indexY < ChunksStorage.SIZE_Y; indexY++) {
                for (int indexZ = 0; indexZ < ChunksStorage.SIZE_Z; indexZ++) {
                    Entity chunkEntity = world.instantiateEntity();
                    MeshData meshData = VoxelMeshGenerator.makeOneChunkMesh(chunksStorage, indexX, indexY, indexZ, true);
                    Mesh mesh = new Mesh(meshData, false, texture, baseShader);
                    chunksStorage.chunks[indexX][indexY][indexZ].setMesh(mesh);
                    chunkEntity.addComponent(mesh);
                }
            }
        }

        UINode root = new UINode();
        root.addSubnode(new UICrosshair());
        root.addSubnode(new UIView(
                new GRect(50, 500, 200, 50)
        ));
        world.addRootUIElement(root);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
