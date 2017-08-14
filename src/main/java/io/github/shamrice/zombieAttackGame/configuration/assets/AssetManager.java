package io.github.shamrice.zombieAttackGame.configuration.assets;

import io.github.shamrice.zombieAttackGame.configuration.definition.ConfigurationDefinitions;
import io.github.shamrice.zombieAttackGame.logger.Log;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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

    public void buildAssets() throws SlickException {

        if (!isAssetsBuilt) {

            //get list of assets to configure and iterate through, configuring each one
            String[] assetsToConfigure = configProperties.getProperty(ConfigurationDefinitions.ASSET_CONFIGURATIONS).split(",");

            for (String assetConfig : assetsToConfigure) {

                AssetConfiguration assetConfiguration = new AssetConfiguration(assetConfig);

                /* TODO:
                        only build asset configuration for images that exist. Currently this requires
                       redundant images for each image possibility. (Even if they aren't used)
                */

                //Read image file names from config.
                String[] upImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_UP_SUFFIX
                ).split(",");

                String[] downImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_DOWN_SUFFIX
                ).split(",");


                String[] leftImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_LEFT_SUFFIX
                ).split(",");


                String[] rightImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_RIGHT_SUFFIX
                ).split(",");

                String[] deadImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_DEAD_SUFFIX
                ).split(",");

                String[] hurtImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_HURT_SUFFIX
                ).split(",");

                String[] attackImageNames = configProperties.getProperty(
                        assetConfig + ConfigurationDefinitions.IMAGES_ATTACK_SUFFIX
                ).split(",");

                //Load image array with images using file names from config
                Image[] upImages = new Image[upImageNames.length];
                Image[] downImages = new Image[downImageNames.length];
                Image[] leftImages = new Image[leftImageNames.length];
                Image[] rightImages = new Image[rightImageNames.length];
                Image[] deadImages = new Image[deadImageNames.length];
                Image[] hurtImages = new Image[hurtImageNames.length];
                Image[] attackImages = new Image[attackImageNames.length];

                for (int i = 0; i < upImageNames.length; i++) {
                    upImages[i] = new Image(upImageNames[i]);
                }

                for (int i = 0; i < downImageNames.length; i++) {
                    downImages[i] = new Image(downImageNames[i]);
                }
                for (int i = 0; i < leftImageNames.length; i++) {
                    leftImages[i] = new Image(leftImageNames[i]);
                }
                for (int i = 0; i < rightImageNames.length; i++) {
                    rightImages[i] = new Image(rightImageNames[i]);
                }

                for (int i = 0; i < deadImageNames.length; i++) {
                    deadImages[i] = new Image(deadImageNames[i]);
                }

                for (int i = 0; i < hurtImageNames.length; i++) {
                    hurtImages[i] = new Image(hurtImageNames[i]);
                }

                for (int i = 0; i < attackImageNames.length; i++) {
                    attackImages[i] = new Image(attackImageNames[i]);
                }

                //set animation durations for frames
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
                assetConfiguration.addImages(ImageTypes.IMAGE_UP, upImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_DOWN, downImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_LEFT, leftImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_RIGHT, rightImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_DEAD, deadImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_HURT, hurtImages);
                assetConfiguration.addImages(ImageTypes.IMAGE_ATTACK, hurtImages);
                assetConfiguration.setFrameDurations(durations);

                //add animations to config using images and frame durations

                //TODO : number of durations should be based on number of images, not hardcoded.

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
}
