package io.github.shamrice.zombieAttackGame.actors.statistics;

/**
 * Created by Erik on 8/22/2017.
 */
public class EnemyStatistics extends ActorStatistics {

    private int randomizerPercentage;
    private int experienceWorth;

    public EnemyStatistics(String name, int level, int health, int attackDamage, int defense,
                           int experienceWorth, int randomizerPercentage) {

        super(name, level, health, attackDamage, defense);

        this.randomizerPercentage = randomizerPercentage;
        this.experienceWorth = experienceWorth;
    }

    public int getRandomizerPercentage() {
        return this.randomizerPercentage;
    }

    @Override
    public int decreaseHealth(int amount) {
        int damageAmount = amount - this.currentDefense;

        if (damageAmount < 0)
            damageAmount = 0;

        if (amount > 0) {
            this.currentHealth -= damageAmount;
        }

        return damageAmount;
    }

    @Override
    public int getAttackDamage() {
        return currentAttackDamage;
    }

    public int getExperienceWorth() {
        return experienceWorth;
    }

    public int getDefense() { return currentDefense; }
}
