package io.github.shamrice.zombieAttackGame.actors;

import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;

/**
 * Created by Erik on 7/22/2017.
 */
public class PlayerActor extends Actor {

    private int health;

    public PlayerActor(AssetConfiguration assetConfiguration) {
        super(assetConfiguration);

        this.health = 100;
    }

}
