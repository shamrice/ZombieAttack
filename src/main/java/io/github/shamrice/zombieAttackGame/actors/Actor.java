package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import org.newdawn.slick.Animation;

/**
 * Created by Erik on 7/22/2017.
 */
public abstract class Actor {

    private boolean isRunning = false;
    private final float walkSpeedMultiplier = 0.1f;

    protected String name;
    protected float xPos = 0;
    protected float yPos = 0;
    protected AssetConfiguration assetConfiguration;
    protected Animation currentAnimation;

    public Actor(AssetConfiguration assetConfiguration) {

        this.assetConfiguration = assetConfiguration;
        this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_UP);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setxPos(float xNewPos) {

        if (xNewPos != this.xPos) {
            if (xNewPos > this.xPos) {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_RIGHT);
            } else {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_LEFT);
            }

            this.xPos = xNewPos;
        }
    }

    public float getxPos() {
        return this.xPos;
    }

    public void setyPos(float yNewPos) {

        if (yNewPos != this.yPos) {

            if (yNewPos > this.yPos) {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DOWN);
            } else {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_UP);
            }

            this.yPos = yNewPos;
        }
    }

    public float getyPos() {
        return this.yPos;
    }

    public Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    public void move(Directions direction, int delta) {

        boolean moved = false;

        float tempY = this.yPos;
        float tempX = this.xPos;

        float currentWalkSpeed = isRunning ? walkSpeedMultiplier * 2 :walkSpeedMultiplier;

        switch (direction) {

            case UP:
                tempY -= delta * currentWalkSpeed;
                setyPos(tempY);
                moved = true;
                break;

            case DOWN:
                tempY += delta * currentWalkSpeed;
                setyPos(tempY);
                moved = true;
                break;

            case LEFT:
                tempX -= delta * currentWalkSpeed;
                setxPos(tempX);
                moved = true;
                break;

            case RIGHT:
                tempX += delta * currentWalkSpeed;
                setxPos(tempX);
                moved = true;
                break;
        }

        if (moved)
            currentAnimation.update(delta);
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

}
