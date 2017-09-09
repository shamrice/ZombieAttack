package io.github.shamrice.zombieAttackGame.actors.actorStats;

/**
 * Created by Erik on 8/22/2017.
 */
public class ProjectileStatistics extends ActorStatistics {

    private int maxDistance;

    public ProjectileStatistics(String name, int attackDamage, int maxDistance) {
        super(name, 1, 1000, attackDamage, 0);

        this.maxDistance = maxDistance;
    }

    @Override
    public int decreaseHealth(int amount) {
        return 0;
    }

    @Override
    public int getAttackDamage() {
        return this.currentAttackDamage;
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }

}
