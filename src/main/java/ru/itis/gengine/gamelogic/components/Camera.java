package ru.itis.gengine.gamelogic.components;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.WindowSizeDelegate;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.renderer.Shader;

public class Camera extends Component implements WindowSizeDelegate {
    private Vector4f target;
    private Matrix4f view;
    private Matrix4f projection;
    private Shader shader;
    private float fieldOfView;
    private GSize windowSize;
    private Transform transform;

    @Override
    public void initialize() {
        this.windowSize = getEntity().getWindow().getWindowSize();
        target = new Vector4f();
        view = new Matrix4f();
        projection = new Matrix4f();
        transform = getEntity().getTransform();
        getEntity().getEvents().addWindowSizeDelegate(this);
    }

    @Override
    public void update(long deltaTime) {
        // target = position + front
        transform.getPosition().add(transform.getFront(), target);
        view.identity();
        view.lookAt(
                transform.getPosition().x, transform.getPosition().y, transform.getPosition().z,
                target.x, target.y, target.z,
                transform.getUp().x, transform.getUp().y, transform.getUp().z
        );
        shader.use();
        shader.setUniform("view", view);
    }

    @Override
    public void terminate() {
        getEntity().getEvents().removeWindowSizeDelegate(this);
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void setFieldOfView(float degrees) {
        fieldOfView = degrees;
        updateProjectionMatrix();
    }

    @Override
    public void sizeChanged(GSize size) {
        windowSize = size;
        updateProjectionMatrix();
    }

    private void updateProjectionMatrix() {
        projection.identity();
        projection.perspective(
                Math.toRadians(fieldOfView),
                windowSize.width / windowSize.height,
                0.1f, 1000.0f
        );
        shader.use();
        shader.setUniform("projection", projection);
    }
}
