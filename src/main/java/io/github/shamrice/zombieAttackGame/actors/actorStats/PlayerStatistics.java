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

    public PlayerStatistics(int level, int baseHealth, int baseAttackDamage, int baseDefense, Projectile projectile) {
        super(level, baseHealth, baseAttackDamage, baseDefense);

        this.currentProjectile = projectile;
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

    @Override
    public int getAttackDamage() {
        return baseAttackDamage + currentProjectile.getAttackDamage();
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
