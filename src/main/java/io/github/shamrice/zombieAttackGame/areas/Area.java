package io.github.shamrice.zombieAttackGame.areas;

import io.github.shamrice.zombieAttackGame.configuration.areas.AreaConfiguration;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;

/**
 * Created by Erik on 7/29/2017.
 */
public class Area {

    private int xPos;
    private int yPos;
    private String tileMapFile;
    private TiledMap tiledMap = null;
    private String areaConfigFileName;
    private AreaConfiguration areaConfiguration;

    private boolean isLoaded = false;

    public Area(int xPos, int yPos, String tileMapFile, String areaInformationFileName) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileMapFile = tileMapFile;
        this.areaConfigFileName = areaInformationFileName;
    }

    public void load() throws SlickException {
        if (!isLoaded) {
            tiledMap = new TiledMap(tileMapFile);

            try {
                areaConfiguration = new AreaConfiguration(areaConfigFileName);

                isLoaded = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void unload() {
        if (isLoaded) {
            tiledMap = null;
            areaConfiguration = null;
            isLoaded = false;
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public AreaConfiguration getAreaConfiguration() {
        return areaConfiguration;
    }

}
