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

    private Boolean collisionMap[][];

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
            player.setxPos(34);
            player.setyPos(34);

            resetArea();

            //testMap = new TiledMap("assets/test.tmx");
/*
            collisionMap = new Boolean[testMap.getWidth()][testMap.getHeight()];
            for (int x = 0; x < testMap.getWidth(); x++) {
                for (int y = 0; y < testMap.getHeight(); y++) {

                    // int tileId = testMap.getTileId(x, y, 0);
                    collisionMap[x][y] = testMap.getTileId(x, y, 0) == 31;
                }
            }
*/
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

        //if shift, they're running.
        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            player.setRunning(true);
        } else {
            player.setRunning(false);
        }

        //player movement
        if (input.isKeyDown(Input.KEY_UP)) {
                player.move(Directions.UP, delta);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player.move(Directions.DOWN, delta);
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player.move(Directions.LEFT, delta);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.move(Directions.RIGHT, delta);
        }

        //check for quit
        if (input.isKeyDown(Input.KEY_ESCAPE) || input.isKeyDown(Input.KEY_Q)) {
            container.exit();
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
        for (EnemyActor enemy : enemyActors) {
            if (enemy.getxPos() > player.getxPos()) {
                enemy.move(Directions.LEFT, delta);
            } else if (enemy.getxPos() < player.getxPos()) {
                enemy.move(Directions.RIGHT, delta);
            }

            if (enemy.getyPos() > player.getyPos()) {
                enemy.move(Directions.UP, delta);
            } else if (enemy.getyPos() < player.getyPos()) {
                enemy.move(Directions.DOWN, delta);
            }

            if (enemy.getCollisionRect().intersects(
                    player.getCollisionRect()
            )) {
                player.decreaseHealth(1);
            }
        }


        //collision detection with environment
/*
        float tileLeftX = (tempX + 50) / 50;
        float tileTopY = (tempY + 50) / 50;
        float tileRightX = tempX / 50;
        float tileBottomY = tempY / 50;

        //System.out.println("TileX: " + (int)tileX + " TileY: " + (int)tileY);
        //System.out.println("tempX = " + tempX + " tempY = " + tempY);

        if (tileLeftX > 0 && tileTopY > 0 && tileRightX > 0 && tileBottomY > 0) {

            int tileIdLeft = testMap.getTileId((int) tileLeftX, (int) tileTopY, 0);
            int tileIdRight = testMap.getTileId((int) tileRightX, (int)tileBottomY, 0);
          //  System.out.println("tileId = " + tileId);

            if (tileIdLeft != 32 && tileIdRight != 32) {
                //player.setxPos(tempX);
               // player.setyPos(tempY);
                player.move(tempX, tempY, delta);
            }
        }
        */

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
