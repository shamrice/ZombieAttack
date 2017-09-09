package io.github.shamrice.zombieAttackGame.configuration.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by Erik on 9/9/2017.
 */
public class StatisticsBoxConfig extends InformationBoxConfig {

    //Currently a placeholder for further implementation.

    public StatisticsBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont) {
        super(assetConfiguration, trueTypeFont);

        xPos = 800;
        yPos = 400;
    }

    public StatisticsBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont,
                            int xPos, int yPos) {
        super(assetConfiguration, trueTypeFont, xPos, yPos);
    }
}
