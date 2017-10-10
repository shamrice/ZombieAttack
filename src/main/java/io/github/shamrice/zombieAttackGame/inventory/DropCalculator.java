package io.github.shamrice.zombieAttackGame.inventory;

import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemBuilder;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemNames;

import java.util.Random;

/**
 * Created by Erik on 8/7/2017.
 */
public class DropCalculator {

    public static InventoryItem getItemDrop(int dropLevel) {

        InventoryItemNames[] names = InventoryItemNames.values();

        InventoryItemNames selectedItemName = InventoryItemNames.NONE;

        while (selectedItemName == InventoryItemNames.NONE) {
            int nameIndex = new Random().nextInt(names.length);
            selectedItemName = names[nameIndex];
        }

        return InventoryItemBuilder.buildItem(selectedItemName);

    }
}
