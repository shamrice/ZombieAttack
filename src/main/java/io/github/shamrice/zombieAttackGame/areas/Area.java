package io.github.shamrice.zombieAttackGame.areas;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Erik on 7/29/2017.
 */
public class Area {

    private int xPos;
    private int yPos;
    private String tileMapFile;
    private TiledMap tiledMap = null;
    private Object areaConfiguration; //TODO: to house object and enemy information for the area

    private boolean isLoaded = false;

    public Area(int xPos, int yPos, String tileMapFile) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileMapFile = tileMapFile;
    }

    public void load() throws SlickException {
        if (!isLoaded) {
            tiledMap = new TiledMap(tileMapFile);
            isLoaded = true;
        }
    }

    public void unload() {
        if (isLoaded) {
            tiledMap = null;
            isLoaded = false;
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }



}
