package io.github.shamrice.zombieAttackGame.core;

import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.EnemyActor;
import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import org.newdawn.slick.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik on 8/3/2017.
 */
public class GameEngine {

    private static GameEngine instance = null;

    private final int MAX_X = 760;
    private final int MAX_Y = 560;
    private final int MIN_X = 20;
    private final int MIN_Y = 20;

    private boolean isConfigured = false;
    private boolean isRunning = true;

    private Configuration configuration;
    private AreaManager areaManager;
    private PlayerActor player;
    private List<EnemyActor> enemyActors;

    private GameEngine() { }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }

        return instance;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void configure() {
        if (!isConfigured) {
            areaManager = configuration.getAreaManager();
            areaManager.setCurrentAreaLocation(0, 0);

            player = configuration.getConfiguredPlayerActor();
            player.setxPos(140);
            player.setyPos(65);

            resetArea();
        }
    }

    public boolean isGameOver() {
        return !isRunning;
    }

    public void renderScene() {
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

        if (player.getCurrentProjectile().isActive()) {
            player.getCurrentProjectile().getCurrentAnimation().draw(
                    player.getCurrentProjectile().getxPos(),
                    player.getCurrentProjectile().getyPos()
            );
        }
    }

    public void handlePlayerInput(Input input, int delta) {

        //check for quit
        if (input.isKeyDown(org.newdawn.slick.Input.KEY_ESCAPE) || input.isKeyDown(org.newdawn.slick.Input.KEY_Q)) {
            isRunning = false;
        }

        if (player.isAlive()) {


            Directions attemptedDirection = Directions.NONE;

            //if shift, they're running.
            if (input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT)) {
                player.setRunning(true);
            } else {
                player.setRunning(false);
            }

            //player movement
            if (input.isKeyDown(org.newdawn.slick.Input.KEY_UP)) {
                attemptedDirection = Directions.UP;
            }
            if (input.isKeyDown(org.newdawn.slick.Input.KEY_DOWN)) {
                attemptedDirection = Directions.DOWN;
            }
            if (input.isKeyDown(org.newdawn.slick.Input.KEY_LEFT)) {
                attemptedDirection = Directions.LEFT;
            }
            if (input.isKeyDown(org.newdawn.slick.Input.KEY_RIGHT)) {
                attemptedDirection = Directions.RIGHT;
            }


            //fire projectile if not currently fired.
            if (input.isKeyDown(Input.KEY_SPACE)) {
                if (!player.getCurrentProjectile().isActive()) {
                    player.getCurrentProjectile().setActive(true);
                    player.getCurrentProjectile().setxPos(player.getxPos());
                    player.getCurrentProjectile().setyPos(player.getyPos());

                    if (player.getCurrentDirection() != Directions.NONE) {
                        player.getCurrentProjectile().setDirection(player.getCurrentDirection());
                    }
                }
            }

            // TODO: Move into own method?
            boolean areaCollision = areaManager.getCurrentArea()
                    .checkCollision(
                            player.getCollisionRect()
                    );

            boolean projectileAreaCollision = areaManager.getCurrentArea()
                    .checkCollision(
                            player.getCurrentProjectile().getCollisionRect()
                    );

            if (!areaCollision) {
                player.move(attemptedDirection, delta);
            } else {
                //hack to get unstuck from area collision.
                //player collision rect is only updated once the new xy is set so checkCollision
                //is always one move behind. Needs to be fixed.
                player.move(player.getOppositeLastDirection(), delta);
            }

            if (projectileAreaCollision) {
                player.getCurrentProjectile().setActive(false);
            }

            if (areaCollision) {
                System.out.println("Currently collided with environment. If message continues... there is an error");
            }

            checkAreaBounds();
        }
    }

    public void handleEnemyUpdate(int delta) {
        //check for enemy collisions

        if (player.isAlive()) {

            Directions enemyAttemptedHorizontal = Directions.NONE;
            Directions enemyAttemptedVertical = Directions.NONE;

            for (EnemyActor enemy : enemyActors) {

                if (enemy.isAlive()) {
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

                    //check enemy collisions
                    if (enemy
                            .getCollisionRect()
                            .intersects(player.getCollisionRect())) {

                        player.decreaseHealth(enemy.getAttackDamage());
                        //TODO : enemy attack animation

                    } else if (areaManager.getCurrentArea().checkCollision(enemy.getCollisionRect())) {
                        enemy.move(enemy.getOppositeLastDirection(), delta);

                    } else if (player.getCurrentProjectile().isActive() &&
                            enemy
                                    .getCollisionRect()
                                    .intersects(player.getCurrentProjectile().getCollisionRect())) {

                        enemy.decreaseHealth(player.getCurrentProjectile().getAttackDamage());
                        player.getCurrentProjectile().setActive(false);

                    } else {
                        enemy.move(enemyAttemptedHorizontal, delta);
                        enemy.move(enemyAttemptedVertical, delta);
                    }
                }
            }
        }

    }

    public void handleProjectiles(int delta) {

        if (player.getCurrentProjectile().isActive()) {

            player.getCurrentProjectile().move(
                    player.getCurrentProjectile().getDirection(),
                    delta
            );
        }
    }

    private void checkAreaBounds() {
        //move to next area if leaving screen.
        if (player.getxPos() > MAX_X) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX() + 1,
                    areaManager.getCurrentY()
            );
            player.setxPos(MIN_X + 10);
            resetArea();
        } else if (player.getxPos() < MIN_X) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX() - 1,
                    areaManager.getCurrentY()
            );
            player.setxPos(MAX_X - 10);
            resetArea();
        }

        if (player.getyPos() > MAX_Y) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX(),
                    areaManager.getCurrentY() + 1
            );
            player.setyPos(MIN_Y + 10);
            resetArea();
        } else if (player.getyPos() < MIN_Y) {
            areaManager.setCurrentAreaLocation(
                    areaManager.getCurrentX(),
                    areaManager.getCurrentY() - 1
            );
            player.setyPos(MAX_Y - 10);
            resetArea();
        }


        //check player projectile
        if (player.getCurrentProjectile().isActive()) {

            if (player.getCurrentProjectile().getxPos() > MAX_X ||
                    player.getCurrentProjectile().getxPos() < MIN_X ||
                    player.getCurrentProjectile().getyPos() > MAX_Y ||
                    player.getCurrentProjectile().getyPos() < MIN_Y)
            {
                player.getCurrentProjectile().setActive(false);
            }
        }
    }

    private void resetArea() {

        enemyActors = new ArrayList<EnemyActor>();

        int numEnemies = areaManager
                .getCurrentArea()
                .getAreaConfiguration()
                .getNumEnemeies();

        for (int i = 0; i < numEnemies; i++) {
            EnemyActor yarnball = new EnemyActor(configuration.getAssetConfiguration(AssetTypes.YARNBALL), 50);
            yarnball.setxPos(new Random().nextInt(MAX_X));
            yarnball.setyPos(new Random().nextInt(MAX_Y));
            yarnball.setWalkSpeedMultiplier(0.05f);
            enemyActors.add(yarnball);
        }

    }

}
