package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.actors.actorStats.PlayerStatistics;
import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.logger.Log;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private Inventory inventory;
    private PlayerStatistics playerStatistics;

    public PlayerActor(AssetConfiguration assetConfiguration, PlayerStatistics playerStatistics) {
        super(assetConfiguration);

        this.playerStatistics = playerStatistics;

        //TODO: build inventory correctly
        this.inventory = new Inventory(5);
    }

    public int decreaseHealth(int amount) {
        int amountDamaged = playerStatistics.decreaseHealth(amount);

        currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_HURT);

        //debug
        Log.logDebug("CURRENT HEALTH: " + playerStatistics.getCurrentHealth());

        if (playerStatistics.getCurrentHealth() <= 0) {
            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DEAD);
        }

        return amountDamaged;
    }

    @Override
    public int getAttackDamage() {
        //will change based on weapon later on.
        return playerStatistics.getAttackDamage();
    }

    public int getCurrentAttackDamage() {
        return playerStatistics.getAttackDamage();
    }

    public void attack() {
        if (!playerStatistics.getCurrentProjectile().isActive()) {
            playerStatistics.getCurrentProjectile().setActive(true);
            playerStatistics.getCurrentProjectile().setxPos(xPos);
            playerStatistics.getCurrentProjectile().setyPos(yPos);

            if (getCurrentDirection() != Directions.NONE) {
                playerStatistics.getCurrentProjectile().setDirection(getCurrentDirection());
            }

            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_ATTACK);
        }
    }

    public boolean isAlive() {
        return this.playerStatistics.getCurrentHealth() > 0;
    }

    public void setCurrentProjectile(Projectile newProjectile) {
        playerStatistics.setProjectile(newProjectile);
    }

    public Projectile getCurrentProjectile() {
        return playerStatistics.getCurrentProjectile();
    }

    public boolean addToInventory(InventoryItem inventoryItem) {

        //TODO : if blocks can be combined. Currently this way for debug messaging.

        if (inventoryItem != null) {

            Log.logDebug("Adding " + inventoryItem.getNameString() + " to inventory.");
            Log.logDebug("          Item type: " + inventoryItem.getType().name());
            Log.logDebug("         Item Value: " + inventoryItem.getValue());

            if (!inventory.addInventoryItem(inventoryItem)) {
                Log.logDebug("Failed to add item to inventory.");
                return false;
            }
        } else {
            Log.logDebug("Item was null. Already looted? Item will not be added.");
            return false;
        }


        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addExperience(int amount) {
        playerStatistics.addExperience(amount);
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public void setPlayerStatistics(PlayerStatistics playerStatistics) {
        this.playerStatistics = playerStatistics;
    }
}
