package ru.itis.gengine.gamelogic.ui;

public class UINode {
    public UIRoot root;

    public UIRoot getRoot() {
        return root;
    }

    public void setRoot(UIRoot root) {
        this.root = root;
    }

    public void initialize() {}
    public void render() {}
    public void terminate() {}
}
