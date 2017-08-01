package io.github.shamrice.zombieAttackGame;

import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.EnemyActor;
import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.ConfigurationBuilder;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik on 7/20/2017.
 */
public class ZombieAttackGame extends BasicGame {

    private final boolean isDebug = true;

    private PlayerActor player;
    private List<EnemyActor> enemyActors;
    private AreaManager areaManager;
    private Configuration configuration;

    public ZombieAttackGame() {
        super("Zombie Attack Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new ZombieAttackGame());
            app.setDisplayMode(800, 600, false);
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

            configuration = ConfigurationBuilder.build();

            areaManager = configuration.getAreaManager();
            areaManager.setCurrentAreaLocation(0, 0);

            player = configuration.getConfiguredPlayerActor();
            player.setxPos(140);
            player.setyPos(65);

            resetArea();

        } catch (Exception ex) {
            ex.printStackTrace();
            container.exit();
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        //check if dead
        if (!player.isAlive()) {
            System.out.println("YOU'RE DEAD");
            container.exit();
        }

        Input input = container.getInput();

        Directions attemptedDirection = Directions.NONE;

        //if shift, they're running.
        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            player.setRunning(true);
        } else {
            player.setRunning(false);
        }

        //player movement
        if (input.isKeyDown(Input.KEY_UP)) {
                attemptedDirection = Directions.UP;
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            attemptedDirection = Directions.DOWN;
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            attemptedDirection = Directions.LEFT;
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            attemptedDirection = Directions.RIGHT;
        }

        //check for quit
        if (input.isKeyDown(Input.KEY_ESCAPE) || input.isKeyDown(Input.KEY_Q)) {
            container.exit();
        }

        boolean areaCollision = areaManager.getCurrentArea()
                .checkCollision(
                        player.getCollisionRect()
                );

        if (!areaCollision) {
            player.move(attemptedDirection, delta);
        } else {
            //hack to get unstuck from area collision.
            //player shouldn't have moved into collision rect but for some reason does...?
            player.move(player.getOppositeLastDirection(), delta);
        }

        if (areaCollision) {
            System.out.println("Currently collided with environment. If message continues... there is an error");
        }

        //move to next area if leaving screen.
        if (player.getxPos() > 760) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX() + 1,
                    0
            );
            player.setxPos(20);
            resetArea();
        } else if (player.getxPos() < 10) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX() - 1,
                    0
            );
            player.setxPos(750);
            resetArea();
        }

        //check for enemy collisions

        Directions enemyAttemptedHorizontal = Directions.NONE;
        Directions enemyAttemptedVertical = Directions.NONE;

        for (EnemyActor enemy : enemyActors) {

            if (enemy.getxPos() > player.getxPos()) {
                enemyAttemptedHorizontal = Directions.LEFT;

            } else if (enemy.getxPos() < player.getxPos()) {
                enemyAttemptedHorizontal = Directions.RIGHT;
            }

            if (enemy.getyPos() > player.getyPos()) {
                enemyAttemptedVertical = Directions.UP;

            } else if (enemy.getyPos() < player.getyPos()) {
                enemyAttemptedVertical = Directions.DOWN;
            }

            if (enemy.getCollisionRect().intersects(
                    player.getCollisionRect())) {

                player.decreaseHealth(1);
                enemy.move(enemy.getOppositeLastDirection(), delta);

            } else if (areaManager.getCurrentArea().checkCollision(enemy.getCollisionRect())) {
                enemy.move(enemy.getOppositeLastDirection(), delta);

            } else {
                enemy.move(enemyAttemptedHorizontal, delta);
                enemy.move(enemyAttemptedVertical, delta);
            }
        }

    }

    public void render(GameContainer container, Graphics g) throws SlickException {

        areaManager
                .getCurrentAreaTileMap()
                .render(0, 0);

        player.getCurrentAnimation().draw(
                player.getxPos(),
                player.getyPos()
        );

        for (EnemyActor enemy : enemyActors) {
            enemy.getCurrentAnimation().draw(
                    enemy.getxPos(),
                    enemy.getyPos()
            );
        }

    }

    private void resetArea() {

        enemyActors = new ArrayList<EnemyActor>();

        int numEnemies = areaManager
                .getCurrentArea()
                .getAreaConfiguration()
                .getNumEnemeies();

        for (int i = 0; i < numEnemies; i++) {
            EnemyActor yarnball = new EnemyActor(configuration.getAssetConfiguration(AssetTypes.YARNBALL));
            yarnball.setxPos(new Random().nextInt(700));
            yarnball.setyPos(new Random().nextInt(500));
            yarnball.setWalkSpeedMultiplier(0.05f);
            enemyActors.add(yarnball);
        }

    }
}
