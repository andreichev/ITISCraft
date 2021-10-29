package ru.itis.itiscraft.events;

public interface Events {
    void initialize();
    void pollEvents();

    boolean isKeyPressed(Key key);
    boolean isKeyJustPressed(Key key);
    boolean isMouseButtonClicked(MouseButton button);
    boolean isMouseButtonJustClicked(MouseButton button);
    boolean isCursorLocked();
    void toggleCursorLock();
    void addWindowSizeDelegate(WindowSizeDelegate delegate);
    void removeWindowSizeDelegate(WindowSizeDelegate delegate);
    float getDeltaX();
    float getDeltaY();
}
