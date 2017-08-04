package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.actors.projectiles.BulletProjectileActor;
import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import jdk.internal.util.xml.impl.Input;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private int health;
    private boolean isAlive;
    private Projectile currentProjectile;

    public PlayerActor(AssetConfiguration assetConfiguration, AssetConfiguration projectileConfig) {
        super(assetConfiguration);

        this.health = 10000; // DEBUG SET INSANELY HIGH
        this.attackDamage = 50;

        this.currentProjectile = new BulletProjectileActor(projectileConfig);
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


}
