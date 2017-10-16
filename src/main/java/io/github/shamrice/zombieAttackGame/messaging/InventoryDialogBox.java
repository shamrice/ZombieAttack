package io.github.shamrice.zombieAttackGame.messaging;

import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InformationBoxConfig;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.logger.Log;
import org.newdawn.slick.Color;

/**
 * Created by Erik on 8/8/2017.
 */
public class InventoryDialogBox {

    private InformationBoxConfig config;
    private int itemNumSelected = -1;

    public InventoryDialogBox(InformationBoxConfig config) {
        this.config = config;

    }

    public void selectNextItem(int totalNumItems) {
        if (itemNumSelected < totalNumItems) {
            itemNumSelected++;
        }

        if (itemNumSelected >= totalNumItems) {
            itemNumSelected = totalNumItems - 1;
        }
    }

    public void selectPreviousItem(int totalNumItems) {
        if (itemNumSelected > 0) {
            itemNumSelected--;
        }

        if (itemNumSelected < 0) {
            itemNumSelected = 0;
        }
    }

    public int getItemNumSelected() {
        return this.itemNumSelected;
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

        String displayText = "";

        for (int i = 0; i < inventory.getNumberOfItems(); i++) {

            InventoryItem item = inventory.getInventoryItem(i);

            if (itemNumSelected >= 0 && i == itemNumSelected) {
                displayText = "> " + item.getNameString();
            } else {
                displayText = "  " + item.getNameString();
            }

            lineHeight += config.getTrueTypeFont().getLineHeight();

            config.getTrueTypeFont().drawString(
                    config.getxPos() + 15,
                    config.getyPos() + 30 + lineHeight,
                    displayText,
                    item.isEquipped() ? Color.green : Color.white
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
