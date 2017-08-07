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
    private int numEnemeies = 0;

    public AreaConfiguration(String areaConfigFileName) throws FileNotFoundException, IOException {

        areaConfigProperties = new Properties();
        InputStream configInput = new FileInputStream(areaConfigFileName);
        areaConfigProperties.load(configInput);
        configInput.close();

        if (!setUpEnemies()) {
            System.out.println("Failure in " + areaConfigFileName + ". Please check for errors.");
        }
    }

    public int getNumEnemeies() {
        return numEnemeies;
    }

    private boolean setUpEnemies() {

        try {
            numEnemeies = Integer.parseInt(
                    areaConfigProperties.get("NUMBER_OF_ENEMIES").toString()
            );

            return true;

        } catch (NumberFormatException numFormatEx) {
            System.out.println("ERROR: Failed to set up enemies in area. Defaulting to zero.");
            numFormatEx.printStackTrace();
        }

        return false;
    }

}
