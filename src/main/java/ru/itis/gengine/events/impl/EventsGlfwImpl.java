package ru.itis.gengine.events.impl;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.events.MouseButton;
import ru.itis.gengine.events.WindowSizeDelegate;
import ru.itis.gengine.window.Window;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class EventsGlfwImpl implements Events {
    private boolean[] keys;
    private int[] frames;
    private int current = 0;
    private float deltaX = 0.f;
    private float deltaY = 0.f;
    private float x = 0.f;
    private float y = 0.f;
    private boolean cursorLocked = false;
    private boolean cursorStarted = false;
    private Window window;
    private final Set<WindowSizeDelegate> windowSizeDelegates = new HashSet<>();

    private final int MOUSE_BUTTONS = 1024;

    public EventsGlfwImpl() {
        this.keys = new boolean[1032];
        this.frames = new int[1032];
    }

    @Override
    public void initialize() {
        long windowHandle = window.getWindowHandle();
        keys = new boolean[1032];
        frames = new int[1032];

        glfwSetKeyCallback(windowHandle, (long w, int key, int scancode, int action, int mode) -> {
            keys[key] = action == GLFW_PRESS || action == GLFW_REPEAT;
            frames[key] = current;
        });
        glfwSetCursorPosCallback(windowHandle, (long w, double xPos, double yPos) -> {
            if (cursorStarted) {
                deltaX += xPos - x;
                deltaY += yPos - y;
            } else {
                cursorStarted = true;
            }
            x = (float) xPos;
            y = (float) yPos;
        });
        glfwSetMouseButtonCallback(windowHandle, (long w, int button, int action, int mode) -> {
            keys[MOUSE_BUTTONS + button] = action == GLFW_PRESS || action == GLFW_REPEAT;
            frames[MOUSE_BUTTONS + button] = current;
        });
        glfwSetWindowSizeCallback(windowHandle, (long w, int width, int height) -> {
            GSize size = new GSize(width, height);
            for(WindowSizeDelegate delegate: windowSizeDelegates) {
                delegate.sizeChanged(size);
            }
        });
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

    @Override
    public void pollEvents() {
        current++;
        glfwPollEvents();
    }

    @Override
    public boolean isKeyPressed(Key key) {
        return keys[key.code];
    }

    @Override
    public boolean isKeyJustPressed(Key key) {
        return keys[key.code] && frames[key.code] == current;
    }

    @Override
    public boolean isMouseButtonClicked(MouseButton button) {
        int index = MOUSE_BUTTONS + button.code;
        return keys[index];
    }

    @Override
    public boolean isMouseButtonJustClicked(MouseButton button) {
        int index = MOUSE_BUTTONS + button.code;
        return keys[index] && frames[index] == current;
    }

    @Override
    public boolean isCursorLocked() {
        return cursorLocked;
    }

    @Override
    public void toggleCursorLock() {
        cursorLocked = cursorLocked == false;
        resetCursorPos();
        glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, cursorLocked ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    @Override
    public void resetCursorPos() {
        cursorStarted = false;
        deltaX = 0.f;
        deltaY = 0.f;
    }

    @Override
    public float getDeltaX() {
        float deltaX = this.deltaX;
        this.deltaX = 0.f;
        return deltaX;
    }

    @Override
    public float getDeltaY() {
        float deltaY = this.deltaY;
        this.deltaY = 0.f;
        return deltaY;
    }

    @Override
    public void addWindowSizeDelegate(WindowSizeDelegate delegate) {
        windowSizeDelegates.add(delegate);
    }

    @Override
    public void removeWindowSizeDelegate(WindowSizeDelegate delegate) {
        windowSizeDelegates.remove(delegate);
    }
}
