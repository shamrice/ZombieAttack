package io.github.shamrice.zombieAttackGame;

import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.ConfigurationBuilder;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Erik on 7/20/2017.
 */
public class ZombieAttackGame extends BasicGame {

    private final boolean isDebug = true;

    private PlayerActor player;
    private TiledMap testMap;
    private Boolean collisionMap[][];

    public ZombieAttackGame() {
        super("Zombie Attack Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new ZombieAttackGame());
            app.setDisplayMode(800, 600, false);
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

            Configuration configuration = ConfigurationBuilder.build();

            player = configuration.getConfiguredPlayerActor();
            player.setxPos(34);
            player.setyPos(34);

            testMap = new TiledMap("assets/test.tmx");

            collisionMap = new Boolean[testMap.getWidth()][testMap.getHeight()];
            for (int x = 0; x < testMap.getWidth(); x++) {
                for (int y = 0; y < testMap.getHeight(); y++) {

                    // int tileId = testMap.getTileId(x, y, 0);
                    collisionMap[x][y] = testMap.getTileId(x, y, 0) == 31;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            container.exit();
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        Input input = container.getInput();

        Directions moveDirection = Directions.NONE;

        //if shift, they're running.
        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            player.setRunning(true);
        } else {
            player.setRunning(false);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
                moveDirection = Directions.UP;
        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            moveDirection = Directions.DOWN;
        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            moveDirection = Directions.LEFT;
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            moveDirection = Directions.RIGHT;
        } else if (input.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }

        player.move(moveDirection, delta);
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

        testMap.render(0, 0);

        player.getCurrentAnimation().draw(
                player.getxPos(),
                player.getyPos()
        );
    }
}
