package io.github.shamrice.zombieAttackGame.inventory.items;

import java.util.Random;

/**
 * Created by Erik on 8/7/2017.
 */
public class InventoryItemBuilder {

    private static final InventoryItemBuilder instance = new InventoryItemBuilder();

    public static InventoryItem buildItem(InventoryItemNames inventoryItemName) {

        int value = new Random().nextInt(10);
        InventoryItemTypes type = getInventoryItemType(inventoryItemName);

        //just some extra debug stuff.
        String description = inventoryItemName.name() + " is a(n) " + type.name() + ". " +
                "It has a value of " + value + ".";

        if (value == 0) {
            description += " Unfortunately it is rubbish.";
        }

        return new InventoryItem(
                inventoryItemName,
                type,
                value,
                description
        );

    }

    public static InventoryItem getEmptyItem(InventoryItemTypes itemType) {
        return new InventoryItem(
                InventoryItemNames.NONE,
                itemType,
                0,
                "NONE"
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
