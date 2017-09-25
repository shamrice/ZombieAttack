package io.github.shamrice.zombieAttackGame.inventory;

import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InformationBoxConfig;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 8/8/2017.
 */
public class InventoryDialogBox {

    private InformationBoxConfig config;
    private List<String> currentText;

    public InventoryDialogBox(InformationBoxConfig config) {
        this.config = config;

        this.currentText = new ArrayList<String>();
    }

    public void draw(Inventory inventory) {

        config
                .getAssetConfiguration()
                .getAnimation(ImageTypes.DEFAULT)
                .draw(
                        config.getxPos(),
                        config.getyPos()
                );

        int lineHeight = 0;

        for (int i = 0; i < inventory.getNumberOfItems(); i++) {

            InventoryItem item = inventory.getInventoryItem(i);

            lineHeight += config.getTrueTypeFont().getLineHeight();

            config.getTrueTypeFont().drawString(
                    config.getxPos() + 15,
                    config.getyPos() + 30 + lineHeight,
                    item.getNameString(),
                    Color.white
            );
        }

        String coinString = "COINS: " + inventory.getNumberOfCoins();

        config.getTrueTypeFont().drawString(
                config.getxPos() + 15,
                config.getyPos() + 370,
                coinString,
                Color.white
        );
    }
}
