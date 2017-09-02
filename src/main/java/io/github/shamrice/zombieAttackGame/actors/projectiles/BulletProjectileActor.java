package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.actors.Actor;
import io.github.shamrice.zombieAttackGame.actors.actorStats.ProjectileStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 8/4/2017.
 */
public class BulletProjectileActor extends Projectile {

    public BulletProjectileActor(AssetConfiguration assetConfiguration) {

        super(
                assetConfiguration,
                new ProjectileStatistics(25)
        );

        setWalkSpeedMultiplier(0.9f);
    }

    @Override
    public int getAttackDamage() {
        return this.projectileStatistics.getAttackDamage();
    }
}
