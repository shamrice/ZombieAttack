package io.github.shamrice.zombieAttackGame.core.state.player;

import io.github.shamrice.zombieAttackGame.actors.statistics.PlayerStatistics;

/**
 * Created by Erik on 9/10/2017.
 */
public class PlayerState {

    private PlayerStatistics playerStatistics;
    private float x;
    private float y;

    public PlayerState(float x, float y, PlayerStatistics playerStatistics) {
        this.x = x;
        this.y = y;
        this.playerStatistics = playerStatistics;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }
}
