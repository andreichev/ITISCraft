package ru.itis.gengine.gamelogic;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.ui.UINode;
import ru.itis.gengine.gamelogic.ui.UIRoot;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

public class World {
    private final Entity root;
    private final UIRoot uiRoot;
    private final Window window;
    private final Events events;
    private final Renderer renderer;

    // MARK: - Init

    public World(Window window, Events events, Renderer renderer) {
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        root = new Entity(window, events, renderer);
        uiRoot = new UIRoot(renderer);
    }

    // MARK: - Public methods

    public void initialize() {
        root.initialize();
    }

    public void terminate() {
        root.terminate();
        uiRoot.terminate();
    }

    public void update(float deltaTime) {
        root.update(deltaTime);
        uiRoot.render();
    }

    public void addUiNode(UINode node) {
        uiRoot.addSubnode(node);
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

    public Renderer getRenderer() {
        return renderer;
    }
}
