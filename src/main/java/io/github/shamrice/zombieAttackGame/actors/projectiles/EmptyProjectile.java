package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.actors.statistics.ProjectileStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 9/4/2017.
 */
public class EmptyProjectile extends Projectile {

    public EmptyProjectile(AssetConfiguration assetConfiguration, ProjectileStatistics projectileStatistics) {
        super(
                assetConfiguration,
                projectileStatistics
        );

        this.name = "Unarmed";

        setWalkSpeedMultiplier(0.9f);
    }

    @Override
    public int getAttackDamage() {
        return this.projectileStatistics.getAttackDamage();
    }
}
