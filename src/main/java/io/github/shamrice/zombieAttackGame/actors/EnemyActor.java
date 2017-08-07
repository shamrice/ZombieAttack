package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.inventory.DropCalculator;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;

/**
 * Created by Erik on 7/29/2017.
 */
public class EnemyActor extends Actor {

    private int health;
    private boolean isLooted = false;
    private InventoryItem itemDrop;

    public EnemyActor(AssetConfiguration assetConfiguration, int attackDamage) {
        super(assetConfiguration);

        this.health = 100;
        this.attackDamage = attackDamage;

        this.itemDrop = DropCalculator.getItemDrop(1);
    }

    @Override
    public int getAttackDamage() {
        return attackDamage;
    }

    public void decreaseHealth(int amount) {
        health -= amount;

        //debug
        System.out.println("CURRENT ENEMY: " + health);

        if (health <= 0) {
            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DEAD);
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public InventoryItem getItemDrop() {

        if (!isLooted) {
            System.out.println("Getting item from fallen enemy: " + itemDrop.getNameString());

            isLooted = true;
            return itemDrop;

        } else
            return null;
    }

}
