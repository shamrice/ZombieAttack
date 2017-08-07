package io.github.shamrice.zombieAttackGame.areas;

import io.github.shamrice.zombieAttackGame.configuration.areas.AreaConfiguration;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
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
    private Rectangle collisionMap[][];
    private AreaConfiguration areaConfiguration;

    private boolean isLoaded = false;

    public Area(int xPos, int yPos, String tileMapFile, String areaInformationFileName) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileMapFile = tileMapFile;

        try {
            areaConfiguration = new AreaConfiguration(areaInformationFileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load() throws SlickException {
        if (!isLoaded) {
            tiledMap = new TiledMap(tileMapFile);

            collisionMap = new Rectangle[tiledMap.getWidth()][tiledMap.getHeight()];

            int objectLayer = tiledMap.getLayerIndex("objectLayer");

            if (objectLayer > 0) {
                for (int x = 0; x < tiledMap.getWidth(); x++) {
                    for (int y = 0; y < tiledMap.getHeight(); y++) {
                        int tileId =tiledMap.getTileId(x, y, objectLayer);
                        if (tileId > 0) {
                            //DEBUG
                            //System.out.println("Found " + tileId + " in collision layer");
                            collisionMap[x][y] = new Rectangle(x * 50, y * 50, 50, 50);
                        }
                    }
                }
            }

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

    public AreaConfiguration getAreaConfiguration() {
        return areaConfiguration;
    }

    public boolean checkCollision(Shape collisionRect) {

        if (collisionMap != null) {

            for (int j = 0; j < collisionMap.length; j++) {
                for (int i = 0; i < collisionMap[j].length; i++) {

                    if (collisionMap[j][i] != null) {

                        if (collisionMap[j][i].intersects(collisionRect)) {
                            return true;
                        }
                    }
                }

            }

        }
        return false;
    }

}
