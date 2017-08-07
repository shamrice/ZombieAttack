package io.github.shamrice.zombieAttackGame.inventory.items;

import java.util.Random;

/**
 * Created by Erik on 8/7/2017.
 */
public class InventoryItemBuilder {

    private static final InventoryItemBuilder instance = new InventoryItemBuilder();

    public static InventoryItem buildItem(InventoryItemNames inventoryItemName) {

        int value = new Random().nextInt(10);

        return new InventoryItem(
                inventoryItemName,
                getInventoryItemType(inventoryItemName),
                value
        );

    }


    private static InventoryItemTypes getInventoryItemType(InventoryItemNames inventoryItemName) {
        switch (inventoryItemName) {
            case COIN:
                return InventoryItemTypes.ITEM;

            case BLASTER:
                return InventoryItemTypes.WEAPON;

            default:
                return InventoryItemTypes.OTHER;
        }
    }

}
