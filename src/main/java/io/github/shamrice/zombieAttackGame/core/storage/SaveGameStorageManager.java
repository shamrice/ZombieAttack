package io.github.shamrice.zombieAttackGame.core.storage;

import io.github.shamrice.zombieAttackGame.actors.statistics.PlayerStatistics;
import io.github.shamrice.zombieAttackGame.core.state.GameState;
import io.github.shamrice.zombieAttackGame.core.state.area.AreaState;
import io.github.shamrice.zombieAttackGame.core.state.player.PlayerState;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemTypes;
import io.github.shamrice.zombieAttackGame.logger.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Erik on 9/9/2017.
 */
public class SaveGameStorageManager {

    public boolean saveGame(String fileName, GameState gameState) {

        Map<String, String> dataMap = new HashMap<>();

        dataMap.put(SaveFileDefinitions.PLAYER_NAME, gameState.getPlayerState().getPlayerStatistics().getName());
        dataMap.put(SaveFileDefinitions.PLAYER_X, String.valueOf(gameState.getPlayerState().getX()));
        dataMap.put(SaveFileDefinitions.PLAYER_Y, String.valueOf(gameState.getPlayerState().getY()));
        dataMap.put(SaveFileDefinitions.PLAYER_LEVEL, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getLevel()));
        dataMap.put(SaveFileDefinitions.PLAYER_LEVEL, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getLevel()));
        dataMap.put(SaveFileDefinitions.PLAYER_HEALTH_CURRENT, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getCurrentHealth()));
        dataMap.put(SaveFileDefinitions.PLAYER_HEALTH_BASE, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getBaseHealth()));
        dataMap.put(SaveFileDefinitions.PLAYER_ATTACK_BASE, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getBaseAttackDamage()));
        dataMap.put(SaveFileDefinitions.PLAYER_DEFENSE_BASE, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getBaseDefense()));
        dataMap.put(SaveFileDefinitions.PLAYER_EXPERIENCE_CURRENT, String.valueOf(gameState.getPlayerState().getPlayerStatistics().getCurrentExperience()));
        dataMap.put(
                SaveFileDefinitions.PLAYER_EXPERIENCE_TO_NEXT_LEVEL,
                String.valueOf(gameState.getPlayerState().getPlayerStatistics().getExperienceToNextLevel())
        );
        dataMap.put(SaveFileDefinitions.AREA_WORLD_NUMBER, String.valueOf(gameState.getAreaState().getCurrentWorld()));
        dataMap.put(SaveFileDefinitions.AREA_X, String.valueOf(gameState.getAreaState().getCurrentX()));
        dataMap.put(SaveFileDefinitions.AREA_Y, String.valueOf(gameState.getAreaState().getCurrentY()));
        dataMap.put(SaveFileDefinitions.INVENTORY_NUM_COINS, String.valueOf(gameState.getInventory().getNumberOfCoins()));
        dataMap.put(SaveFileDefinitions.INVENTORY_ITEM_COUNT, String.valueOf(gameState.getInventory().getNumberOfItems()));

        int itemNumber = 0;
        for (InventoryItem item : gameState.getInventory().getInventoryItemList()) {
            String itemKeyPrefix = SaveFileDefinitions.INVENTORY_ITEM_PREFIX + itemNumber;
            dataMap.put(itemKeyPrefix + SaveFileDefinitions.INVENTORY_ITEM_NAME_SUFFIX, item.getNameString());
            dataMap.put(itemKeyPrefix + SaveFileDefinitions.INVENTORY_ITEM_TYPE_SUFFIX, item.getType().name());
            dataMap.put(itemKeyPrefix + SaveFileDefinitions.INVENTORY_ITEM_VALUE_SUFFIX, String.valueOf(item.getValue()));
            dataMap.put(itemKeyPrefix + SaveFileDefinitions.INVENTORY_ITEM_DESCRIPTION_SUFFIX, item.getDescription());
            itemNumber++;
        }

        List<String> outputLineData = new ArrayList<>(); // Arrays.asList(

        for (String key : dataMap.keySet()) {
            outputLineData.add(key + "=" + dataMap.get(key));
        }

        try {
            Files.write(
                    Paths.get(fileName),
                    outputLineData,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE
            );

        } catch (IOException ioExc) {
            Log.logException("Unable to save game", ioExc);
            return false;
        }

        return true;
    }

    public GameState loadGame(String fileName) {

        Properties saveFileProperties = new Properties();

        File configFile = new File(fileName);

        if (!configFile.exists() && !configFile.isDirectory()) {
            Log.logError("Unable to find save file " + fileName);
            return null;
        }

        try {
            InputStream configInput = new FileInputStream(fileName);
            saveFileProperties.load(configInput);
            configInput.close();
        } catch (IOException ioExc) {
            Log.logException("Error loading save file " + fileName, ioExc);
            return null;
        }

        try {
            AreaState areaState = new AreaState(
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.AREA_WORLD_NUMBER).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.AREA_X).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.AREA_Y).toString())
            );

            PlayerStatistics playerStatistics = new PlayerStatistics(
                    saveFileProperties.get(SaveFileDefinitions.PLAYER_NAME).toString(),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_LEVEL).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_HEALTH_BASE).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_ATTACK_BASE).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_DEFENSE_BASE).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_EXPERIENCE_CURRENT).toString()),
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.PLAYER_EXPERIENCE_TO_NEXT_LEVEL).toString())
            );


            PlayerState playerState = new PlayerState(
                    Float.parseFloat(saveFileProperties.get(SaveFileDefinitions.PLAYER_X).toString()),
                    Float.parseFloat(saveFileProperties.get(SaveFileDefinitions.PLAYER_Y).toString()),
                    playerStatistics
            );

            Inventory inventory = new Inventory(
                    Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.INVENTORY_ITEM_COUNT).toString())
            );

            inventory.addInventoryItem(
                    new InventoryItem(
                            InventoryItemNames.COIN,
                            InventoryItemTypes.ITEM,
                            Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.INVENTORY_NUM_COINS).toString()),
                            "COINS LOADED FROM SAFE FILE"
                    )
            );

            int numItems = Integer.parseInt(saveFileProperties.get(SaveFileDefinitions.INVENTORY_ITEM_COUNT).toString());

            for (int i = 0; i < numItems; i++) {

                String currentInventoryPrefix = SaveFileDefinitions.INVENTORY_ITEM_PREFIX + i;

                inventory.addInventoryItem(
                        new InventoryItem(
                                InventoryItemNames.valueOf(
                                        saveFileProperties.get(currentInventoryPrefix +
                                                SaveFileDefinitions.INVENTORY_ITEM_NAME_SUFFIX).toString()
                                ),
                                InventoryItemTypes.valueOf(
                                        saveFileProperties.get(currentInventoryPrefix +
                                                SaveFileDefinitions.INVENTORY_ITEM_TYPE_SUFFIX).toString()
                                ),
                                Integer.parseInt(
                                        saveFileProperties.get(currentInventoryPrefix +
                                                SaveFileDefinitions.INVENTORY_ITEM_VALUE_SUFFIX).toString()
                                ),
                                saveFileProperties.get(currentInventoryPrefix +
                                        SaveFileDefinitions.INVENTORY_ITEM_DESCRIPTION_SUFFIX).toString()
                        )
                );
            }

            return new GameState(areaState, playerState, inventory);
        } catch (Exception ex) {
            Log.logException("Loading game error. Error setting up objects stored in save file", ex);
        }

        return null;
    }
}
