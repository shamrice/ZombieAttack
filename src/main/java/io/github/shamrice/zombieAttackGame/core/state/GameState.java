package io.github.shamrice.zombieAttackGame.core.state;

import io.github.shamrice.zombieAttackGame.core.state.area.AreaState;
import io.github.shamrice.zombieAttackGame.core.state.player.PlayerState;
import io.github.shamrice.zombieAttackGame.inventory.Inventory;

/**
 * Created by Erik on 9/9/2017.
 */
public class GameState {

    private AreaState areaState;
    private PlayerState playerState;
    private Inventory inventory;

    public GameState(AreaState areaState, PlayerState playerState, Inventory inventory) {
        this.areaState = areaState;
        this.playerState = playerState;
        this.inventory = inventory;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public AreaState getAreaState() {
        return areaState;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
