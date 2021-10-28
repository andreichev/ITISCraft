package ru.itis.itiscraft.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.joml.Math;
import ru.itis.itiscraft.gamelogic.Component;

public class Camera extends Component {
    private final Vector4f target;
    private final Matrix4f view;
    private final Matrix4f projection;

    private Transform transform;

    public Camera() {
        target = new Vector4f();
        view = new Matrix4f();
        projection = new Matrix4f();
        projection.perspective(Math.toRadians(90.f), 1.0f, 0.1f, 1000.0f);
    }

    @Override
    public void initialize() {
        transform = getEntity().getTransform();
    }

    public Matrix4f getView() {
        // target = position + front
        transform.getPosition().add(transform.getFront(), target);
        view.identity();
        return view.lookAt(
                transform.getPosition().x, transform.getPosition().y, transform.getPosition().z,
                target.x, target.y, target.z,
                transform.getUp().x, transform.getUp().y, transform.getUp().z
        );
    }

    public void setFieldOfView(float degrees) {
        projection.perspective(Math.toRadians(degrees), 1.0f, 0.1f, 1000.0f);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
