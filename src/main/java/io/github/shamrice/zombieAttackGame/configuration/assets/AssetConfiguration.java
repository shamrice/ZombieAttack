package io.github.shamrice.zombieAttackGame.configuration.assets;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 7/23/2017.
 */
public class AssetConfiguration {

    private String name;
    private Map<ImageTypes, Image[]> animationImagesMap;
    private int[] frameDurations;
    private Map<ImageTypes, Animation> animations;

    public AssetConfiguration(String name) {
        this.name = name;
        this.animationImagesMap = new HashMap<ImageTypes, Image[]>();
        this.animations = new HashMap<ImageTypes, Animation>();
    }

    public String getName() {
        return this.name;
    }

    public void addImages(ImageTypes imageType, Image[] images) {
        animationImagesMap.put(imageType, images);
    }

    public Image[] getImages(ImageTypes imageType) {
        return animationImagesMap.get(imageType);
    }

    public void setFrameDurations(int[] frameDurations) {
        this.frameDurations = frameDurations;
    }

    public int[] getFrameDurations() {
        return frameDurations;
    }

    public void addAnimation(ImageTypes imageType, Animation animation) {
        animations.put(imageType, animation);
    }

    public Animation getAnimation(ImageTypes imageType) {
        return animations.get(imageType);
    }

    public Animation getAnimation(String imageType) {
        try {
            return animations.get(ImageTypes.valueOf(imageType));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
