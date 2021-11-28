package ru.itis.gengine.gamelogic.ui;

import ru.itis.gengine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public class UIRoot {
    public final List<UINode> subnodes;
    private final Renderer renderer;

    public UIRoot(Renderer renderer) {
        this.subnodes = new ArrayList<>();
        this.renderer = renderer;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void addSubnode(UINode node) {
        subnodes.add(node);
        node.setRoot(this);
        node.initialize();
    }

    public void render() {
        for (UINode node: subnodes) {
            node.render();
        }
    }

    public void terminate() {
        for (UINode node: subnodes) {
            node.terminate();
        }
    }
}
