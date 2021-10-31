package ru.itis.gengine.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.gamelogic.Direction;

public class Transform extends Component {
    private final Vector3f rotation;
    private final Vector4f position;

    /// Переменные для совершения вычислений новой позиции
    private final Matrix4f rotationMatrix;
    private final Vector4f front;
    private final Vector4f up;
    private final Vector4f right;

    public Transform() {
        position = new Vector4f();
        rotation = new Vector3f();
        rotationMatrix = new Matrix4f();
        front = new Vector4f();
        up = new Vector4f();
        right = new Vector4f();
        updateVectors();
    }

    public void rotate(float x, float y, float z) {
        rotation.add(x, y, z);
        rotationMatrix.identity();
        rotationMatrix.rotate(rotation.x, 1.f, 0.f, 0.f);
        rotationMatrix.rotate(rotation.y, 0.f, 1.f, 0.f);
        rotationMatrix.rotate(rotation.z, 0.f, 0.f, 1.f);
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

    private void updateVectors() {
        // front = rotation * unitForward
        Direction.unitForward.mul(rotationMatrix, front);
        // right = rotation * unitRight
        Direction.unitRight.mul(rotationMatrix, right);
        // up = rotation * unitUp
        Direction.unitUp.mul(rotationMatrix, up);
    }

    public Vector4f getFront() {
        return front;
    }

    public Vector4f getRight() {
        return right;
    }

    public Vector4f getUp() {
        return up;
    }

    public Vector4f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
