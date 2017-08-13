package io.github.shamrice.zombieAttackGame.core;

import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.EnemyActor;
import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.inventory.InventoryDialogBox;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.logger.ConsoleLogger;
import io.github.shamrice.zombieAttackGame.logger.FileLogger;
import io.github.shamrice.zombieAttackGame.messaging.MessageBox;
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
    private MessageBox messageBox;
    private InventoryDialogBox inventoryDialogBox;
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

            messageBox = new MessageBox(configuration.getMessageBoxConfig());

            inventoryDialogBox = new InventoryDialogBox(configuration.getInventoryMessageBoxConfig());

            player = configuration.getConfiguredPlayerActor();
            player.setyPos(65);
            player.setxPos(140);
            player.setName("Pepper");

            messageBox.write("Welcome to the world of evil yarnballs. Enjoy your stay!");

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

        player.getCurrentAnimation().draw(
                player.getxPos(),
                player.getyPos()
        );

        messageBox.draw();
        inventoryDialogBox.draw(player.getInventory());

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
                player.attack();
            }

            //get item from fallen enemy.
            if (input.isKeyDown(Input.KEY_G)) {
                for (EnemyActor enemy : enemyActors) {
                    if (enemy
                            .getCollisionRect()
                            .intersects(player.getCollisionRect()) &&

                            !enemy.isAlive())
                    {

                        if (!enemy.isLooted()) {

                            InventoryItem itemToAdd = enemy.getItemDrop();

                            if (itemToAdd != null) {
                                if (player.addToInventory(itemToAdd)) {
                                    if (itemToAdd.getName() == InventoryItemNames.COIN) {
                                        messageBox.write(String.valueOf(itemToAdd.getValue()) + " " + itemToAdd.getName() +
                                            "(s) have been added to you coins."
                                        );
                                    } else {
                                        messageBox.write(itemToAdd.getNameString() + " has been added to your inventory.");
                                    }
                                } else {
                                    messageBox.write(itemToAdd.getNameString() +
                                            " cannot be picked up. Free up space in inventory.");
                                }
                            }
                        } else {
                            // TODO: this floods the messagebox with too many of the same messages.
                            //messageBox.write("There is nothing to be picked up.");
                        }
                    }
                }
            }

            //debug display inventory items
            if (input.isKeyDown(Input.KEY_I)) {

                messageBox.write("INVENTORY:");
                messageBox.write("----------");

                for (int i = 0; i < player.getInventory().getNumberOfItems(); i++) {
                    InventoryItem item = player.getInventory().getInventoryItem(i);

                    System.out.println("SLOT " + i + ":");
                    System.out.println("  ITEM NAME: " + item.getNameString());
                    System.out.println("  ITEM TYPE: " + item.getType().name());
                    System.out.println(" ITEM VALUE: " + item.getValue());
                    System.out.println("DESCRIPTION: \n" + item.getDescription() + "\n");

                    messageBox.write(item.getNameString());
                }

                messageBox.write("You have " + player.getInventory().getNumberOfCoins() + " coin(s).");
            }

            if (input.isKeyDown(Input.KEY_T)) {
                messageBox.write("TEST BUTTON PRESSED");
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

                        enemy.attack();
                        player.decreaseHealth(enemy.getAttackDamage());
                        messageBox.write(enemy.getName() + " attacks you for " + enemy.getAttackDamage() + " damage.");

                    } else if (areaManager.getCurrentArea().checkCollision(enemy.getCollisionRect())) {
                        enemy.move(enemy.getOppositeLastDirection(), delta);

                    } else if (player.getCurrentProjectile().isActive() &&
                            enemy
                                    .getCollisionRect()
                                    .intersects(player.getCurrentProjectile().getCollisionRect())) {

                        enemy.decreaseHealth(player.getCurrentProjectile().getAttackDamage());
                        player.getCurrentProjectile().setActive(false);

                        messageBox.write("You attack " + enemy.getName() +
                                " for " + player.getAttackDamage() + " damage.");

                        if (!enemy.isAlive()) {
                            messageBox.write(enemy.getName() + " has expired.");
                        }


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

        //set area enemies if they haven't already be done. (Needs to be done when walking to new area)
        if (!areaManager.getCurrentArea().getAreaConfiguration().isEnemiesSet()) {

            List<EnemyActor> tempEnemyActors = new ArrayList<EnemyActor>();

            int numEnemies = areaManager
                    .getCurrentArea()
                    .getAreaConfiguration()
                    .getNumEnemeies();

            for (int i = 0; i < numEnemies; i++) {
                EnemyActor yarnball = new EnemyActor(configuration.getAssetConfiguration(AssetTypes.YARNBALL), 10);
                yarnball.setxPos(new Random().nextInt(MAX_X));
                yarnball.setyPos(new Random().nextInt(MAX_Y));
                yarnball.setWalkSpeedMultiplier(0.05f);
                yarnball.setName("Yarnball");
                tempEnemyActors.add(yarnball);
            }

            areaManager.getCurrentArea().getAreaConfiguration().setEnemyActors(tempEnemyActors);

        }

        enemyActors = areaManager.getCurrentArea().getAreaConfiguration().getEnemyActors();

    }

}
