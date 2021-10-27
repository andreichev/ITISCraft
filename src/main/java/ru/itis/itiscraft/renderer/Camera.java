package ru.itis.itiscraft.renderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import ru.itis.itiscraft.gamelogic.Direction;

public class Camera {
    private final Matrix4f rotation;
    private final Vector4f position;
    private final Vector4f front;
    private final Vector4f target;
    private final Vector4f up;
    private final Vector4f right;
    private final Matrix4f view;
    private float rotationX;
    private float rotationY;

    private static final Vector4f unitUp = new Vector4f(0.f, 1.f, 0.f, 1.f);
    private static final Vector4f unitForward = new Vector4f(0.f, 0.f, -1.f, 1.f);
    private static final Vector4f unitRight = new Vector4f(1.f, 0.f, 0.f, 1.f);

    public Camera(float x, float y, float z) {
        position = new Vector4f(x, y, z, 1.f);
        target = new Vector4f();
        front = new Vector4f();
        up = new Vector4f();
        right = new Vector4f();
        rotation = new Matrix4f();
        view = new Matrix4f();
        rotationX = 0.f;
        rotationY = 0.f;
        updateVectors();
    }

    public void applyMouseMove(float deltaX, float deltaY) {
        rotationX += deltaY;
        rotationY += deltaX;
        rotation.identity();
        rotation.rotate(rotationX, 1.f, 0.f, 0.f);
        rotation.rotate(rotationY, 0.f, 1.f, 0.f);
        rotation.transpose();
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
        unitForward.mul(rotation, front);
        // right = rotation * unitRight
        unitRight.mul(rotation, right);
        // up = rotation * unitUp
        unitUp.mul(rotation, up);
    }
}
