package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Created by Erik on 7/22/2017.
 */
public abstract class Actor {

    private boolean isRunning = false;
    private float walkSpeedMultiplier = 0.1f;
    private final float width = 50;
    private final float height = 50;
    private Shape collisionRect;
    private Directions lastDirection = Directions.NONE;
    private Directions currentDirection = Directions.NONE;

    protected String name;
    protected float xPos = 1;
    protected float yPos = 1;
    protected AssetConfiguration assetConfiguration;
    protected Animation currentAnimation;

    public Actor(AssetConfiguration assetConfiguration) {

        this.assetConfiguration = assetConfiguration;
        this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_RIGHT);
        this.collisionRect = new Rectangle(xPos, yPos, width, height);
    }

    public abstract int getAttackDamage();

    public void setWalkSpeedMultiplier(float walkSpeedMultiplier) {
        this.walkSpeedMultiplier = walkSpeedMultiplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Shape getCollisionRect() {
        return this.collisionRect;
    }

    public Shape getCollisionRectAtDirection(Directions attemptedDirection, int delta) {

        float tempY = this.collisionRect.getY();
        float tempX = this.collisionRect.getX();

        float currentWalkSpeed = getCurrentWalkSpeed();

        switch (attemptedDirection) {

            case UP:
                tempY -= delta * currentWalkSpeed;
                break;

            case DOWN:
                tempY += delta * currentWalkSpeed;
                break;

            case LEFT:
                tempX -= delta * currentWalkSpeed;
                break;

            case RIGHT:
                tempX += delta * currentWalkSpeed;
                break;
        }

        return new Rectangle(tempX, tempY,
                this.collisionRect.getWidth(), this.collisionRect.getHeight());
    }

    public void setxPos(float xNewPos) {

        if (xNewPos != this.xPos) {
            if (xNewPos > this.xPos) {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_RIGHT);
            } else {
                this.currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_LEFT);
            }

            this.xPos = xNewPos;
            this.collisionRect.setLocation(this.xPos, this.yPos);
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
            this.collisionRect.setLocation(this.xPos, this.yPos);
        }
    }

    public float getyPos() {
        return this.yPos;
    }

    public Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    public void move(Directions direction, int delta) {

        lastDirection = direction;

        boolean moved = false;

        float tempY = this.yPos;
        float tempX = this.xPos;

        float currentWalkSpeed = getCurrentWalkSpeed();

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

        if (moved) {
            currentDirection = direction;
            currentAnimation.update(delta);
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    protected ImageTypes getAttackImageTypeForCurrentDirection() {
        switch (currentDirection) {
            case UP:
                return ImageTypes.IMAGE_UP_ATTACK;
            case DOWN:
                return ImageTypes.IMAGE_DOWN_ATTACK;
            case LEFT:
                return ImageTypes.IMAGE_LEFT_ATTACK;
            case RIGHT:
                return ImageTypes.IMAGE_RIGHT_ATTACK;
            default:
                return ImageTypes.DEFAULT;
        }
    }

    private float getCurrentWalkSpeed() {
        return isRunning ? walkSpeedMultiplier * 3 : walkSpeedMultiplier;
    }
}
