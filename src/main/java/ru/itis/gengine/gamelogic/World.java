package ru.itis.gengine.gamelogic;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

public class World {
    private final Entity root;
    private final Window window;
    private final Events events;
    private final Renderer renderer;

    // MARK: - Init

    public World(Window window, Events events, Renderer renderer) {
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        root = new Entity(window, events, renderer);
    }

    // MARK: - Public methods

    public void initialize() {
        root.initialize();
    }

    public void terminate() {
        root.terminate();
    }

    public void update(double deltaTime) {
        root.update(deltaTime);
    }

    public Entity instantiateEntity() {
        Entity entity = new Entity(window, events, renderer);
        root.addChildEntity(entity);
        return entity;
    }

    public void destroy(Entity entity) {
        entity.terminate();
        root.removeEntity(entity);
    }

    public Window getWindow() {
        return window;
    }

    public Events getEvents() {
        return events;
    }
}
