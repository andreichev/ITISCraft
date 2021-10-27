package ru.itis.itiscraft.window;

public interface Window {
    void initialize(String title, int width, int height, boolean isFullscreen);
    void terminate();

    boolean isShouldClose();
    void setShouldClose(boolean flag);
    void swapBuffers();
}
