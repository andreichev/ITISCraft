package ru.itis.itiscraft.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.itiscraft.gamelogic.Component;
import ru.itis.itiscraft.gamelogic.Direction;

public class Camera extends Component {
    private final Matrix4f rotationMatrix;
    private final Vector3f rotation;
    private final Vector4f position;
    private final Vector4f front;
    private final Vector4f target;
    private final Vector4f up;
    private final Vector4f right;
    private final Matrix4f view;

    public Camera(float x, float y, float z) {
        position = new Vector4f(x, y, z, 1.f);
        target = new Vector4f();
        front = new Vector4f();
        up = new Vector4f();
        right = new Vector4f();
        rotationMatrix = new Matrix4f();
        view = new Matrix4f();
        rotation = new Vector3f();
        updateVectors();
    }

    public void applyMouseMove(float deltaX, float deltaY) {
        rotation.x += deltaY;
        rotation.y += deltaX;
        rotationMatrix.identity();
        rotationMatrix.rotate(rotation.x, 1.f, 0.f, 0.f);
        rotationMatrix.rotate(rotation.y, 0.f, 1.f, 0.f);
        rotationMatrix.transpose();
        updateVectors();
    }

    public void move(Direction direction, float speed) {
        switch (direction) {
            case Forward:
                position.add(front.mul(speed));
                break;
            case Backward:
                position.sub(front.mul(speed));
                break;
            case Left:
                position.sub(right.mul(speed));
                break;
            case Right:
                position.add(right.mul(speed));
                break;
        }
    }

    public Matrix4f getView() {
        // target = position + front
        position.add(front, target);
        view.identity();
        return view.lookAt(
                position.x, position.y, position.z,
                target.x, target.y, target.z,
                up.x, up.y, up.z
        );
    }

    private void updateVectors() {
        // front = rotation * unitForward
        Direction.unitForward.mul(rotationMatrix, front);
        // right = rotation * unitRight
        Direction.unitRight.mul(rotationMatrix, right);
        // up = rotation * unitUp
        Direction.unitUp.mul(rotationMatrix, up);
    }
}
