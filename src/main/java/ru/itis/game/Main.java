package ru.itis.game;

import ru.itis.game.levels.GameLevel;
import ru.itis.gengine.application.Application;
import ru.itis.gengine.application.ApplicationStartupSettings;
import ru.itis.gengine.base.GSize;

public class Main {
    public static void main(String[] args) {
        Application.shared.run(ApplicationStartupSettings.builder()
                .name("ITIS Craft")
                .windowTitle("ITIS CRAFT")
                .windowSize(new GSize(900, 600))
                .startupLevel(new GameLevel())
                .isFullScreen(false)
                .build()
        );
    }
}
