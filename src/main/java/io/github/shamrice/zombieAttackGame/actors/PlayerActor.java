package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.actors.actorStats.ActorStatistics;
import io.github.shamrice.zombieAttackGame.actors.projectiles.BulletProjectileActor;
import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.logger.Log;
import jdk.internal.util.xml.impl.Input;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private boolean isAlive;
    private Projectile currentProjectile;
    private Inventory inventory;

    public PlayerActor(AssetConfiguration assetConfiguration, AssetConfiguration projectileConfig,
                       ActorStatistics actorStatistics) {

        super(assetConfiguration, actorStatistics);

        this.currentProjectile = new BulletProjectileActor(projectileConfig);

        //TODO: build inventory correctly
        this.inventory = new Inventory(5);
    }

    public void decreaseHealth(int amount) {
        actorStatistics.decreaseHealth(amount);
        //health -= amount;

        currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_HURT);

        //debug
        Log.logDebug("CURRENT HEALTH: " + actorStatistics.getCurrentHealth());

        if (actorStatistics.getCurrentHealth() <= 0) {
            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DEAD);
        }
    }

    @Override
    public int getAttackDamage() {
        //will change based on weapon later on.
        return actorStatistics.getAttackDamage();
    }

    public void attack() {
        if (!currentProjectile.isActive()) {
            currentProjectile.setActive(true);
            currentProjectile.setxPos(xPos);
            currentProjectile.setyPos(yPos);

            if (getCurrentDirection() != Directions.NONE) {
                currentProjectile.setDirection(getCurrentDirection());
            }

            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_ATTACK);
        }
    }

    public boolean isAlive() {
        return this.actorStatistics.getCurrentHealth() > 0;
    }

    public Projectile getCurrentProjectile() {
        return currentProjectile;
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

}
