package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private int health;
    private boolean isAlive;

    public PlayerActor(AssetConfiguration assetConfiguration) {
        super(assetConfiguration);

        this.health = 100;
        this.isAlive = true;
    }

    public void decreaseHealth(int amount) {
        health -= amount;

        //debug
        System.out.println("CURRENT HEALTH: " + health);

        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return this.health >= 0;
    }

}
