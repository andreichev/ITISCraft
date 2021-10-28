package ru.itis.itiscraft.gamelogic;

public abstract class Component {
    public boolean isActive = true;
    private Entity entity;

    void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void initialize() {}

    public void terminate() {}

    public void update(long deltaTime) {}
}
