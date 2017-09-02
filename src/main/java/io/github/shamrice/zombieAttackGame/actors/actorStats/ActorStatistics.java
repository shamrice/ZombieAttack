package io.github.shamrice.zombieAttackGame.actors.actorStats;

/**
 * Created by Erik on 8/22/2017.
 */
public abstract class ActorStatistics {

    protected int level = 1;
    protected int baseHealth;
    protected int currentHealth;
    protected int baseAttackDamage;
    protected int currentAttackDamage;
    protected int baseDefense;
    protected int currentDefense;


    public ActorStatistics(int level, int baseHealth, int baseAttackDamage, int baseDefense) {
        this.level = level;
        this.baseHealth = baseHealth;
        this.baseAttackDamage = baseAttackDamage;
        this.baseDefense = baseDefense;

        this.currentHealth = this.baseHealth;
        this.currentAttackDamage = this.baseAttackDamage;
        this.currentDefense = this.baseDefense;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public void setCurrentHealth(int newCurrentHealth) {
        currentHealth = newCurrentHealth;

        //can't heal over base health.
        if (currentHealth > baseHealth)
            currentHealth = baseHealth;
    }

    public abstract int decreaseHealth(int amount);

    public abstract int getAttackDamage();

    public int getLevel() {
        return this.level;
    }
}
