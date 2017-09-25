package io.github.shamrice.zombieAttackGame.configuration.assets;

import io.github.shamrice.zombieAttackGame.configuration.Configuration;
import io.github.shamrice.zombieAttackGame.configuration.definition.ConfigurationDefinitions;
import io.github.shamrice.zombieAttackGame.logger.Log;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Erik on 7/28/2017.
 */
public class AssetManager {

    private final int DEFAULT_ANIMATION_DURATION = 150;

    private boolean isAssetsBuilt;
    private Properties configProperties;
    private Map<AssetTypes, AssetConfiguration> assetConfigurationMap;

    public AssetManager(Properties configProperties) {
        isAssetsBuilt = false;
        this.configProperties = configProperties;
        assetConfigurationMap = new HashMap<AssetTypes, AssetConfiguration>();
    }

    public AssetConfiguration getAssetConfiguration(AssetTypes assetType) {
        return assetConfigurationMap.get(assetType);
    }

    public AssetConfiguration getAssetConfiguration(String assetType) {
        try {
            return assetConfigurationMap.get(AssetTypes.valueOf(assetType));
        } catch (Exception getAssetExc) {
            getAssetExc.printStackTrace();
            return null;
        }
    }

    public void buildAssets() throws SlickException, IOException {

        if (!isAssetsBuilt) {

            //get list of assets to configure and iterate through, configuring each one
            String[] assetsToConfigure = configProperties.getProperty(ConfigurationDefinitions.ASSET_CONFIGURATIONS).split(",");

            for (String assetConfig : assetsToConfigure) {

                AssetConfiguration assetConfiguration = new AssetConfiguration(assetConfig);

                //get array of images names from config, using default image if not configured.
                String[] defaultImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_DEFAULT_SUFFIX);
                String[] upImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_UP_SUFFIX);
                String[] downImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_DOWN_SUFFIX);
                String[] leftImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_LEFT_SUFFIX);
                String[] rightImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_RIGHT_SUFFIX);
                String[] deadImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_DEAD_SUFFIX);
                String[] lootedImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_LOOTED_SUFFIX);
                String[] hurtImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_HURT_SUFFIX);
                String[] attackImageNames = getImageNamesFromConfig(assetConfig, ConfigurationDefinitions.IMAGES_ATTACK_SUFFIX);

                //Load image array with images using file names from config
                Image[] defaultImages = initializeImageArray(defaultImageNames);
                Image[] upImages = initializeImageArray(upImageNames);
                Image[] downImages = initializeImageArray(downImageNames);
                Image[] leftImages = initializeImageArray(leftImageNames);
                Image[] rightImages = initializeImageArray(rightImageNames);
                Image[] deadImages = initializeImageArray(deadImageNames);
                Image[] lootedImages = initializeImageArray(lootedImageNames);
                Image[] hurtImages = initializeImageArray(hurtImageNames);
                Image[] attackImages = initializeImageArray(attackImageNames);

                //set animation durations for frames

                /* TODO : The whole animation setup should be rethought. */

                int animationDuration = DEFAULT_ANIMATION_DURATION;

                try {
                    animationDuration = Integer.parseInt(
                            configProperties.getProperty(
                                    assetConfig + ConfigurationDefinitions.ANIMATION_DURATION_PER_FRAME_SUFFIX
                            )
                    );
                } catch (NumberFormatException numFormatEx) {
                    Log.logException("Failed to set animation duration for " + assetConfig +
                            ". Defaulting to " + DEFAULT_ANIMATION_DURATION + ".",
                            numFormatEx
                    );
                }

                int[] durations = new int[rightImages.length];
                for (int i = 0; i < durations.length; i++) {
                    durations[i] = animationDuration;
                }

                //add images to config
                assetConfiguration.addImages(ImageTypes.DEFAULT, defaultImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_UP, upImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_DOWN, downImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_LEFT, leftImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_RIGHT, rightImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_DEAD, deadImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_LOOTED, lootedImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_HURT, hurtImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_ATTACK, hurtImages);
                assetConfiguration.setFrameDurations(durations);

                //add animations to config using images and frame durations

                //TODO : number of durations should be based on number of images, not hardcoded.

                assetConfiguration.addAnimation(ImageTypes.DEFAULT, new Animation(defaultImages, 1, false));

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_UP,
                        new Animation(upImages, durations, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_DOWN,
                        new Animation(downImages, durations, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_LEFT,
                        new Animation(leftImages, durations, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_RIGHT,
                        new Animation(rightImages, durations, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_DEAD,
                        new Animation(deadImages, 1, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_LOOTED,
                        new Animation(lootedImages, 1, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_HURT,
                        new Animation(hurtImages, 1, false)
                );

                assetConfiguration.addAnimation(
                        ImageTypes.IMAGE_ATTACK,
                        new Animation(attackImages, 1, false)
                );

                //put completed asset configuration in map
                try {
                    assetConfigurationMap.put(
                            AssetTypes.valueOf(assetConfig),
                            assetConfiguration
                    );
                } catch (Exception addAsssetEx) {
                    Log.logException(
                            "ERROR: Unable to add asset " + assetConfig + ".",
                            addAsssetEx
                    );
                }
            }
        }
    }

    private String[] getImageNamesFromConfig(String assetName, String propertyName) throws IOException {

        //Read image file names from config.
        String defaultImageConfigNames = configProperties.getProperty(
                assetName + ConfigurationDefinitions.IMAGES_DEFAULT_SUFFIX
        );

        //check to see if there's a default image, if not, we can't do anything.
        if (defaultImageConfigNames == null) {
            Log.logError("Default image name missing for asset " + assetName);
            throw new IOException("Missing default image name for " + assetName);
        }

        String[] defaultImageNames = defaultImageConfigNames.split(",");

        String attemptedImageConfigNames = configProperties.getProperty(
                assetName + propertyName
        );

        if (attemptedImageConfigNames == null) {
            return defaultImageNames;
        } else {
            return attemptedImageConfigNames.split(",");
        }
    }

    private Image[] initializeImageArray(String[] filenamesToUse) throws SlickException {

        //Load image array with images using file names from config
        Image[] images = new Image[filenamesToUse.length];

        for (int i = 0; i < images.length; i++) {
            images[i] = new Image(filenamesToUse[i]);
        }

        return images;

    }
}
