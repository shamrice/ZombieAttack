package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.definition.ConfigurationDefinitions;
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

        // LOAD AREA
        System.out.println("Loading area config...");

        AreaManager areaManager = null;
        File areaConfigDirectory = new File(configProperties.getProperty(ConfigurationDefinitions.AREA_CONFIG_FILES_LOCATION));
        String areaConfigLocation = areaConfigDirectory.getPath() + "/";

        if (!areaConfigDirectory.exists()) {
            System.out.println("Cannot find configured area config directory. Trying ./conf/areas/");
            areaConfigLocation = "conf/areas/";
        }

        try {

             areaManager = new AreaManager(
                    buildAreaTileFilesArray(),
                    areaConfigLocation
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Unable to resolve area config. Exiting...");
            System.exit(-1);

        }

        // BUILD CONFIGURATION
        System.out.println("Building complete configuration...");

        try {

            configuration = new Configuration(
                    buildAssetConfiguration(),
                    areaManager,
                    buildMessageBoxFont()
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to build configuration... exiting");
            System.exit(-2);
        }


        System.out.println("Configuration built successfully.");
        return configuration;
    }

    private static AssetManager buildAssetConfiguration() throws SlickException {
        AssetManager assetManager = new AssetManager(configProperties);
        assetManager.buildAssets();

        return assetManager;
    }

    private static String[][] buildAreaTileFilesArray() {

        int maxX = Integer.parseInt(
                configProperties.getProperty(
                    ConfigurationDefinitions.AREA_MAX_X
                ));

        int maxY = Integer.parseInt(
                configProperties.getProperty(
                        ConfigurationDefinitions.AREA_MAX_Y
                ));

        String[][] fileNames = new String[maxX][maxY];

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                fileNames[x][y] = configProperties.getProperty(
                        ConfigurationDefinitions.AREA_FILES_LOCATION
                ) + x + "_" + y + ".tmx";

            }
        }

        return fileNames;

    }

    private static TrueTypeFont buildMessageBoxFont() {
        String fontName = configProperties.getProperty(
                ConfigurationDefinitions.MESSAGE_BOX_FONT_NAME
        );

        Font font = new Font(fontName, Font.PLAIN, 12);

        return new TrueTypeFont(font, true);
    }
}
