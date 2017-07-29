package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 7/29/2017.
 */
public class EnemyActor extends Actor {

    private int health;

    public EnemyActor(AssetConfiguration assetConfiguration) {
        super(assetConfiguration);

        this.health = 100;
    }
}
