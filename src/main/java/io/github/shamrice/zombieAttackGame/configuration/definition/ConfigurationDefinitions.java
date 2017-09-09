package io.github.shamrice.zombieAttackGame.configuration.definition;

/**
 * Created by Erik on 7/23/2017.
 */
public class ConfigurationDefinitions {

    //TODO : debug location... set to final location or pass as parameter???
    public static final String CONFIGURATION_FILE_LOCATION = "target/classes/conf/config.properties";

    public static final String LOG_FILENAME = "log.filename";
    public static final String LOG_LEVEL = "log.level";
    public static final String LOG_TYPE = "log.type";

    public static final String ASSET_CONFIGURATIONS = "asset.configurations";

    public static final String IMAGES_UP_SUFFIX = ".images.up";
    public static final String IMAGES_DOWN_SUFFIX = ".images.down";
    public static final String IMAGES_LEFT_SUFFIX = ".images.left";
    public static final String IMAGES_RIGHT_SUFFIX = ".images.right";
    public static final String IMAGES_DEAD_SUFFIX = ".images.dead";
    public static final String IMAGES_HURT_SUFFIX = ".images.hurt";
    public static final String IMAGES_ATTACK_SUFFIX = ".images.attack";
    public static final String ANIMATION_DURATION_PER_FRAME_SUFFIX = ".animation.duration.perFrame";

    public static final String AREA_CONFIG_FILES_LOCATION = "area.files.configuration.location";
    public static final String AREA_FILES_LOCATION = "area.files.data.location";
    public static final String AREA_NUM_WORLDS = "area.num.worlds";
    public static final String AREA_MAX_X= "area.max.x";
    public static final String AREA_MAX_Y = "area.max.y";

    public static final String MESSAGE_BOX_FONT_NAME = "MESSAGE_BOX.font.name";
    public static final String INVENTORY_FONT_NAME = "INVENTORY.font.name";
    public static final String STATS_BOX_FONT_NAME = "STATS_BOX.font.name";

    public static final String STATISTICS_CONFIG_FILES_LOCATION = "stats.files.configuration.location";
    public static final String STATISTICS_CONFIGURED_ENEMIES = "stats.enemies.items";
    public static final String STATISTICS_CONFIGURED_PROJECTILES = "stats.projectiles.items";


}
