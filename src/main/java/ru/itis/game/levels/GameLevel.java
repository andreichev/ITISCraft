package ru.itis.game.levels;

import ru.itis.game.components.CameraMove;
import ru.itis.gengine.gamelogic.Direction;
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
        baseShader = new Shader(
                "resources/shaders/base/vertex.glsl",
                "resources/shaders/base/fragment.glsl"
        );

        Entity cameraEntity = world.instantiateEntity();
        Camera camera = new Camera();
        cameraEntity.addComponent(camera);
        camera.setFieldOfView(90.f);
        camera.setShader(baseShader);
        cameraEntity.addComponent(new CameraMove());

        Entity cube1 = world.instantiateEntity();
        Texture texture1 = new Texture("resources/textures/oak_planks.png");
        Mesh mesh1 = new Mesh(Primitives.createCube(8.f), texture1, baseShader);
        cube1.addComponent(mesh1);

        Entity cube2 = world.instantiateEntity();
        Texture texture2 = new Texture("resources/textures/stone.png");
        Mesh mesh2 = new Mesh(Primitives.createCube(8.f), texture2, baseShader);
        cube2.addComponent(mesh2);
        cube2.getTransform().translate(Direction.Forward, 10);
    }

    @Override
    public void terminate() {
        baseShader.delete();
    }
}
