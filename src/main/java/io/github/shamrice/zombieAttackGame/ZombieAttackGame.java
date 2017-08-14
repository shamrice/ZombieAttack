package io.github.shamrice.zombieAttackGame;

import io.github.shamrice.zombieAttackGame.configuration.ConfigurationBuilder;
import io.github.shamrice.zombieAttackGame.core.GameEngine;
import org.newdawn.slick.*;



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
        try {
            AppGameContainer app = new AppGameContainer(new ZombieAttackGame());
            app.setDisplayMode(1024, 768, false);
            app.setTargetFrameRate(60);
            app.start();
        }
        catch (SlickException slickEx) {
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
