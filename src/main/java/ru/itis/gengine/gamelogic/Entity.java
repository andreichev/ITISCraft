package ru.itis.gengine.gamelogic;

import org.jetbrains.annotations.Nullable;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.gamelogic.components.Transform;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final List<Component> components;
    private final List<Entity> childEntities;
    private Entity parentEntity;
    private final Transform transform;
    private final Window window;
    private final Events events;
    private final Renderer renderer;

    // MARK: - Init

    Entity(Window window, Events events, Renderer renderer) {
        this.window = window;
        this.events = events;
        this.renderer = renderer;
        components = new ArrayList<>();
        childEntities = new ArrayList<>();
        transform = new Transform();
        addComponent(transform);
    }

    // MARK: - Public methods

    public void initialize() {
        for (Entity entity : childEntities) {
            entity.initialize();
        }
        for (Component component : components) {
            if (component.isActive) {
                component.initialize();
            }
        }
    }

    public void update(float deltaTime) {
        // Обновить дочерние сущности
        for (Entity entity : childEntities) {
            entity.update(deltaTime);
        }
        // Обновить все компонеты у этой сущности
        for (Component component : components) {
            if (component.isActive) {
                component.update(deltaTime);
            }
        }
    }

    public void terminate() {
        for (Entity entity : childEntities) {
            entity.terminate();
        }
        for (Component component : components) {
            if (component.isActive) {
                component.terminate();
            }
        }
    }

    public Transform getTransform() {
        return transform;
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        components.add(component);
        component.initialize();
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    public <T extends Component> T getComponentWithType(Class<T> type) {
        for (Component component : components) {
            if (type.isInstance(component)) {
                return (T) component;
            }
        }
        throw new RuntimeException("Component with type " + type.getName() + "  not found");
    }

    public void addChildEntity(Entity entity) {
        entity.parentEntity = entity;
        childEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entity.parentEntity = null;
        childEntities.remove(entity);
    }

    @Nullable
    public Entity getParent() {
        return parentEntity;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Window getWindow() {
        return window;
    }

    public Events getEvents() {
        return events;
    }
}
