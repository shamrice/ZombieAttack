package io.github.shamrice.zombieAttackGame.configuration;

import io.github.shamrice.zombieAttackGame.actors.PlayerActor;
import io.github.shamrice.zombieAttackGame.areas.AreaManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetConfiguration;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetManager;
import io.github.shamrice.zombieAttackGame.configuration.assets.AssetTypes;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InformationBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.messaging.InventoryBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.messaging.MessageBoxConfig;
import io.github.shamrice.zombieAttackGame.configuration.statistics.StatisticsConfiguration;
import org.newdawn.slick.SlickException;

/**
 * Created by Erik on 7/23/2017.
 */
public class Configuration {

    private PlayerActor playerActor;
    private AssetManager assetManager;
    private AreaManager areaManager;
    private MessageBoxConfig messageBoxConfig;
    private InventoryBoxConfig inventoryBoxConfig;
    private StatisticsConfiguration statisticsConfiguration;

    public Configuration(AssetManager assetManager, AreaManager areaManager,
                         PlayerActor playerActor, MessageBoxConfig messageBoxConfig,
                         InventoryBoxConfig inventoryBoxConfig, StatisticsConfiguration statisticsConfiguration)
            throws SlickException {

        this.assetManager = assetManager;
        this.areaManager = areaManager;
        this.playerActor = playerActor;
        this.messageBoxConfig = messageBoxConfig;
        this.inventoryBoxConfig = inventoryBoxConfig;
        this.statisticsConfiguration = statisticsConfiguration;
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

    public InformationBoxConfig getMessageBoxConfig() {
        return messageBoxConfig;
    }

    public InformationBoxConfig getInventoryMessageBoxConfig() {
        return inventoryBoxConfig;
    }

    public StatisticsConfiguration getStatisticsConfiguration() {
        return statisticsConfiguration;
    }
}
