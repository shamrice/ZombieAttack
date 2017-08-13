package io.github.shamrice.zombieAttackGame.configuration.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.messaging.MessageBox;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by Erik on 8/7/2017.
 */
public abstract class InformationBoxConfig {

    protected int xPos;
    protected int yPos;

    protected AssetConfiguration assetConfiguration;
    protected TrueTypeFont trueTypeFont;

    public InformationBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont) {

        this.assetConfiguration = assetConfiguration;
        this.trueTypeFont = trueTypeFont;
    }

    public InformationBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont,
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
