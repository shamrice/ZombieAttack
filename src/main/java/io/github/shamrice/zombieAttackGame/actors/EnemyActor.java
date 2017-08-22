package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.actors.actorStats.ActorStatistics;
import io.github.shamrice.zombieAttackGame.actors.actorStats.EnemyStatistics;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.ImageTypes;
import io.github.shamrice.zombieAttackGame.inventory.DropCalculator;
import io.github.shamrice.zombieAttackGame.inventory.items.InventoryItem;
import io.github.shamrice.zombieAttackGame.logger.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by Erik on 7/29/2017.
 */
public class EnemyActor extends Actor {

    private boolean isLooted = false;
    private InventoryItem itemDrop;

    public EnemyActor(AssetConfiguration assetConfiguration, ActorStatistics actorStatistics) {
        super(assetConfiguration, actorStatistics);

        this.itemDrop = DropCalculator.getItemDrop(1);
    }

    @Override
    public int getAttackDamage() {
        return actorStatistics.getAttackDamage();
    }

    public void decreaseHealth(int amount) {
        actorStatistics.decreaseHealth(amount);
        currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_HURT);

        //debug
        Log.logDebug("CURRENT ENEMY: " + actorStatistics.getCurrentHealth());

        if (actorStatistics.getCurrentHealth() <= 0) {
            currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_DEAD);
        }
    }

    public void attack() {
        currentAnimation = assetConfiguration.getAnimation(ImageTypes.IMAGE_ATTACK);
    }

    public boolean isAlive() {
        return this.actorStatistics.getCurrentHealth() > 0;
    }

    public boolean isLooted() {
        return isLooted;
    }

    public InventoryItem getItemDrop() {

        if (!isLooted) {
            Log.logDebug("Getting item from fallen enemy: " + itemDrop.getNameString());

            isLooted = true;
            return itemDrop;

        } else
            return null;
    }

    public Directions directionRandomizer(Directions attemptedDirection) {

        //TODO : potentially unsafe casting.
        int randomPercentageThreshold = ((EnemyStatistics)actorStatistics).getRandomizerPercentage();
        int randInt = new Random(new Date().getTime()).nextInt(100);

        if (randInt <= randomPercentageThreshold) {

            Directions newDirection = Directions.NONE;

            //OMG a do/while loop in the wild.
            do {
                int newDirectionIndex = new Random().nextInt(5);

                switch (newDirectionIndex) {
                    case 0:
                        newDirection = Directions.UP;
                        break;

                    case 1:
                        newDirection = Directions.DOWN;
                        break;

                    case 2:
                        newDirection = Directions.LEFT;
                        break;

                    case 3:
                        newDirection = Directions.RIGHT;
                        break;

                    default:
                        newDirection = Directions.NONE;
                        break;
                }
            } while (newDirection == attemptedDirection);

            return newDirection;
        }

        return attemptedDirection;
    }

}
