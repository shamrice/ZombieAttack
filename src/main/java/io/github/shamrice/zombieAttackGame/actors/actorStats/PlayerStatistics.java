package io.github.shamrice.zombieAttackGame.actors.actorStats;

import io.github.shamrice.zombieAttackGame.actors.projectiles.Projectile;
import io.github.shamrice.zombieAttackGame.logger.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by Erik on 8/22/2017.
 */
public class PlayerStatistics extends ActorStatistics {

    private int currentExperience = 0;
    private int experienceToNextLevel = 10;
    private Projectile currentProjectile;

    public PlayerStatistics(String name, int level, int baseHealth, int baseAttackDamage, int baseDefense) {
        super(name, level, baseHealth, baseAttackDamage, baseDefense);
    }

    public PlayerStatistics(String name, int level, int baseHealth, int baseAttackDamage, int baseDefense,
                            int currentExperience, int experienceToNextLevel) {
        super(name, level, baseHealth, baseAttackDamage, baseDefense);

        this.currentExperience = currentExperience;
        this.experienceToNextLevel = experienceToNextLevel;
    }

    @Override
    public int getAttackDamage() {
        return baseAttackDamage + currentProjectile.getAttackDamage();
    }

    public int getBaseAttackDamage() {
        return baseAttackDamage;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getCurrentDefense() {
        return currentDefense;
    }

    public int getCurrentExperience() {
        return currentExperience;
    }

    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void addExperience(int expAmount) {
        currentExperience += expAmount;

        if (currentExperience >= experienceToNextLevel) {
            levelUp();

            currentExperience = 0;

            //TODO : better formula for exp to next level
            experienceToNextLevel *= (1.5 * level);
        }
    }

    public void setProjectile(Projectile newProjectile) {
        this.currentProjectile = newProjectile;
    }

    public Projectile getCurrentProjectile() {
        return currentProjectile;
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

    private void levelUp() {

        Log.logDebug("PLAYER LEVEL UP. WAS LEVEL " + level);

        this.level++;

        Log.logDebug("NOW LEVEL " + level);

        //update stats...
        //TODO : possible better way to do this.

        Long dateSeed = new Date().getTime();

        this.baseHealth += new Random(dateSeed).nextInt(4) + 1;
        this.baseAttackDamage += new Random(dateSeed).nextInt(4) + 1;
        this.baseDefense += new Random(dateSeed).nextInt(3) + 1;

        this.currentHealth = this.baseHealth;
        this.currentAttackDamage = this.baseAttackDamage + currentProjectile.getAttackDamage();
        this.currentDefense = this.baseDefense;

        Log.logDebug("CURRENT HEALTH: " + currentHealth);
        Log.logDebug("CURRENT ATTACK DAMAGE: " + currentAttackDamage);
        Log.logDebug("CURRENT DEFENSE: " + currentDefense);
        Log.logDebug("EXP TO NEXT LEVEL: " + experienceToNextLevel);

    }

}
