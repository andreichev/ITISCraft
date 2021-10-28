package ru.itis.itiscraft.gamelogic;

import ru.itis.itiscraft.gamelogic.components.Transform;

import java.util.HashSet;
import java.util.Set;

public class Entity {
    private final Set<Component> components;
    private final Set<Entity> childEntities;
    private final Transform transform;

    Entity() {
        components = new HashSet<>();
        childEntities = new HashSet<>();
        transform = new Transform();
        addComponent(transform);
    }

    public void initialize() {
        for(Entity entity: childEntities) {
            entity.initialize();
        }
        for(Component component: components) {
            if(component.isActive) {
                component.initialize();
            }
        }
    }

    public void terminate() {
        for(Entity entity: childEntities) {
            entity.terminate();
        }
        for(Component component: components) {
            if(component.isActive) {
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
        for(Component component: components) {
            if (type.isInstance(component)) {
                return (T) component;
            }
        }
        throw new RuntimeException("Component with type " + type.getName() + "  not found");
    }

    public void addChildEntity(Entity entity) {
        childEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        childEntities.remove(entity);
    }

    public void update(long deltaTime) {
        for(Entity entity: childEntities) {
            entity.update(deltaTime);
        }
        for(Component component: components) {
            if(component.isActive) {
                component.update(deltaTime);
            }
        }
    }
}
