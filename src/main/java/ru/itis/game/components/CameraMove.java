package ru.itis.game.components;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Direction;
import ru.itis.gengine.gamelogic.components.Transform;

public class CameraMove extends Component {
    public float mouseSpeed = 0.01f;
    public float moveSpeed = 0.01f;

    private Transform transform;
    private Events events;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(long deltaTime) {
        if(events.isKeyPressed(Key.W)) {
            transform.translate(Direction.Forward, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.S)) {
            transform.translate(Direction.Backward, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.A)) {
            transform.translate(Direction.Left, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.D)) {
            transform.translate(Direction.Right, moveSpeed * deltaTime);
        }
        if (events.isCursorLocked()) {
            transform.rotate(events.getDeltaY() * mouseSpeed * deltaTime, events.getDeltaX() * 0.01f, 0.f);
        }
    }
}