package ru.itis.itiscraft.gamelogic;

public class World {
    private final Entity root;

    public World() {
        root = new Entity();
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

    public void instantiate(Entity entity) {
        root.addChildEntity(entity);
    }

    public void destroy(Entity entity) {
        root.removeEntity(entity);
    }
}
