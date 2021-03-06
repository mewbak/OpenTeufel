package org.openteufel.game;

import java.io.File;
import java.io.IOException;

import org.openteufel.file.GamedataLoader;
import org.openteufel.game.levels.LevelState1Cathedral;
import org.openteufel.game.levels.LevelState2Catacombs;
import org.openteufel.game.levels.LevelState3Caves;
import org.openteufel.game.levels.LevelState4Hell;
import org.openteufel.game.levels.LevelState5Crypt;
import org.openteufel.game.levels.LevelState6Nest;
import org.openteufel.game.levels.LevelStateTown;
import org.openteufel.ui.KeyboardEvent;
import org.openteufel.ui.KeyboardHandler;
import org.openteufel.ui.Renderer;
import org.openteufel.ui.TextRenderer;

public class GameRunner implements KeyboardHandler
{
    private final GamedataLoader dataLoader;
    private final Renderer<?>    renderer;
    private LevelRenderer        levelrenderer = null;
    private final TextRenderer   textrenderer;

    private LevelState           level         = null;
    private boolean              runGame       = true;

    private static final int     targetFps     = 20;

    public GameRunner(final Renderer<?> renderer) throws IOException
    {
        this.dataLoader = new GamedataLoader(new File("."));
        this.renderer = renderer;
        textrenderer = new TextRenderer(renderer, dataLoader);

        switchLevel(0);
        this.renderer.setTargetFps(targetFps);
        renderer.registerKeyboardHandler(this);
    }

    public void runGame() throws Exception
    {
        int gametime = 0;
        long renderTime = 0;
        while (runGame)
        {
            gametime++;
            long processStart = System.nanoTime();
            levelrenderer.applyUserInput();
            LevelState newLevel = level.runFrame(gametime);
            long processTime = (System.nanoTime() - processStart) / 1000000;

            long renderStart = System.nanoTime();
            this.renderer.startFrame();
            levelrenderer.renderFrame();

            {
                textrenderer.writeText(1, 1, "proc=" + processTime + "ms / render=" + renderTime + "ms / mem=" + (Runtime.getRuntime().totalMemory() >> 20) + "M", 16);
                textrenderer.writeText(1, 18, "pos=" + level.getCameraPos()  + " / num=" + level.getEntityManager().getNumEntities() + " / t=" + gametime, 16);
                textrenderer.writeText(1, 34, "squareId="+level.getSquareId(level.getCameraPos().getTileX()/2, level.getCameraPos().getTileY()/2), 16);
            }
            if(newLevel != null)
            {
                textrenderer.writeText(renderer.getScreenWidth()/2-(42*8), renderer.getScreenHeight()/2, "Loading ...", 42);
            }
            this.renderer.finishFrame();
            renderTime = (System.nanoTime() - renderStart) / 1000000;
            
            if(newLevel != null)
            {
                newLevel.init(dataLoader);
                level = newLevel;
                levelrenderer = new LevelRenderer(this.dataLoader, this.level, this.renderer);
            }
        }
    }

    public void switchLevel(int level)
    {
        try
        {
            switch (level)
            {
                case 0:
                    this.level = new LevelStateTown();
                    break;

                case 1:
                    this.level = new LevelState1Cathedral();
                    break;

                case 5:
                    this.level = new LevelState2Catacombs();
                    break;

                case 9:
                    this.level = new LevelState3Caves();
                    break;

                case 13:
                    this.level = new LevelState4Hell();
                    break;
                    

                case 17:
                    this.level = new LevelState5Crypt();
                    break;
                    

                case 21:
                    this.level = new LevelState6Nest();
                    break;
            }
            this.level.init(dataLoader);
            levelrenderer = new LevelRenderer(this.dataLoader, this.level, this.renderer);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleKeyboardEvent(KeyboardEvent e)
    {
        if(e.state)
        switch (e.key)
        {
            case 1:
            case 27:
                runGame = false;
                break;

            case 11:
                switchLevel(0);
                break;

            case 2:
                switchLevel(1);
                break;

            case 3:
                switchLevel(5);
                break;

            case 4:
                switchLevel(9);
                break;

            case 5:
                switchLevel(13);
                break;

            case 6:
                switchLevel(17);
                break;
                
            case 7:
                switchLevel(21);
                break;
                
            default:
                break;
        }
    }

}
