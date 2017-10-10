package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.StatisticsConfiguration;

/**
 * Created by Erik on 10/9/2017.
 */
public class ProjectileBuilder {

    private static AssetManager assetManager;
    private static StatisticsConfiguration statisticsConfiguration;

    public static void setConfigurations(AssetManager assetManagerToUse, StatisticsConfiguration statisticsConfigurationToUse) {
        assetManager = assetManagerToUse;
        statisticsConfiguration = statisticsConfigurationToUse;

    }

    public static Projectile build(ProjectileTypes projectileTypes) {
        switch (projectileTypes) {
            case BULLET:
                return new BulletProjectileActor(
                        assetManager.getAssetConfiguration(AssetTypes.BULLET_PROJECTILE),
                        statisticsConfiguration.getProjectileStatistics(ProjectileTypes.BULLET)
                );

            default:
                return new EmptyProjectile(
                        assetManager.getAssetConfiguration(AssetTypes.EMPTY_PROJECTILE),
                        statisticsConfiguration.getProjectileStatistics(ProjectileTypes.UNARMED)
                );
        }
    }
}
