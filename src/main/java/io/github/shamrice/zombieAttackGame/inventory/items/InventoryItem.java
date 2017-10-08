package io.github.shamrice.zombieAttackGame.inventory.items;

/**
 * Created by Erik on 8/7/2017.
 */
public class InventoryItem {

    private InventoryItemNames name;
    private InventoryItemTypes type;
    private int value;
    private String description;
    private boolean isEquipped;

    public InventoryItem(InventoryItemNames name, InventoryItemTypes type, int value, String description) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.description = description;
        this.isEquipped = false;
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

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEquipped() { return this.isEquipped; }

    public void setEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }
}
