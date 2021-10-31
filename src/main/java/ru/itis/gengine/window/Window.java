package ru.itis.gengine.window;

import ru.itis.gengine.base.GSize;

public interface Window {
    void initialize(String title, GSize size, boolean isFullscreen);
    void terminate();

    double getTime();
    boolean isShouldClose();
    void setShouldClose(boolean flag);
    GSize getWindowSize();
    void swapBuffers();
}
