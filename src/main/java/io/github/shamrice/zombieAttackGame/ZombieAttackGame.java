package io.github.shamrice.zombieAttackGame;

import io.github.shamrice.zombieAttackGame.configuration.ConfigurationBuilder;
import io.github.shamrice.zombieAttackGame.core.GameEngine;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by Erik on 7/20/2017.
 */
public class ZombieAttackGame extends BasicGame {

    private final boolean isDebug = true;
    private GameEngine gameEngine;

    public ZombieAttackGame() {
        super("Zombie Attack Game");
    }

    public static void main(String[] arguments) {

        boolean fullscreen = false;

        if (arguments.length > 0) {
            if (arguments[0].toLowerCase().equals("--full") || arguments[0].toLowerCase().equals("-f")) {
                fullscreen = true;
            }
        }

        try {

            //TODO : Update this to use config value to point to libs directory
            File lwjgLibDirectory = null;

            switch(LWJGLUtil.getPlatform())
            {
                case LWJGLUtil.PLATFORM_WINDOWS:
                    lwjgLibDirectory = new File("target/classes/libs/windows/");
                    break;

                case LWJGLUtil.PLATFORM_LINUX:
                    lwjgLibDirectory = new File("target/classes/libs/linux/");
                    break;

                case LWJGLUtil.PLATFORM_MACOSX:
                    lwjgLibDirectory = new File("target/classes/libs/macosx/");
                    break;

                default:
                    throw new FileNotFoundException("Unable to set LWJG Library based on system platform: " + LWJGLUtil.getPlatformName());
            }

            System.setProperty("org.lwjgl.librarypath", lwjgLibDirectory.getAbsolutePath());

            AppGameContainer app = new AppGameContainer(new ZombieAttackGame());
            app.setDisplayMode(1024, 768, fullscreen);
            app.setTargetFrameRate(60);
            app.start();
        }
        catch (Exception slickEx) {
            slickEx.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        if (!isDebug)
            container.setShowFPS(false);

        try {

            gameEngine = GameEngine.getInstance();
            gameEngine.setConfiguration(ConfigurationBuilder.build());
            gameEngine.configure();

        } catch (Exception ex) {
            ex.printStackTrace();
            container.exit();
        }

        System.out.println("Game initialization complete. Starting...");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        //check if dead or quit requested
        if (gameEngine.isGameOver()) {
            System.out.println("Exiting...");
            container.exit();
        }

        Input input = container.getInput();

        gameEngine.handlePlayerInput(input, delta);
        gameEngine.handleEnemyUpdate(delta);
        gameEngine.handleProjectiles(delta);

    }

    public void render(GameContainer container, Graphics g) throws SlickException {

        gameEngine.renderScene();

    }

}
