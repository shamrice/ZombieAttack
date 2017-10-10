package io.github.shamrice.zombieAttackGame.actors.player;

import io.github.shamrice.zombieAttackGame.actors.Actor;
import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.projectiles.ProjectileBuilder;
import io.github.shamrice.zombieAttackGame.actors.statistics.PlayerStatistics;
import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemTypes;
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

        int projectileAttackDamage = inventory.getEquippedItem(InventoryItemTypes.WEAPON).getAttackValue();
        return (playerStatistics.getBaseAttackDamage() + projectileAttackDamage);
    }

    public void attack() {

        Projectile currentProjectile = getCurrentProjectile();

        if (!currentProjectile.isActive()) {
            currentProjectile.setActive(true);
            currentProjectile.setxPos(xPos);
            currentProjectile.setyPos(yPos);

            if (getCurrentDirection() != Directions.NONE) {
                currentProjectile.setDirection(getCurrentDirection());
            }

            currentAnimation = assetConfiguration.getAnimation(getAttackImageTypeForCurrentDirection());
        }
    }

    public boolean isAlive() {
        return this.playerStatistics.getCurrentHealth() > 0;
    }

    public Projectile getCurrentProjectile() {
        InventoryItem equippedWeapon = inventory.getEquippedItem(InventoryItemTypes.WEAPON);
        return equippedWeapon.getProjectile();
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
