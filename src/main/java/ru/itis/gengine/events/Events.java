package ru.itis.gengine.events;

import ru.itis.gengine.window.Window;

public interface Events {
    void initialize();
    void setWindow(Window window);
    void pollEvents();

    boolean isKeyPressed(Key key);
    boolean isKeyJustPressed(Key key);
    boolean isMouseButtonClicked(MouseButton button);
    boolean isMouseButtonJustClicked(MouseButton button);
    boolean isCursorLocked();
    void toggleCursorLock();
    void addWindowSizeDelegate(WindowSizeDelegate delegate);
    void removeWindowSizeDelegate(WindowSizeDelegate delegate);
    void resetCursorPos();
    float getDeltaX();
    float getDeltaY();
}
