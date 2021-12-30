package ru.itis.game.components.ui;

import ru.itis.gengine.base.GRect;
import ru.itis.gengine.gamelogic.ui.UINode;
import ru.itis.gengine.gamelogic.ui.UIView;

public class UICrosshair extends UINode {
    private UIView horizontal;
    private UIView vertical;

    public UICrosshair() {
        addSubviews();
    }

    public void addSubviews() {
        horizontal = new UIView();
        vertical = new UIView();
        addSubnode(horizontal);
        addSubnode(vertical);
    }

    @Override
    public void layout() {
        horizontal.setFrame(new GRect(windowSize.width / 2 - 25, windowSize.height / 2 - 2, 50, 4));
        vertical.setFrame(new GRect(windowSize.width / 2 - 2, windowSize.height / 2 - 25, 4, 50));
    }
}
