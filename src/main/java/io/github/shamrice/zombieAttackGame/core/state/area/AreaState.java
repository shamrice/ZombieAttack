package io.github.shamrice.zombieAttackGame.core.state.area;

/**
 * Created by Erik on 9/10/2017.
 */
public class AreaState {

    private int currentWorld;
    private int currentX;
    private int currentY;

    public AreaState(int currentWorld, int currentX, int currentY) {
        this.currentWorld = currentWorld;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public int getCurrentWorld() {
        return currentWorld;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }
}
