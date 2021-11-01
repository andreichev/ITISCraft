package ru.itis.gengine.application;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.events.impl.EventsGlfwImpl;
import ru.itis.gengine.gamelogic.LevelBase;
import ru.itis.gengine.gamelogic.World;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;
import ru.itis.gengine.window.impl.WindowGlfwImpl;

public class Application {
    public static final Application shared = new Application();

    private Window window;
    private Events events;
    private Renderer renderer;
    private World world;
    private LevelBase currentLevel;
    private double time;
    private float timeCount;
    private int fps;
    private int fpsCount;
    private int ups;
    private int upsCount;

    public void run(ApplicationStartupSettings applicationStartupSettings) {
        initialize(applicationStartupSettings);
        loop();
        terminate();
    }

    public void loadLevel(LevelBase level) {
        if(currentLevel != null) {
            currentLevel.terminate();
        }
        currentLevel = level;
        currentLevel.start(world);
    }

    private Application() {}

    private void initialize(ApplicationStartupSettings settings) {
        renderer = new Renderer();
        WindowGlfwImpl windowGlfw = new WindowGlfwImpl();
        window = windowGlfw;
        window.initialize(settings.getWindowTitle(), settings.getWindowSize(), settings.isFullScreen());
        events = new EventsGlfwImpl(windowGlfw.getWindowHandle());
        events.initialize();
        world = new World(window, events, renderer);
        renderer.initialize();
        time = window.getTime();
        settings.getStartupLevel().start(world);
    }

    private void loop() {
        while (window.isShouldClose() == false) {
            float deltaTime = (float) window.getTime() - (float) time;
            time = window.getTime();
            timeCount += deltaTime;
            fpsCount++;
            upsCount++;
            if (timeCount > 1f) {
                fps = fpsCount;
                System.out.println("FPS: "+ fps);
                fpsCount = 0;

                ups = upsCount;
                upsCount = 0;

                timeCount -= 1.f;
            }

            renderer.clear();
            if(events.isKeyPressed(Key.ESCAPE)) {
                window.setShouldClose(true);
            }
            if(events.isKeyJustPressed(Key.TAB)) {
                events.toggleCursorLock();
            }

            world.update(deltaTime);
            renderer.checkForErrors();
            window.swapBuffers();
            events.pollEvents();
        }
    }

    private void terminate() {
        if(currentLevel != null) {
            currentLevel.terminate();
        }
        world.terminate();
        window.terminate();
        renderer.terminate();
    }
}
