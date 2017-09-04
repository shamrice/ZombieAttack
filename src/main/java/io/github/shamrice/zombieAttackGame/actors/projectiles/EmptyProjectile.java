package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.actors.actorStats.ProjectileStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.logger.Log;

/**
 * Created by Erik on 9/4/2017.
 */
public class EmptyProjectile extends Projectile {

    public EmptyProjectile(AssetConfiguration assetConfiguration) {
        super(
                assetConfiguration,
                new ProjectileStatistics("EMPTY", 0)
        );

        setWalkSpeedMultiplier(0.9f);
    }

    @Override
    public int getAttackDamage() {
        Log.logDebug("Returning value from EMPTY: " + projectileStatistics.getAttackDamage());
        return this.projectileStatistics.getAttackDamage();
    }
}
