package ru.itis.itiscraft.application;

import ru.itis.itiscraft.events.Events;
import ru.itis.itiscraft.events.Key;
import ru.itis.itiscraft.events.impl.EventsGlfwImpl;
import ru.itis.itiscraft.gamelogic.Direction;
import ru.itis.itiscraft.renderer.Camera;
import ru.itis.itiscraft.renderer.Renderer;
import ru.itis.itiscraft.window.Window;
import ru.itis.itiscraft.window.impl.WindowGlfwImpl;

public class Application {
    private final int WIDTH = 700;
    private final int HEIGHT = 700;

    private final Window window;
    private final Events events;
    private final Renderer renderer = new Renderer();
    private final Camera camera = new Camera(0.f, 0.f, 30.f);

    public Application() {
        WindowGlfwImpl windowGlfw = new WindowGlfwImpl();
        window = windowGlfw;
        window.initialize("Hello, world!", WIDTH, HEIGHT, false);
        events = new EventsGlfwImpl(windowGlfw.getWindowHandle());
        events.initialize();
        renderer.initialize();
        renderer.setClearColor(0.07f, 0.13f, 0.17f, 1.0f);
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

            if(events.isKeyPressed(Key.W)) {
                camera.move(Direction.Forward, 1.f);
            }
            if(events.isKeyPressed(Key.S)) {
                camera.move(Direction.Backward, 1.f);
            }
            if(events.isKeyPressed(Key.A)) {
                camera.move(Direction.Left, 1.f);
            }
            if(events.isKeyPressed(Key.D)) {
                camera.move(Direction.Right, 1.f);
            }
            if(events.isKeyJustPressed(Key.TAB)) {
                events.toggleCursorLock();
            }

            camera.applyMouseMove(events.getDeltaX() * 0.01f, events.getDeltaY() * 0.01f);

            renderer.setView(camera.getView());
            // renderer.setView(new Matrix4f());
            // render, game logic
            renderer.render();

            window.swapBuffers();
            events.pollEvents();
        }
    }

    private void terminate() {
        window.terminate();
        renderer.terminate();
    }
}
