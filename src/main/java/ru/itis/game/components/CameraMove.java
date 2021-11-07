package ru.itis.game.components;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.base.Direction;
import ru.itis.gengine.gamelogic.components.Transform;

public class CameraMove extends Component {
    public float mouseSpeed = 0.005f;
    public float moveSpeed = 3.0f;

    private Transform transform;
    private Events events;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(float deltaTime) {
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