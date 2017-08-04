package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
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
    private AreaManager areaManager;

    public Configuration(AssetManager assetManager, AreaManager areaManager) throws SlickException {
        this.assetManager = assetManager;
        this.areaManager = areaManager;

        playerActor = new PlayerActor(
                assetManager.getAssetConfiguration(AssetTypes.PLAYER),
                assetManager.getAssetConfiguration(AssetTypes.BULLET_PROJECTILE)
        );
    }

    public AssetConfiguration getAssetConfiguration(AssetTypes type) {
        return assetManager.getAssetConfiguration(type);
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public PlayerActor getConfiguredPlayerActor() {
        return playerActor;
    }

}
