package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.actors.projectiles.BulletProjectileActor;
import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import jdk.internal.util.xml.impl.Input;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private int health;
    private boolean isAlive;
    private Projectile currentProjectile;
    private Inventory inventory;

    public PlayerActor(AssetConfiguration assetConfiguration, AssetConfiguration projectileConfig) {
        super(assetConfiguration);

        this.health = 10000; // DEBUG SET INSANELY HIGH
        this.attackDamage = 50;

        this.currentProjectile = new BulletProjectileActor(projectileConfig);

        this.inventory = new Inventory(5);
    }

    public void decreaseHealth(int amount) {
        health -= amount;

        //debug
        System.out.println("CURRENT HEALTH: " + health);

        if (health <= 0) {
            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DEAD);
        }
    }

    @Override
    public int getAttackDamage() {
        //will change based on weapon later on.
        return attackDamage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public Projectile getCurrentProjectile() {
        return currentProjectile;
    }

    public boolean addToInventory(InventoryItem inventoryItem) {

        //TODO : if blocks can be combined. Currently this way for debug messaging.

        if (!inventory.isFull()) {
            if (inventoryItem != null) {

                System.out.println("Adding " + inventoryItem.getNameString() + " to inventory.");
                System.out.println("          Item type: " + inventoryItem.getType().name());
                System.out.println("         Item Value: " + inventoryItem.getValue());

                if (!inventory.addInventoryItem(inventoryItem)) {
                    System.out.println("Failed to add item to inventory.");
                    return false;
                }
            } else {
                System.out.println("Item was null. Already looted? Item will not be added.");
                return false;
            }
        } else {
            System.out.println("Inventory currently full. Cannot be added.");
            return false;
        }

        return true;
    }

    public Inventory getInventory() {
        return inventory;
    }

}
