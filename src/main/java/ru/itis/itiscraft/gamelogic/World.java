package ru.itis.itiscraft.gamelogic;

import ru.itis.itiscraft.events.Events;
import ru.itis.itiscraft.renderer.Renderer;
import ru.itis.itiscraft.window.Window;

public class World {
    private final Entity root;
    private final Window window;
    private final Events events;
    private final Renderer renderer;

    public World(Window window, Events events, Renderer renderer) {
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        root = new Entity(window, events, renderer);
    }

    public void initialize() {
        root.initialize();
    }

    public void terminate() {
        root.terminate();
    }

    public void update(long deltaTime) {
        root.update(deltaTime);
    }

    public Entity instantiate() {
        Entity entity = new Entity(window, events, renderer);
        root.addChildEntity(entity);
        return entity;
    }

    public void destroy(Entity entity) {
        entity.terminate();
        root.removeEntity(entity);
    }
}
