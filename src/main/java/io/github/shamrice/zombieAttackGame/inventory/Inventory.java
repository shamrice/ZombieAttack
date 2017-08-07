package io.github.shamrice.zombieAttackGame.inventory;

import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItemTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 8/7/2017.
 */
public class Inventory {

    private List<InventoryItem> inventoryItemList;
    private int inventorySize;

    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        inventoryItemList = new ArrayList<InventoryItem>();
    }

    public int getNumberOfItems() {
        return inventoryItemList.size();
    }

    public boolean isFull() {
        return inventoryItemList.size() == inventorySize;
    }

    public InventoryItem getInventoryItem(int inventorySlotNumber) {
        return inventoryItemList.get(inventorySlotNumber);
    }

    public InventoryItem getInventoryItem(InventoryItemNames inventoryItemName) {

        for (InventoryItem item : inventoryItemList) {
            if (item.getName() == inventoryItemName) {
                return item;
            }
        }

        return null;
    }

    public boolean addInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItemList.size() < inventorySize) {
            inventoryItemList.add(inventoryItem);
            return true;
        }

        return false;
    }

    // TODO : Possibly remove this as the builder should be used instead.
    public void addInventoryItem(InventoryItemNames inventoryItemName, InventoryItemTypes inventoryItemType, int value) {

        if (inventoryItemList.size() < inventorySize) {
            inventoryItemList.add(
                    new InventoryItem(
                            inventoryItemName,
                            inventoryItemType,
                            value
                    )
            );
        }
    }
}
