package io.github.shamrice.zombieAttackGame.inventory;

import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItem;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemBuilder;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemNames;
import io.github.shamrice.zombieAttackGame.inventory.item.InventoryItemTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 8/7/2017.
 */
public class Inventory {

    private List<InventoryItem> inventoryItemList;
    private InventoryItem coins;
    private InventoryItem unarmedAttackItem;
    private int inventorySize;

    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        inventoryItemList = new ArrayList<InventoryItem>();

        coins = new InventoryItem(InventoryItemNames.COIN, InventoryItemTypes.ITEM, 0, "Money");
        unarmedAttackItem = InventoryItemBuilder.getEmptyItem(InventoryItemTypes.WEAPON);
    }

    public int getInventorySize() {
        return this.inventorySize;
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

    public InventoryItem getEquippedItem(InventoryItemTypes itemType) {
        for (InventoryItem item : inventoryItemList) {
            if (item.isEquipped() && itemType == item.getType()) {
                return item;
            }
        }

        if (itemType == InventoryItemTypes.WEAPON) {
            return unarmedAttackItem;
        }

        return null;
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

    public List<InventoryItem> getInventoryItemList() {
        return inventoryItemList;
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
