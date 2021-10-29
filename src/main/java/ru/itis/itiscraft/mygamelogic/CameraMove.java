package ru.itis.itiscraft.mygamelogic;

import ru.itis.itiscraft.events.Events;
import ru.itis.itiscraft.events.Key;
import ru.itis.itiscraft.events.WindowSizeDelegate;
import ru.itis.itiscraft.gamelogic.Component;
import ru.itis.itiscraft.gamelogic.Direction;
import ru.itis.itiscraft.gamelogic.components.Transform;

public class CameraMove extends Component {
    private Transform transform;
    private Events events;

    float mouseSpeed = 0.01f;
    float moveSpeed = 0.01f;

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
        events = getEntity().getEvents();
    }

    @Override
    public void update(long deltaTime) {
        if(events.isKeyPressed(Key.W)) {
            transform.move(Direction.Forward, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.S)) {
            transform.move(Direction.Backward, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.A)) {
            transform.move(Direction.Left, moveSpeed * deltaTime);
        }
        if(events.isKeyPressed(Key.D)) {
            transform.move(Direction.Right, moveSpeed * deltaTime);
        }
        if (events.isCursorLocked()) {
            transform.rotate(events.getDeltaY() * mouseSpeed * deltaTime, events.getDeltaX() * 0.01f, 0.f);
        }
    }
}