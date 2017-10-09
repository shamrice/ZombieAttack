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
    private int itemNumSelected = 0;
    private int previousItemNumSelected = 0;

    public InventoryDialogBox(InformationBoxConfig config) {
        this.config = config;

    }

    public void setItemNumSelected(int numSelected, Inventory inventory) {
        if (numSelected < inventory.getNumberOfItems()) {
            this.previousItemNumSelected = this.itemNumSelected;
            this.itemNumSelected = numSelected;
        } else {
            this.previousItemNumSelected = inventory.getNumberOfItems();
            this.itemNumSelected = 0;
        }
    }

    public int getPreviousItemNumSelected() {
        return this.previousItemNumSelected;
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

            if (itemNumSelected > 0 && i == itemNumSelected) {
                displayText = "> " + item.getNameString();
            } else {
                displayText = "  " + item.getNameString();
            }

            lineHeight += config.getTrueTypeFont().getLineHeight();

            config.getTrueTypeFont().drawString(
                    config.getxPos() + 15,
                    config.getyPos() + 30 + lineHeight,
                    displayText,
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
