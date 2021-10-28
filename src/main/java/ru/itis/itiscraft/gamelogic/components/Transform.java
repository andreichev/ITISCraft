package ru.itis.itiscraft.gamelogic.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ru.itis.itiscraft.gamelogic.Component;

public class Transform extends Component {
    private final Vector3f rotation;
    private final Vector4f position;

    /// Матрицы для вычислений
    private final Matrix4f rotationMatrix;

    public Transform() {
        position = new Vector4f();
        rotation = new Vector3f();
        rotationMatrix = new Matrix4f();
    }
}
