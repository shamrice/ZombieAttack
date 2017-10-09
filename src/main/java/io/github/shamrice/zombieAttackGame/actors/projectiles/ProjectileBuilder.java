package io.github.shamrice.zombieAttackGame.actors.projectiles;

import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;

/**
 * Created by Erik on 10/9/2017.
 */
public class ProjectileBuilder {

    private static Configuration configuration;

    public static void setConfiguration(Configuration configurationToUse) {
        configuration = configurationToUse;
    }

    public static Projectile build(ProjectileTypes projectileTypes, int itemValueAmount) {
        switch (projectileTypes) {
            case BULLET:
                return new BulletProjectileActor(
                        configuration.getAssetConfiguration(AssetTypes.BULLET_PROJECTILE),
                        configuration.getStatisticsConfiguration().getProjectileStatistics(ProjectileTypes.BULLET),
                        itemValueAmount
                );

            default:
                return new EmptyProjectile(
                        configuration.getAssetConfiguration(AssetTypes.EMPTY_PROJECTILE),
                        configuration.getStatisticsConfiguration().getProjectileStatistics(ProjectileTypes.UNARMED)
                );
        }
    }
}
