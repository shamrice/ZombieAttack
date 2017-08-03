package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import jdk.internal.util.xml.impl.Input;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private int health;
    private boolean isAlive;

    public PlayerActor(AssetConfiguration assetConfiguration) {
        super(assetConfiguration);

        this.health = 10000; // DEBUG SET INSANELY HIGH
        this.attackDamage = 50;
    }

    public void decreaseHealth(int amount) {
        health -= amount;

        //debug
        System.out.println("CURRENT HEALTH: " + health);
    }

    @Override
    public int getAttackDamage() {
        //will change based on weapon later on.
        return attackDamage;
    }

    public boolean isAlive() {
        return this.health >= 0;
    }




}
