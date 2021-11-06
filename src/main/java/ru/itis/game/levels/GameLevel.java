package ru.itis.game.levels;

import ru.itis.game.components.CameraMove;
import ru.itis.gengine.gamelogic.Entity;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.gamelogic.components.Camera;
import ru.itis.gengine.gamelogic.components.Mesh;
import ru.itis.gengine.gamelogic.primitives.Primitives;
import ru.itis.gengine.renderer.Shader;
import ru.itis.gengine.renderer.Texture;

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

        Texture texture1 = new Texture("resources/textures/oak_planks.png");
        Texture texture2 = new Texture("resources/textures/stone.png");

        Entity cube1 = world.instantiateEntity();
        Mesh mesh1 = new Mesh(Primitives.createCube(1.f), texture1, baseShader);
        cube1.addComponent(mesh1);
        cube1.getTransform().translate(0.f, 0.f, 0.f);

        Entity cube2 = world.instantiateEntity();
        Mesh mesh2 = new Mesh(Primitives.createCube(1.f), texture2, baseShader);
        cube2.addComponent(mesh2);
        cube2.getTransform().translate(1.f, 1.f, 0.f);

        Entity cube3 = world.instantiateEntity();
        Mesh mesh3 = new Mesh(Primitives.createCube(1.f), texture2, baseShader);
        cube3.addComponent(mesh3);
        cube3.getTransform().translate(2.f, 1.f, 0.f);

        Entity cube4 = world.instantiateEntity();
        Mesh mesh4 = new Mesh(Primitives.createCube(1.f), texture2, baseShader);
        cube4.addComponent(mesh4);
        cube4.getTransform().translate(-1.f, 0.f, 0.f);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
