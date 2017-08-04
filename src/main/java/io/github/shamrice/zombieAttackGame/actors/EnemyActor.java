package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 7/29/2017.
 */
public class EnemyActor extends Actor {

    private int health;

    public EnemyActor(AssetConfiguration assetConfiguration, int attackDamage) {
        super(assetConfiguration);

        this.health = 100;
        this.attackDamage = attackDamage;
    }

    @Override
    public int getAttackDamage() {
        return attackDamage;
    }

    public void decreaseHealth(int amount) {
        health -= amount;

        //debug
        System.out.println("CURRENT ENEMY: " + health);
    }

    public boolean isAlive() {
        return this.health > 0;
    }

}
