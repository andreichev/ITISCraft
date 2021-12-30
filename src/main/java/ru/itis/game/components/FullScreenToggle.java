package ru.itis.game.components;

import ru.itis.gengine.events.Events;
import ru.itis.gengine.events.Key;
import ru.itis.gengine.gamelogic.Component;
import ru.itis.gengine.window.Window;

public class FullScreenToggle extends Component {
    private Window window;
    private Events events;

    @Override
    public void initialize() {
        window = getEntity().getWindow();
        events = getEntity().getEvents();
    }

    @Override
    public void update(float deltaTime) {
        if(events.isKeyJustPressed(Key.F)) {
            window.setFullScreen(window.isFullScreen() == false);
        }
    }
}
