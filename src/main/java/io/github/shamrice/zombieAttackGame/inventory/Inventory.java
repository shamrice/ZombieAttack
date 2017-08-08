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
    private InventoryItem coins;
    private int inventorySize;

    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        inventoryItemList = new ArrayList<InventoryItem>();

        coins = new InventoryItem(InventoryItemNames.COIN, InventoryItemTypes.ITEM, 0, "Money");
    }

    public int getNumberOfItems() {
        return inventoryItemList.size();
    }

    public boolean isFull() {
        return inventoryItemList.size() == inventorySize;
    }

    public int getNumberOfCoins() {
        return coins.getValue();
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

        //if pick up is coins, add to coin bank, if not, add to inventory.
        if (inventoryItem.getName() == InventoryItemNames.COIN) {
            coins.setValue(coins.getValue() + inventoryItem.getValue());
            return true;

        } else {

            if (inventoryItemList.size() < inventorySize) {
                inventoryItemList.add(inventoryItem);
                return true;
            }
        }

        return false;
    }

}
