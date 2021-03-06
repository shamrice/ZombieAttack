package io.github.shamrice.zombieAttackGame.core;

import io.github.shamrice.zombieAttackGame.actors.Actor;
import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.enemies.EnemyActor;
import io.github.shamrice.zombieAttackGame.actors.player.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.EnemyTypes;
import io.github.shamrice.zombieAttackGame.core.state.GameState;
import io.github.shamrice.zombieAttackGame.core.state.area.AreaState;
import io.github.shamrice.zombieAttackGame.core.state.player.PlayerState;
import io.github.shamrice.zombieAttackGame.core.storage.SaveGameStorageManager;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.messaging.InventoryDialogBox;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemTypes;
import io.github.shamrice.zombieAttackGame.logger.Log;
import io.github.shamrice.zombieAttackGame.messaging.MessageBox;
import io.github.shamrice.zombieAttackGame.messaging.StatisticsMessageBox;
import org.newdawn.slick.Input;

import javax.swing.*;
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
    private boolean isInInventory = false;
    private boolean isPaused = false;

    private Configuration configuration;
    private AreaManager areaManager;
    private PlayerActor player;
    private MessageBox messageBox;
    private InventoryDialogBox inventoryDialogBox;
    private StatisticsMessageBox statisticsMessageBox;
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

            statisticsMessageBox = new StatisticsMessageBox(configuration.getStatisticsBoxConfig());

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

        //draw mask over play area if game is paused.
        if (isPaused) {
            configuration
                    .getAssetConfiguration(AssetTypes.GAMEPLAY_MASK)
                    .getAnimation(ImageTypes.DEFAULT)
                    .draw(1, 1);
        }

        messageBox.draw();
        statisticsMessageBox.draw(
                player.getPlayerStatistics(),
                player.getInventory().getEquippedItem(InventoryItemTypes.WEAPON));
        inventoryDialogBox.draw(player.getInventory());


    }

    public void handlePlayerInput(Input input, int delta) {
        //check for quit
        if (input.isKeyDown(org.newdawn.slick.Input.KEY_ESCAPE) || input.isKeyDown(org.newdawn.slick.Input.KEY_Q)) {
            isRunning = false;
        }

        //pause button.
        if (input.isKeyPressed(Input.KEY_PAUSE)) {

            if (isPaused) {
                messageBox.write("Game unpaused.");
                isPaused = false;
            } else {
                messageBox.write("Game paused.");
                isPaused = true;
            }
        }

        if (player.isAlive()) {

            if (isInInventory) {
                handleInventoryInput(input);
            } else {

                //if shift, they're running.
                if (input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT)) {
                    player.setRunning(true);
                } else {
                    player.setRunning(false);
                }

                //player movement
                if (input.isKeyDown(org.newdawn.slick.Input.KEY_UP)) {
                    if (!checkActorAreaCollision(player, Directions.UP, delta)) {
                        player.move(Directions.UP, delta);
                    }
                }
                if (input.isKeyDown(org.newdawn.slick.Input.KEY_DOWN)) {
                    if (!checkActorAreaCollision(player, Directions.DOWN, delta)) {
                        player.move(Directions.DOWN, delta);
                    }
                }
                if (input.isKeyDown(org.newdawn.slick.Input.KEY_LEFT)) {
                    if (!checkActorAreaCollision(player, Directions.LEFT, delta)) {
                        player.move(Directions.LEFT, delta);
                    }
                }
                if (input.isKeyDown(org.newdawn.slick.Input.KEY_RIGHT)) {
                    if (!checkActorAreaCollision(player, Directions.RIGHT, delta)) {
                        player.move(Directions.RIGHT, delta);
                    }
                }

                //fire projectile if not currently fired.
                if (input.isKeyDown(Input.KEY_SPACE)) {
                    player.attack();
                }

                //get item from fallen enemy.
                //TODO: Move to own method.
                if (input.isKeyDown(Input.KEY_G)) {
                    for (EnemyActor enemy : enemyActors) {
                        if (enemy
                                .getCollisionRect()
                                .intersects(player.getCollisionRect()) &&
                                !enemy.isAlive()) {

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

                //debug display inventory item
                if (input.isKeyPressed(Input.KEY_I)) {

                    isInInventory = true;
                    isPaused = true;
                    messageBox.write("Game paused for inventory input.");
/*
                    messageBox.write("INVENTORY:");
                    messageBox.write("----------");

                    for (int i = 0; i < player.getInventory().getNumberOfItems(); i++) {
                        InventoryItem item = player.getInventory().getInventoryItem(i);

                        Log.logDebug("SLOT " + i + ":");
                        Log.logDebug("  ITEM NAME: " + item.getNameString());
                        Log.logDebug("  ITEM TYPE: " + item.getType().name());
                        Log.logDebug(" ITEM VALUE: " + item.getValue());
                        Log.logDebug("DESCRIPTION: \n" + item.getDescription() + "\n");

                        messageBox.write(item.getNameString());
                    }

                    messageBox.write("You have " + player.getInventory().getNumberOfCoins() + " coin(s).");
                    */
                }

                if (input.isKeyPressed(Input.KEY_T)) {
                    messageBox.write("TEST BUTTON PRESSED");
                }

                //debug for now. just equip the first weapon in the inventory.
                if (input.isKeyPressed(Input.KEY_E)) {
                    for (InventoryItem item : player.getInventory().getInventoryItemList()) {
                        if (item.getType() == InventoryItemTypes.WEAPON) {
                            item.setEquipped(true);

                            messageBox.write("Equipped item " + item.getNameString());
                            break;
                        }
                    }
                }

                // Save game
                if (input.isKeyPressed(Input.KEY_F2)) {
                    saveGame();
                }

                //load game
                if (input.isKeyPressed(Input.KEY_F3)) {
                    loadGame();
                }

                boolean projectileAreaCollision = areaManager.getCurrentArea()
                        .checkCollision(
                                player.getCurrentProjectile().getCollisionRect()
                        );

                if (projectileAreaCollision) {
                    player.getCurrentProjectile().setActive(false);
                }

                checkAreaBounds();
            }
        }
    }

    public void handleEnemyUpdate(int delta) {
        //check for enemy collisions

        if (player.isAlive() && !isPaused) {

            for (EnemyActor enemy : enemyActors) {

                if (enemy.isAlive()) {

                    //enemy movement
                    if (enemy.getxPos() > player.getxPos()) {
                        if (!checkActorAreaCollision(enemy, Directions.LEFT, delta)) {
                            enemy.move(
                                    enemy.directionRandomizer(Directions.LEFT),
                                    delta
                            );
                        }
                    }

                    if (enemy.getxPos() < player.getxPos()) {
                        if (!checkActorAreaCollision(enemy, Directions.RIGHT, delta)) {
                            enemy.move(
                                    enemy.directionRandomizer(Directions.RIGHT),
                                    delta
                            );
                        }
                    }

                    if (enemy.getyPos() > player.getyPos()) {
                        if (!checkActorAreaCollision(enemy, Directions.UP, delta)) {
                            enemy.move(
                                    enemy.directionRandomizer(Directions.UP),
                                    delta
                            );
                        }
                    }

                    if (enemy.getyPos() < player.getyPos()) {
                        if (!checkActorAreaCollision(enemy, Directions.DOWN, delta)) {
                            enemy.move(
                                    enemy.directionRandomizer(Directions.DOWN),
                                    delta
                            );
                        }
                    }

                    //check enemy collisions
                    if (enemy
                            .getCollisionRect()
                            .intersects(player.getCollisionRect())) {

                        enemy.attack();
                        int amountDamaged = player.decreaseHealth(enemy.getAttackDamage());
                        messageBox.write(enemy.getName() + " attacks you for " + amountDamaged + " damage.");

                    } else if (player.getCurrentProjectile().isActive() &&
                            enemy
                                    .getCollisionRect()
                                    .intersects(player.getCurrentProjectile().getCollisionRect())) {

                        int amountDamaged = enemy.decreaseHealth(player.getAttackDamage());
                        player.getCurrentProjectile().setActive(false);

                        messageBox.write("You attack " + enemy.getName() +
                                " for " + amountDamaged + " damage.");

                        if (!enemy.isAlive()) {
                            messageBox.write(enemy.getName() + " has expired.");
                            player.addExperience(enemy.getExperienceWorth());
                        }
                    }
                }
            }
        }
    }

    public void handleProjectiles(int delta) {

        if (player.getCurrentProjectile().isActive() && !isPaused) {

            player.getCurrentProjectile().move(
                    player.getCurrentProjectile().getDirection(),
                    delta
            );

            player.getCurrentProjectile().increaseDistanceTravelled(delta);
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

                //TODO: Build enemy stats correctly
                EnemyActor yarnball = new EnemyActor(
                        configuration.getAssetConfiguration(AssetTypes.YARNBALL),
                        configuration.getStatisticsConfiguration().getEnemyStatistics(EnemyTypes.YARNBALL)
                );

                //TODO : start positions should be set in configs
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

    private boolean checkActorAreaCollision(Actor actor, Directions attemptedDirection, int delta) {
        return areaManager.getCurrentArea()
                .checkCollision(
                        actor.getCollisionRectAtDirection(attemptedDirection, delta)
                );
    }

    private void saveGame() {

        SaveGameStorageManager saveGameStorageManager = new SaveGameStorageManager();

        GameState gameState = new GameState(
                new AreaState(
                        areaManager.getCurrentWorld(),
                        areaManager.getCurrentX(),
                        areaManager.getCurrentY()
                ),
                new PlayerState(
                        player.getxPos(),
                        player.getyPos(),
                        player.getPlayerStatistics()
                ),
                player.getInventory()
        );

        //TODO : Eventually move this from a JOptionPane to a more formal dialog.
        String fileName = JOptionPane.showInputDialog(null, "Select filename to save as: ");

        if (fileName != null) {

            if (!saveGameStorageManager.saveGame(fileName, gameState)) {
                messageBox.write("Unable to save game. Please check logs.");
                Log.logError("Unable to save game to file " + fileName);
            } else {
                messageBox.write("Game saved to file " + fileName + ".");
                Log.logInfo("Game saved to file " + fileName);
            }
        }
    }

    private void loadGame() {

        //TODO : Eventually move this from a JOptionPane to a more formal dialog.
        String fileName = JOptionPane.showInputDialog(null, "Select filename to load: ");

        SaveGameStorageManager saveGameStorageManager = new SaveGameStorageManager();
        GameState loadedGameState = saveGameStorageManager.loadGame(fileName);

        if (loadedGameState != null) {
            areaManager.setCurrentWorld(loadedGameState.getAreaState().getCurrentWorld());
            areaManager.setCurrentAreaLocation(
                    loadedGameState.getAreaState().getCurrentX(),
                    loadedGameState.getAreaState().getCurrentY()
            );

            player = new PlayerActor(
                    configuration.getAssetConfiguration(AssetTypes.PLAYER),
                    loadedGameState.getPlayerState().getPlayerStatistics()
            );

            player.setxPos(loadedGameState.getPlayerState().getX());
            player.setyPos(loadedGameState.getPlayerState().getY());

            //add inventory items back from save file.
            for (InventoryItem item : loadedGameState.getInventory().getInventoryItemList()) {
                player.addToInventory(item);
            }

            messageBox.write("Game loaded from file " + fileName);
            Log.logInfo("Loaded game from file " + fileName);

            resetArea();

        } else {
            messageBox.write("Failed to load game save from file " + fileName);
            Log.logError("Unable to load game from file " + fileName);
        }
    }

    private void handleInventoryInput(Input input) {

        if (input.isKeyPressed(Input.KEY_DOWN)) {
            inventoryDialogBox.selectNextItem(player.getInventory().getNumberOfItems());

        } else if (input.isKeyPressed(Input.KEY_UP)) {

            inventoryDialogBox.selectPreviousItem(player.getInventory().getNumberOfItems());

        } else if (input.isKeyPressed(Input.KEY_ENTER)) {
            if (inventoryDialogBox.getItemNumSelected() > -1) {

                player.equipInventoryItem(inventoryDialogBox.getItemNumSelected());
            }

        } else if (input.isKeyPressed(Input.KEY_DELETE)) {
            player.removeFromInventory(
                    player.getInventory().getInventoryItem(inventoryDialogBox.getItemNumSelected())
            );
        }

        if (input.isKeyDown(Input.KEY_BACK)) {
            isInInventory = false;
            isPaused = false;
            messageBox.write("Game game unpaused.");
        }

    }

}
