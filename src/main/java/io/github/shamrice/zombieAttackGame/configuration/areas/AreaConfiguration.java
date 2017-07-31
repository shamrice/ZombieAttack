package io.github.shamrice.zombieAttackGame.configuration.areas;

import io.github.shamrice.zombieAttackGame.actors.EnemyActor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Erik on 7/30/2017.
 */
public class AreaConfiguration {

    private List<EnemyActor> enemyActors;
    private Properties areaConfigProperties;
    int numEnemeies = 0;

    public AreaConfiguration(String areaConfigFileName) throws FileNotFoundException, IOException {

        areaConfigProperties = new Properties();
        InputStream configInput = new FileInputStream(areaConfigFileName);
        areaConfigProperties.load(configInput);
        configInput.close();

        setUpEnemies();
    }

    public int getNumEnemeies() {
        return numEnemeies;
    }

    private void setUpEnemies() {

        numEnemeies = Integer.parseInt(
                areaConfigProperties.get("NUMBER_OF_ENEMIES").toString()
        );
    }

}
