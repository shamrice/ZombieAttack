package io.github.shamrice.zombieAttackGame.actors.actorStats;

/**
 * Created by Erik on 8/22/2017.
 */
public abstract class ActorStatistics {

    protected int level = 1;
    protected int baseHealth;
    protected int currentHealth;
    protected int baseaAttackDamage;
    protected int currentAttackDamage;


    public ActorStatistics(int level, int baseHealth, int baseAttackDamage) {
        this.level = level;
        this.baseHealth = baseHealth;
        this.baseaAttackDamage = baseAttackDamage;

        this.currentHealth = this.baseHealth;
        this.currentAttackDamage = this.baseaAttackDamage;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public void setCurrentHealth(int newCurrentHealth) {
        currentHealth = newCurrentHealth;
    }

    public void decreaseHealth(int amount) {
         currentHealth -= amount;
        if (currentHealth < 0)
            currentHealth = 0;
    }

    public int getAttackDamage() {
        return this.currentAttackDamage;
    }

    public int getLevel() {
        return this.level;
    }
}
