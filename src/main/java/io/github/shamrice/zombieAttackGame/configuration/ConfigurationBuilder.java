package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.actors.projectiles.BulletProjectileActor;
import io.github.shamrice.zombieAttackGame.actors.projectiles.EmptyProjectile;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.areas.WorldsConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.configuration.definition.ConfigurationDefinitions;
import io.github.shamrice.zombieAttackGame.configuration.logger.LogTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InventoryBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.messaging.MessageBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.messaging.StatisticsBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;
import io.github.shamrice.zombieAttackGame.configuration.statistics.StatisticsConfiguration;
import io.github.shamrice.zombieAttackGame.logger.Log;
import io.github.shamrice.zombieAttackGame.logger.LogLevel;
import io.github.shamrice.zombieAttackGame.logger.types.ConsoleLogger;
import io.github.shamrice.zombieAttackGame.logger.types.FileLogger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Erik on 7/23/2017.
 */
public class ConfigurationBuilder {

    private static Configuration configuration = null;
    private static Properties configProperties = new Properties();

    public static Configuration build() throws IOException {

        //LOAD BASE CONFIG
        System.out.println("Loading base config...");

        File configFile = new File(ConfigurationDefinitions.CONFIGURATION_FILE_LOCATION);
        String configPath = configFile.getPath();

        if (!configFile.exists() && !configFile.isDirectory()) {
            System.out.println("Cannot find default configuration location. Trying ./conf/config.properties");
            configPath = "conf/config.properties";
        }

        try {
            InputStream configInput = new FileInputStream(configPath);
            configProperties.load(configInput);
            configInput.close();
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
            System.out.println("Failed to find config... exiting.");
            System.exit(-1);
        }

        //Set up logger
        String logType = configProperties.getProperty(ConfigurationDefinitions.LOG_TYPE);
        LogLevel logLevel = LogLevel.valueOf(configProperties.getProperty(ConfigurationDefinitions.LOG_LEVEL));

        if (logType.equals(LogTypes.CONSOLE.name())) {
            Log.setLogger(new ConsoleLogger(), logLevel);
        } else if (logType.equals(LogTypes.FILE.name())) {
            Log.setLogger(
                    new FileLogger(configProperties.getProperty(ConfigurationDefinitions.LOG_FILENAME)),
                    logLevel
            );
        }

        // LOAD AREA
        Log.logInfo("Loading area configs and building world configuration...");

        AreaManager areaManager = null;

        try {
             areaManager = new AreaManager(buildWorldsConfiguration());
        } catch (Exception ex) {
            Log.logException("Unable to resolve area config. Exiting...", ex);
            System.exit(-1);

        }

        //Asset configs
        Log.logInfo("Building assets...");
        AssetManager assetManager = null;

        try {
            assetManager = buildAssetConfiguration();
        } catch (SlickException slickExc) {
            Log.logException("Error building assets configuration", slickExc);
            System.exit(-1);
        }

        //Information Box Configs
        Log.logInfo("Building information box Configurations...");

        TrueTypeFont trueTypeFont = buildMessageBoxFont();

        MessageBoxConfig messageBoxConfig = new MessageBoxConfig(
                assetManager.getAssetConfiguration(AssetTypes.MESSAGE_BOX),
                trueTypeFont
        );

        InventoryBoxConfig inventoryBoxConfig = new InventoryBoxConfig(
                assetManager.getAssetConfiguration(AssetTypes.INVENTORY),
                trueTypeFont
        );

        StatisticsBoxConfig statisticsBoxConfig = new StatisticsBoxConfig(
                assetManager.getAssetConfiguration(AssetTypes.STATS_BOX),
                trueTypeFont
        );

        Log.logInfo("Building actor statistics configurations...");

        StatisticsConfiguration statisticsConfiguration = null;
        try {
            statisticsConfiguration = buildStatisticsConfiguration();
        } catch (IOException ioExc) {
            Log.logException("Unable to build actor statistics", ioExc);
            System.exit(-1);
        }

        //PlayerConfig
        Log.logInfo("Building player configuration...");

        PlayerActor playerActor = new PlayerActor(
                assetManager.getAssetConfiguration(AssetTypes.PLAYER),
                statisticsConfiguration.getPlayerStatistics()
        );

        //TODO : Maybe change? currently sets to empty projectile.
        playerActor.setCurrentProjectile(
                new BulletProjectileActor(
                        assetManager.getAssetConfiguration(AssetTypes.BULLET_PROJECTILE),
                        statisticsConfiguration.getProjectileStatistics(ProjectileTypes.BULLET)
                )
                /*
                new EmptyProjectile(
                        assetManager.getAssetConfiguration(AssetTypes.EMPTY_PROJECTILE),
                        statisticsConfiguration.getProjectileStatistics(ProjectileTypes.UNARMED)
                )
                */
        );

        // BUILD CONFIGURATION
        Log.logInfo("Building complete configuration...");
        try {

            configuration = new Configuration(
                    buildAssetConfiguration(),
                    areaManager,
                    playerActor,
                    messageBoxConfig,
                    inventoryBoxConfig,
                    statisticsBoxConfig,
                    statisticsConfiguration
            );

        } catch (Exception ex) {
            Log.logException("Failed to build configuration... exiting", ex);
            System.exit(-2);
        }

        Log.logInfo("Configuration built successfully.");
        return configuration;
    }

    private static AssetManager buildAssetConfiguration() throws SlickException {
        AssetManager assetManager = new AssetManager(configProperties);
        assetManager.buildAssets();

        return assetManager;
    }

    private static WorldsConfiguration buildWorldsConfiguration() {

        int numWorlds = Integer.parseInt(
                configProperties.getProperty(
                        ConfigurationDefinitions.AREA_NUM_WORLDS
                )
        );

        int maxX = Integer.parseInt(
                configProperties.getProperty(
                    ConfigurationDefinitions.AREA_MAX_X
                ));

        int maxY = Integer.parseInt(
                configProperties.getProperty(
                        ConfigurationDefinitions.AREA_MAX_Y
                ));

        String[][][] fileNames = new String[numWorlds][maxX][maxY];

        for (int w = 0; w < numWorlds; w++) {
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    fileNames[w][x][y] = configProperties.getProperty(
                            ConfigurationDefinitions.AREA_FILES_LOCATION
                    ) + w + "_" + x + "_" + y + ".tmx";

                }
            }
        }

        File areaConfigDirectory = new File(configProperties.getProperty(ConfigurationDefinitions.AREA_CONFIG_FILES_LOCATION));
        String areaConfigLocation = areaConfigDirectory.getPath() + "/";

        if (!areaConfigDirectory.exists()) {
            Log.logError("Cannot find configured area config directory. Trying ./conf/areas/");
            areaConfigLocation = "conf/areas/";
        }

        WorldsConfiguration worldsConfiguration = new WorldsConfiguration(numWorlds, maxX, maxY, areaConfigLocation);
        worldsConfiguration.setTileFileNames(fileNames);

        return worldsConfiguration;

    }

    private static TrueTypeFont buildMessageBoxFont() {
        String fontName = configProperties.getProperty(
                ConfigurationDefinitions.MESSAGE_BOX_FONT_NAME
        );

        Font font = new Font(fontName, Font.PLAIN, 12);

        return new TrueTypeFont(font, true);
    }

    private static StatisticsConfiguration buildStatisticsConfiguration() throws IOException {

        String[] listOfEnemyItems = configProperties
                .getProperty(ConfigurationDefinitions.STATISTICS_CONFIGURED_ENEMIES)
                .split(",");

        String[] listOfProjectileItems = configProperties
                .getProperty(ConfigurationDefinitions.STATISTICS_CONFIGURED_PROJECTILES)
                .split(",");


        File statsConfigDirectory = new File(configProperties.getProperty(
                ConfigurationDefinitions.STATISTICS_CONFIG_FILES_LOCATION)
        );

        String configDirectory = statsConfigDirectory.getPath() + "/";

        if (!statsConfigDirectory.exists()) {
            Log.logError("Cannot find configured stats config directory. Trying ./conf/stats/");
            configDirectory = "conf/stats/";
        }

        StatisticsConfiguration statsConfig = new StatisticsConfiguration(
                configDirectory, listOfEnemyItems, listOfProjectileItems
        );

        statsConfig.build();

        return statsConfig;
    }


}
