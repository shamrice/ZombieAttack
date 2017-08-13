package io.github.shamrice.zombieAttackGame.configuration.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import org.newdawn.slick.TrueTypeFont;

/**
 * Created by Erik on 8/13/2017.
 */
public class InventoryBoxConfig extends InformationBoxConfig {

    //Currently a placeholder for further implementation.

    public InventoryBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont) {
        super(assetConfiguration, trueTypeFont);

        xPos = 800;
        yPos = 0;
    }

    public InventoryBoxConfig(AssetConfiguration assetConfiguration, TrueTypeFont trueTypeFont,
                            int xPos, int yPos) {
        super(assetConfiguration, trueTypeFont, xPos, yPos);
    }
}
