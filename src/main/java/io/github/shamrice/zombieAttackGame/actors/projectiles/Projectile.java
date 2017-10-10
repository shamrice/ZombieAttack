package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.actors.Actor;
import io.github.shamrice.zombieAttackGame.actors.Directions;
import io.github.shamrice.zombieAttackGame.actors.statistics.ProjectileStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;


/**
 * Created by Erik on 8/4/2017.
 */
public abstract class Projectile extends Actor {

    private boolean isActive = false;
    private Directions direction;
    private float distanceTravelled = 0;

    protected ProjectileStatistics projectileStatistics;

    public Projectile(AssetConfiguration assetConfiguration, ProjectileStatistics projectileStatistics) {
        super(assetConfiguration);

        this.projectileStatistics = projectileStatistics;

        //default direction
        this.direction = Directions.RIGHT;
    }

    public void increaseDistanceTravelled(float distanceDelta) {
        this.distanceTravelled += distanceDelta;

        //if greater than max allowed, stop being active.
        if (distanceTravelled >= projectileStatistics.getMaxDistance()) {
            setActive(false);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        distanceTravelled = 0;
        isActive = active;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public Directions getDirection() {
        return this.direction;
    }
}
