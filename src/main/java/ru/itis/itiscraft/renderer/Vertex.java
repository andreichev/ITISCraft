package ru.itis.itiscraft.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
    Vector3f pos;
    Vector2f texCoords;
    float light;

    public Vertex(Vector3f pos, Vector2f texCoords, float light) {
        this.pos = pos;
        this.texCoords = texCoords;
        this.light = light;
    }

    public Vertex(float posX, float posY, float posZ, float texCoordX, float texCoordY, float light) {
        this.pos = new Vector3f(posX, posY, posZ);
        this.texCoords = new Vector2f(texCoordX, texCoordY);
        this.light = light;
    }
}
