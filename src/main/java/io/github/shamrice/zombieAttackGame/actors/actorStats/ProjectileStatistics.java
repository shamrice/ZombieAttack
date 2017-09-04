package io.github.shamrice.zombieAttackGame.actors.actorStats;

/**
 * Created by Erik on 8/22/2017.
 */
public class ProjectileStatistics extends ActorStatistics {

    public ProjectileStatistics(String name, int attackDamage) {
        super(name, 1, 1000, attackDamage, 0);
    }

    @Override
    public int decreaseHealth(int amount) {
        return 0;
    }

    @Override
    public int getAttackDamage() {
        return this.currentAttackDamage;
    }

}
