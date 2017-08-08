package io.github.shamrice.zombieAttackGame.configuration.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.messaging.MessageBox;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by Erik on 8/7/2017.
 */
public class MessageBoxConfig {

    private int xPos = 0;
    private int yPos = 600;

    private AssetConfiguration assetConfiguration;
    private TrueTypeFont trueTypeFont;

    public MessageBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont) {

        this.assetConfiguration = assetConfiguration;
        this.trueTypeFont = trueTypeFont;
    }

    public MessageBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont,
                            int xPos, int yPos) {

        this.assetConfiguration = assetConfiguration;
        this.trueTypeFont = trueTypeFont;
        this.xPos = xPos;
        this.yPos = yPos;

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
