package org.openteufel.game;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import org.openteufel.file.GamedataLoader;
import org.openteufel.game.levels.LevelStateTown;
import org.openteufel.ui.renderer.gl.ClassicGLRenderer;
import org.openteufel.ui.Renderer;

public class GameRunner
{
    private final GamedataLoader dataLoader;
    private final Renderer<?>    renderer;

    private boolean runGame = true;

    public GameRunner(final Renderer<?> renderer) throws IOException
    {
        this.dataLoader = new GamedataLoader(new File("."));
        this.renderer = renderer;
    }

    public void runGame() throws Exception
    {
        final LevelState level = new LevelStateTown(this.dataLoader);
        final LevelRenderer levelrenderer = new LevelRenderer(this.dataLoader, level, this.renderer);

        int gametime = 0;
        while (runGame)
        {
            gametime++;
            levelrenderer.applyUserInput();
            switch (renderer.processKeyboard()) {
                case 1:
                case 27:
                    runGame = false;
                    break;
                default:
                    break;
            }
            level.runFrame(gametime);

            levelrenderer.renderFrame();
        }
    }


}
