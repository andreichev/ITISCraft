package ru.itis.game.components;

import ru.itis.gengine.base.Direction;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.components.Transform;

public class CameraMove extends Component {
    public float mouseSpeed = 0.005f;
    public float moveSpeed = 8.0f;

    private Transform transform;
    private Events events;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(float deltaTime) {
        float speed = moveSpeed * deltaTime;

        if (events.isKeyPressed(Key.W)) {
            transform.translate(Direction.Forward, speed);
        }
        if (events.isKeyPressed(Key.S)) {
            transform.translate(Direction.Backward, speed);
        }
        if (events.isKeyPressed(Key.A)) {
            transform.translate(Direction.Left, speed);
        }
        if (events.isKeyPressed(Key.D)) {
            transform.translate(Direction.Right, speed);
        }
        if (events.isKeyPressed(Key.SPACE)) {
            transform.translate(Direction.Up, speed);
        }
        if (events.isKeyPressed(Key.LEFT_SHIFT)) {
            transform.translate(Direction.Down, speed);
        }
        if (events.isCursorLocked()) {
            // DeltaX - смещение мыши за реальное время, поэтому умножение на deltaTime не требуется.
            // Действия в реальном мире не нужно умножать на deltaTime, умножать нужно только действия в игровом мире.
            transform.rotate(
                    events.getDeltaY() * mouseSpeed,
                    events.getDeltaX() * mouseSpeed,
                    0.f
            );
        }
    }
}