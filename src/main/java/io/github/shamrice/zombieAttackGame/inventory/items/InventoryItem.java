package io.github.shamrice.zombieAttackGame.inventory.items;

import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.configuration.statistics.ProjectileTypes;

/**
 * Created by Erik on 8/7/2017.
 */
public class InventoryItem {

    private InventoryItemNames name;
    private InventoryItemTypes type;
    private int value;
    private String description;
    private boolean isEquipped;
    private Projectile projectile;

    public InventoryItem(InventoryItemNames name, InventoryItemTypes type, int value, String description) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.description = description;
        this.isEquipped = false;
        this.projectile = null;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public Projectile getProjectile() {
        return this.projectile;
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
