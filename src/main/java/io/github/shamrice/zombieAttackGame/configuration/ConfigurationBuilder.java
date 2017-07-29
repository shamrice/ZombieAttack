package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.definition.ConfigurationDefinitions;
import org.newdawn.slick.SlickException;

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

        //TODO: this try catch nested garbage is disgusting.

        try {
            InputStream configInput = new FileInputStream(ConfigurationDefinitions.CONFIGURATION_FILE_LOCATION);
            configProperties.load(configInput);
            configInput.close();
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
            System.out.println("Trying current directory...");
            try {
                InputStream configInput = new FileInputStream("conf/config.properties");
                configProperties.load(configInput);
                configInput.close();
            } catch (IOException ioExc2) {
                ioExc2.printStackTrace();
                System.out.println("Failed to find config... exiting.");
                System.exit(-1);
            }
        }

        try {

            configuration = new Configuration(
                    buildAssetConfiguration()
            );

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to build configuration... exiting");
            System.exit(-2);
        }

        return configuration;
    }

    private static AssetManager buildAssetConfiguration() throws SlickException {
        AssetManager assetManager = new AssetManager(configProperties);
        assetManager.buildAssets();

        return assetManager;
    }
}
