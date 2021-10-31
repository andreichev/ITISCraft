package ru.itis.game.components;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.base.Direction;
import ru.itis.gengine.gamelogic.components.Transform;

public class CameraMove extends Component {
    public float mouseSpeed = 10.0f;
    public float moveSpeed = 3.0f;

    private Transform transform;
    private Events events;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(double deltaTime) {
        if(events.isKeyPressed(Key.W)) {
            transform.translate(Direction.Forward, moveSpeed * (float) deltaTime);
        }
        if(events.isKeyPressed(Key.S)) {
            transform.translate(Direction.Backward, moveSpeed * (float) deltaTime);
        }
        if(events.isKeyPressed(Key.A)) {
            transform.translate(Direction.Left, moveSpeed * (float) deltaTime);
        }
        if(events.isKeyPressed(Key.D)) {
            transform.translate(Direction.Right, moveSpeed * (float) deltaTime);
        }
        if (events.isCursorLocked()) {
            transform.rotate(
                    events.getDeltaY() * mouseSpeed * (float) deltaTime,
                    events.getDeltaX() * mouseSpeed * (float) deltaTime,
                    0.f
            );
        }
    }
}