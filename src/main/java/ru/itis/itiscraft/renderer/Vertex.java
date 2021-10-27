package ru.itis.itiscraft.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

class Vertex {
    Vector3f pos;
    Vector2f texCoords;

    Vertex(Vector3f pos, Vector2f texCoords) {
        this.pos = pos;
        this.texCoords = texCoords;
    }

    Vertex(float posX, float posY, float posZ, float texCoordX, float texCoordY) {
        this.pos = new Vector3f(posX, posY, posZ);
        this.texCoords = new Vector2f(texCoordX, texCoordY);
    }
}
