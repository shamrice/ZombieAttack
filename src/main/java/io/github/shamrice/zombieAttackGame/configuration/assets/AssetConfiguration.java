package io.github.shamrice.zombieAttackGame.configuration.assets;

import io.github.shamrice.zombieAttackGame.logger.Log;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 7/23/2017.
 */
public class AssetConfiguration {

    private String name;
    private Map<ImageTypes, Animation> animations;

    public AssetConfiguration(String name) {
        this.name = name;
        this.animations = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public void addAnimation(ImageTypes imageType, Animation animation) {
        animations.put(imageType, animation);
    }

    public Animation getAnimation(ImageTypes imageType) {
        return animations.get(imageType);
    }

}
