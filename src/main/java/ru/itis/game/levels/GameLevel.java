package ru.itis.game.levels;

import ru.itis.game.components.BlocksCreation;
import ru.itis.game.components.CameraMove;
import ru.itis.game.model.Chunk;
import ru.itis.game.other.VoxelMeshGenerator;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.MeshData;
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
        Chunk chunk = new Chunk();
        Entity cameraEntity = world.instantiateEntity();
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);
        camera.setFieldOfView(70.f);
        camera.setShader(baseShader);
        cameraEntity.addComponent(new CameraMove());
        BlocksCreation blocksCreation = new BlocksCreation();
        blocksCreation.setChunk(chunk);
        cameraEntity.addComponent(blocksCreation);
        cameraEntity.getTransform().translate(0.f, 0.f, 1.3f);

        Entity chunkEntity = world.instantiateEntity();
        MeshData mesh1Data = VoxelMeshGenerator.generate(chunk);
        Texture texture = new Texture("resources/textures/Texture.png");
        Mesh mesh1 = new Mesh(mesh1Data, false, texture, baseShader);
        blocksCreation.setMesh(mesh1);
        chunkEntity.addComponent(mesh1);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
