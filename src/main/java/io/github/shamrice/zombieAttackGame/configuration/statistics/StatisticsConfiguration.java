package io.github.shamrice.zombieAttackGame.configuration.statistics;

import io.github.shamrice.zombieAttackGame.actors.actorStats.EnemyStatistics;
import io.github.shamrice.zombieAttackGame.actors.actorStats.PlayerStatistics;
import io.github.shamrice.zombieAttackGame.actors.actorStats.ProjectileStatistics;
import io.github.shamrice.zombieAttackGame.logger.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Erik on 9/4/2017.
 */
public class StatisticsConfiguration {

    private boolean configBuilt = false;

    private PlayerStatistics configuredPlayerStatistics;
    private List<EnemyStatistics> configuredEnemyStatisticsList;
    private List<ProjectileStatistics> configuredProjectileStatisticsList;
    private String configDirectory;
    private String[] listOfConfiguredEnemies;
    private String[] listOfConfiguredProjectiles;


    public StatisticsConfiguration(String statsConfigDirectory, String[] listOfConfiguredEnemies,
                                   String[] listOfConfiguredProjectiles) {

        this.configDirectory = statsConfigDirectory;
        this.listOfConfiguredEnemies = listOfConfiguredEnemies;
        this.listOfConfiguredProjectiles = listOfConfiguredProjectiles;

        this.configuredEnemyStatisticsList = new ArrayList<>();
        this.configuredProjectileStatisticsList = new ArrayList<>();

    }

    public void build() throws IOException {
        if (!configBuilt) {

            //build player stats
            configuredPlayerStatistics = buildPlayerStatistics();

            //build enemy stats
            for (String enemyName : listOfConfiguredEnemies) {
                configuredEnemyStatisticsList.add(
                        buildEnemyStatistics(enemyName)
                );
            }

            //build projectiles
            for (String projectileName : listOfConfiguredProjectiles) {
                configuredProjectileStatisticsList.add(
                        buildProjectileStatistics(projectileName)
                );
            }

        } else {
            Log.logInfo("Attempted to build statistics after statistics were already built.");
        }
    }

    public PlayerStatistics getPlayerStatistics() {
        return configuredPlayerStatistics;
    }

    public EnemyStatistics getEnemyStatistics(EnemyTypes enemyType) {

        for (EnemyStatistics enemyStatistic : configuredEnemyStatisticsList) {
            if (enemyStatistic.getName().equals(enemyType.name().toLowerCase())) {
                return new EnemyStatistics(
                        enemyStatistic.getName(),
                        enemyStatistic.getLevel(),
                        enemyStatistic.getCurrentHealth(),
                        enemyStatistic.getAttackDamage(),
                        enemyStatistic.getDefense(),
                        enemyStatistic.getExperienceWorth(),
                        enemyStatistic.getRandomizerPercentage()
                );
            }
        }

        Log.logError("Unable to find enemy statistics for enemy " + enemyType.name());
        return null;
    }

    public ProjectileStatistics getProjectileStatistics(ProjectileTypes projectileType) {

        for (ProjectileStatistics projectileStatistic : configuredProjectileStatisticsList) {
            if (projectileStatistic.getName().equals(projectileType.name().toLowerCase())) {
                return new ProjectileStatistics(
                        projectileStatistic.getName(),
                        projectileStatistic.getAttackDamage(),
                        projectileStatistic.getMaxDistance()
                );
            }
        }

        Log.logError("Unable to find enemy statistics for projectile " + projectileType.name());
        return null;
    }

    private PlayerStatistics buildPlayerStatistics() throws IOException {

        PlayerStatistics playerStatistics = null;

        String playerStatPropertyFile = configDirectory + "player/player.properties";

        Properties playerStatProperties = new Properties();
        InputStream configInput = new FileInputStream(playerStatPropertyFile);
        playerStatProperties.load(configInput);
        configInput.close();

        String playerPrefixString = StatisticsConfigurationDefinitions.PLAYER_PREFIX;

        try {
            int health = Integer.parseInt(
                    playerStatProperties.getProperty(
                            playerPrefixString +
                                    StatisticsConfigurationDefinitions.PLAYER_HEALTH_SUFFIX
                    )
            );

            int attackDamage = Integer.parseInt(
                    playerStatProperties.getProperty(
                            playerPrefixString + StatisticsConfigurationDefinitions.PLAYER_ATTACK_DAMAGE_SUFFIX
                    )
            );

            int defense = Integer.parseInt(
                    playerStatProperties.getProperty(
                            playerPrefixString + StatisticsConfigurationDefinitions.PLAYER_DEFENSE_SUFFIX
                    )
            );

            playerStatistics = new PlayerStatistics("PLAYER", 1, health, attackDamage, defense);

        } catch (NumberFormatException numFormatEx) {
            Log.logException(
                    "Unable to parse player stats config",
                    numFormatEx
            );
        }

        return playerStatistics;

    }

    private ProjectileStatistics buildProjectileStatistics(String projectileName) throws IOException {

        ProjectileStatistics projectileStatistics = null;

        String projectileStatPropertyFile = configDirectory + "projectiles/" +
                projectileName.toLowerCase() + ".properties";

        Properties projectileStatProperties = new Properties();
        InputStream configInput = new FileInputStream(projectileStatPropertyFile);
        projectileStatProperties.load(configInput);
        configInput.close();

        String projectilePrefixString = StatisticsConfigurationDefinitions.PROJECTILES_PREFIX + projectileName;

        try {

            int attackDamage = Integer.parseInt(
                    projectileStatProperties.getProperty(
                            projectilePrefixString + StatisticsConfigurationDefinitions.PROJECTILES_ATTACK_DAMAGE_SUFFIX
                    )
            );

            int maxDistance = Integer.parseInt(
                    projectileStatProperties.getProperty(
                            projectilePrefixString + StatisticsConfigurationDefinitions.PROJECTILES_MAX_DISTANCE_SUFFIX
                    )
            );

            projectileStatistics = new ProjectileStatistics(projectileName, attackDamage, maxDistance);

        } catch (NumberFormatException numFormatEx) {
            Log.logException(
                    "Unable to parse projectile " + projectileName + " stats config file " +
                    projectileStatPropertyFile + ". Prefix string used: " + projectilePrefixString + ".",
                    numFormatEx
            );
        }

        return projectileStatistics;
    }

    private EnemyStatistics buildEnemyStatistics(String enemyName)
            throws IOException {

        EnemyStatistics enemyStatistics = null;

        String enemyStatPropertyFile = configDirectory + "enemies/" +
                enemyName.toLowerCase() + ".properties";

        Properties enemyStatProperties = new Properties();
        InputStream configInput = new FileInputStream(enemyStatPropertyFile);
        enemyStatProperties.load(configInput);
        configInput.close();

        String enemyPrefixString = StatisticsConfigurationDefinitions.ENEMY_PREFIX + enemyName;

        try {
            int health = Integer.parseInt(
                    enemyStatProperties.getProperty(
                            enemyPrefixString +
                            StatisticsConfigurationDefinitions.ENEMY_HEALTH_SUFFIX
                    )
            );

            int attackDamage = Integer.parseInt(
                    enemyStatProperties.getProperty(
                            enemyPrefixString + StatisticsConfigurationDefinitions.ENEMY_ATTACK_DAMAGE_SUFFIX
                    )
            );

            int defense = Integer.parseInt(
                    enemyStatProperties.getProperty(
                            enemyPrefixString + StatisticsConfigurationDefinitions.ENEMY_DEFENSE_SUFFIX
                    )
            );

            int expWorth = Integer.parseInt(
                    enemyStatProperties.getProperty(
                            enemyPrefixString + StatisticsConfigurationDefinitions.ENEMY_EXPERIENCE_WORTH_SUFFIX
                    )
            );

            int randomizerPercent = Integer.parseInt(
                    enemyStatProperties.getProperty(
                            enemyPrefixString + StatisticsConfigurationDefinitions.ENEMY_RANDOMIZER_PERCENTAGE_SUFFIX
                    )
            );

            enemyStatistics = new EnemyStatistics(enemyName, 1, health, attackDamage, defense, expWorth, randomizerPercent);

        } catch (NumberFormatException numFormatEx) {
            Log.logException(
                    "Unable to parse enemy " + enemyName + " stats config",
                    numFormatEx
            );
        }

        return enemyStatistics;

    }

}
