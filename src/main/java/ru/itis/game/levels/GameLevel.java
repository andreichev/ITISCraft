package ru.itis.game.levels;

import ru.itis.game.components.CameraMove;
import ru.itis.game.model.Chunk;
import ru.itis.game.other.VoxelMeshGenerator;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.renderer.Shader;

public class GameLevel extends LevelBase {
    Shader baseShader;

    @Override
    public void start(World world) {
        world.getRenderer().setClearColor(0.07f, 0.13f, 0.17f, 1.0f);
        baseShader = new Shader(
                "resources/shaders/base/vertex.glsl",
                "resources/shaders/base/fragment.glsl"
        );

        Entity cameraEntity = world.instantiateEntity();
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);
        camera.setFieldOfView(70.f);
        camera.setShader(baseShader);
        cameraEntity.addComponent(new CameraMove());
        cameraEntity.getTransform().translate(0.f, 0.f, 1.3f);

        VoxelMeshGenerator voxelMeshGenerator = new VoxelMeshGenerator();

        Entity cube1 = world.instantiateEntity();
        Mesh mesh1 = voxelMeshGenerator.generate(new Chunk(), baseShader);
        cube1.addComponent(mesh1);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
