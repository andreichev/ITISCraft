package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.base.GSize;
import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.WindowSizeDelegate;
import ru.itis.gengine.renderer.Renderer;
import ru.itis.gengine.window.Window;

import java.util.ArrayList;
import java.util.List;

public class UINode implements WindowSizeDelegate {
    private final List<UINode> subnodes;
    protected Window window;
    protected Events events;
    protected Renderer renderer;
    protected GSize windowSize;

    public UINode() {
        this.subnodes = new ArrayList<>();
    }

    public void configure(Renderer renderer, Window window, Events events) {
        this.renderer = renderer;
        this.window = window;
        this.events = events;
        windowSize = window.getWindowSize();
        events.addWindowSizeDelegate(this);
        layout();
    }

    public void render() {
        if(renderer == null) { return; }
        draw();
        for(UINode node: subnodes) {
            node.draw();
        }
    }

    public void addSubnode(UINode node) {
        node.configure(renderer, window, events);
        subnodes.add(node);
    }

    public void removeSubnode(UINode node) {
        if(subnodes.remove(node)) {
            node.terminate();
        }
    }

    public List<UINode> getSubnodes() {
        return subnodes;
    }

    @Override
    public void sizeChanged(GSize size) {
        windowSize = size;
        layout();
    }

    public void layout() {}
    public void draw() {}
    public void terminate() {}
}
