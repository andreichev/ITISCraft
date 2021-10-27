package ru.itis.itiscraft.events;

public interface Events {
    void initialize();
    void pollEvents();

    boolean isKeyPressed(Key key);
    boolean isKeyJustPressed(Key key);
    boolean isMouseButtonClicked(MouseButton button);
    boolean isMouseButtonJustClicked(MouseButton button);
    void toggleCursorLock();
    float getDeltaX();
    float getDeltaY();
}
