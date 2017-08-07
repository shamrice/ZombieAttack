package io.github.shamrice.zombieAttackGame.inventory.items;

/**
 * Created by Erik on 8/7/2017.
 */
public class InventoryItem {

    private InventoryItemNames name;
    private InventoryItemTypes type;
    private int value;
    private String description;

    public InventoryItem(InventoryItemNames name, InventoryItemTypes type, int value, String description) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public String getNameString() {
        return name.name();
    }

    public InventoryItemNames getName() {
        return name;
    }

    public InventoryItemTypes getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
