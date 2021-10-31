package ru.itis.gengine.window.impl;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import ru.itis.gengine.base.GSize;
import ru.itis.gengine.window.Window;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowGlfwImpl implements Window {
    private long windowHandle;
    GLFWErrorCallback errorCallback;

    @Override
    public void initialize(String title, GSize size, boolean isFullscreen) {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        errorCallback = GLFWErrorCallback.createPrint(System.out);
        glfwSetErrorCallback(errorCallback);

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() == false)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        if (isFullscreen) {
            glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
            glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE);
        } else {
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        }

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Create the window
        windowHandle = glfwCreateWindow(
                isFullscreen ? vidmode.width() * 3 : (int) size.width,
                isFullscreen ? vidmode.height() * 3 : (int) size.height, title,
                isFullscreen ? glfwGetPrimaryMonitor() : NULL, NULL
        );
        if (windowHandle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        if (isFullscreen == false) {
            // Center the window
            glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - (int) size.width) / 2,
                    (vidmode.height() - (int) size.height) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);

        if(isFullscreen == false) {
            // Make the window visible
            glfwShowWindow(windowHandle);
            glfwFocusWindow(windowHandle);
        }
    }

    @Override
    public void terminate() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public GSize getWindowSize() {
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(windowHandle, widthBuffer, heightBuffer);
        int width = widthBuffer.get(0);
        int height = heightBuffer.get(0);
        return new GSize((float) width, (float) height);
    }

    @Override
    public boolean isShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    @Override
    public void setShouldClose(boolean flag) {
        glfwSetWindowShouldClose(windowHandle, flag);
    }

    @Override
    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    public long getWindowHandle() {
        return windowHandle;
    }
}
