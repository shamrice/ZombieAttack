package io.github.shamrice.zombieAttackGame.actors.actorStats;


/**
 * Created by Erik on 8/22/2017.
 */
public class EnemyStatistics extends ActorStatistics {

    private int randomizerPercentage;

    public EnemyStatistics(int level, int health, int attackDamage, int randomizerPercentage) {
        super(level, health, attackDamage);

        this.randomizerPercentage = randomizerPercentage;
    }

    public int getRandomizerPercentage() {
        return this.randomizerPercentage;
    }
}
