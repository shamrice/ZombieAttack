package io.github.shamrice.zombieAttackGame.inventory.item;

import io.github.shamrice.zombieAttackGame.actors.projectiles.ProjectileBuilder;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;

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

        InventoryItem item =  new InventoryItem(
                inventoryItemName,
                type,
                value,
                description
        );

        if (type == InventoryItemTypes.WEAPON) {
            item.setProjectile(ProjectileBuilder.build(ProjectileTypes.BULLET));
        }

        return item;

    }

    public static InventoryItem getEmptyItem(InventoryItemTypes itemType) {
        InventoryItem item = new InventoryItem(
                InventoryItemNames.NONE,
                itemType,
                0,
                "NONE"
        );

        item.setProjectile(ProjectileBuilder.build(ProjectileTypes.UNARMED));

        return item;
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
