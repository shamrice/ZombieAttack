package io.github.shamrice.zombieAttackGame.configuration.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by Erik on 8/7/2017.
 */
public class MessageBoxConfig {

    private final int xPos = 0;
    private final int yPos = 600;

    private AssetConfiguration assetConfiguration;
    private TrueTypeFont trueTypeFont;

    public MessageBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont) {

        this.assetConfiguration = assetConfiguration;
        this.trueTypeFont = trueTypeFont;
    }

    public AssetConfiguration getAssetConfiguration() {
        return assetConfiguration;
    }

    public TrueTypeFont getTrueTypeFont() {
        return trueTypeFont;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
