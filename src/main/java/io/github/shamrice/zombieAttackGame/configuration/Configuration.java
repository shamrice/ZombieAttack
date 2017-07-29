package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import org.newdawn.slick.SlickException;

/**
 * Created by Erik on 7/23/2017.
 */
public class Configuration {

    private PlayerActor playerActor;
    private AssetManager assetManager;

    public Configuration(AssetManager assetManager) throws SlickException {
        this.assetManager = assetManager;

        playerActor = new PlayerActor(
                assetManager.getAssetConfiguration(AssetTypes.PLAYER)
        );
    }

    public AssetConfiguration getAssetConfiguration(AssetTypes type) {
        return assetManager.getAssetConfiguration(type);
    }

    public PlayerActor getConfiguredPlayerActor() {
        return playerActor;
    }

}
