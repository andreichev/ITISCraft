package ru.itis.itiscraft.application;

import org.joml.Matrix4f;
import ru.itis.itiscraft.events.Events;
import ru.itis.itiscraft.events.Key;
import ru.itis.itiscraft.events.impl.EventsGlfwImpl;
import ru.itis.itiscraft.gamelogic.Direction;
import ru.itis.itiscraft.gamelogic.Entity;
import ru.itis.itiscraft.gamelogic.World;
import ru.itis.itiscraft.gamelogic.components.Camera;
import ru.itis.itiscraft.gamelogic.components.Mesh;
import ru.itis.itiscraft.gamelogic.primitives.Primitives;
import ru.itis.itiscraft.mygamelogic.CameraMove;
import ru.itis.itiscraft.renderer.Renderer;
import ru.itis.itiscraft.renderer.Shader;
import ru.itis.itiscraft.renderer.Texture;
import ru.itis.itiscraft.window.Window;
import ru.itis.itiscraft.window.impl.WindowGlfwImpl;

public class Application {
    private final int WIDTH = 700;
    private final int HEIGHT = 700;

    private final Window window;
    private final Events events;
    private final Renderer renderer = new Renderer();
    private final Camera camera = new Camera();
    private final Shader baseShader;
    private final Matrix4f model;

    private final World world;

    public Application() {
        model = new Matrix4f();
        WindowGlfwImpl windowGlfw = new WindowGlfwImpl();
        window = windowGlfw;
        window.initialize("Hello, world!", WIDTH, HEIGHT, false);
        events = new EventsGlfwImpl(windowGlfw.getWindowHandle());
        events.initialize();
        world = new World(window, events, renderer);
        renderer.initialize();

        baseShader = new Shader(
                "src/main/resources/shaders/base/vertex.glsl",
                "src/main/resources/shaders/base/fragment.glsl"
        );

        Entity cube1 = world.instantiate();
        Texture texture1 = new Texture("src/main/resources/textures/block.png");
        Mesh mesh1 = new Mesh(Primitives.createCube(8.f), texture1, baseShader);
        cube1.addComponent(mesh1);

        Entity cube2 = world.instantiate();
        Texture texture2 = new Texture("src/main/resources/textures/block.png");
        Mesh mesh2 = new Mesh(Primitives.createCube(4.f), texture2, baseShader);
        cube2.addComponent(mesh2);
        cube2.getTransform().move(Direction.Forward, 10);

        Entity cameraEntity = world.instantiate();
        cameraEntity.addComponent(camera);
        cameraEntity.addComponent(new CameraMove());
    }

    public void run() {
        loop();
        terminate();
    }

    private void loop() {
        while (window.isShouldClose() == false) {
            renderer.clear();
            if(events.isKeyPressed(Key.ESCAPE)) {
                window.setShouldClose(true);
            }
            if(events.isKeyJustPressed(Key.TAB)) {
                events.toggleCursorLock();
            }

            baseShader.use();
            baseShader.setUniform("texture1", 0);
            baseShader.setUniform("view", camera.getView());
            baseShader.setUniform("model", model);
            baseShader.setUniform("projection", camera.getProjection());

            world.update(1);

            renderer.checkForErrors();

            window.swapBuffers();
            events.pollEvents();
        }
    }

    private void terminate() {
        world.terminate();
        window.terminate();
        renderer.terminate();
    }
}
