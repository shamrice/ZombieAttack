package io.github.shamrice.zombieAttackGame.inventory;

import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemBuilder;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemTypes;

import java.util.Random;

/**
 * Created by Erik on 8/7/2017.
 */
public class DropCalculator {

    public static InventoryItem getItemDrop(int dropLevel) {

        InventoryItemNames[] names = InventoryItemNames.values();

        int nameIndex = new Random().nextInt(names.length);

        InventoryItemNames selectedItemName = names[nameIndex];

        return InventoryItemBuilder.buildItem(selectedItemName);

    }
}
