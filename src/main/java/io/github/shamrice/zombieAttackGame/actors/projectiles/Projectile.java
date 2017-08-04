package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.actors.Actor;
import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;


/**
 * Created by Erik on 8/4/2017.
 */
public abstract class Projectile extends Actor {

    private boolean isActive = false;
    private Directions direction;

    public Projectile(AssetConfiguration assetConfiguration) {
        super(assetConfiguration);

        //default direction
        this.direction = Directions.RIGHT;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public Directions getDirection() {
        return this.direction;
    }
}