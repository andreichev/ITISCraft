package ru.itis.gengine.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.base.Direction;

import java.util.HashSet;
import java.util.Set;

public class Transform extends Component {
    private final Vector3f rotation;
    private final Vector4f position;
    private final Set<TransformDelegate> delegates;
    /// Переменные для совершения вычислений новой позиции
    private final Matrix4f rotationMatrix;
    private final Vector4f front;
    private final Vector4f up;
    private final Vector4f right;

    public Transform() {
        delegates = new HashSet<>();
        position = new Vector4f();
        rotation = new Vector3f();
        rotationMatrix = new Matrix4f();
        front = new Vector4f();
        up = new Vector4f();
        right = new Vector4f();
        updateVectors();
    }

    // MARK: - Public methods

    public void rotate(float x, float y, float z) {
        rotation.add(x, y, z);
        updateVectors();
        transformUpdated();
    }

    // Сдвиг объекта относительно себя в заданном направлении
    public void translate(Direction direction, float units) {
        switch (direction) {
            case Forward:
                position.add(front.mul(units));
                break;
            case Backward:
                position.sub(front.mul(units));
                break;
            case Left:
                position.sub(right.mul(units));
                break;
            case Right:
                position.add(right.mul(units));
                break;
        }
        transformUpdated();
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z, 1.f);
        transformUpdated();
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
        position.w = 1.f;
        transformUpdated();
    }

    public Vector4f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void addDelegate(TransformDelegate delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(TransformDelegate delegate) {
        delegates.remove(delegate);
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

    // MARK: - Private methods
    // Обновление нарпавлений единичных векторов
    private void updateVectors() {
        rotationMatrix.identity();
        rotationMatrix.rotate(rotation.x, 1.f, 0.f, 0.f);
        rotationMatrix.rotate(rotation.y, 0.f, 1.f, 0.f);
        rotationMatrix.rotate(rotation.z, 0.f, 0.f, 1.f);
        rotationMatrix.transpose();
        // front = rotation * unitForward
        Direction.unitForward.mul(rotationMatrix, front);
        // right = rotation * unitRight
        Direction.unitRight.mul(rotationMatrix, right);
        // up = rotation * unitUp
        Direction.unitUp.mul(rotationMatrix, up);
    }

    private void transformUpdated() {
        for(TransformDelegate delegate: delegates) {
            delegate.transformChanged(position, rotation);
        }
    }
}
