package io.github.shamrice.zombieAttackGame.areas;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Erik on 7/29/2017.
 */
public class AreaManager {

    private Area[][] areaMatrix;
    private int mapSizeX;
    private int mapSizeY;
    private int currentX;
    private int currentY;
    private String areaConfigLocation;

    public AreaManager(String[][] tileMapFileNames, String areaConfigLocation) {

        this.areaConfigLocation = areaConfigLocation;

        this.mapSizeX = tileMapFileNames.length;
        this.mapSizeY = tileMapFileNames[0].length;
        this.areaMatrix = new Area[mapSizeX][mapSizeY];

        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                areaMatrix[x][y] = new Area(x,
                        y,
                        tileMapFileNames[x][y],
                        areaConfigLocation + "area_" + x + "_" + y + ".properties"
                );
            }
        }

        this.currentX = 0;
        this.currentY = 0;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public Area getArea(int xPos, int yPos) {
        if ((xPos < mapSizeX && xPos >= 0) && (yPos < mapSizeY && yPos >= 0)) {
            return areaMatrix[xPos][yPos];
        }

        return null;
    }

    public Area getCurrentArea() {
        return areaMatrix[currentX][currentY];
    }

    public void setCurrentAreaLocation(int newX, int newY) {
        if ((newX < mapSizeX && newX >= 0) && (newY < mapSizeY && newY >= 0)) {

            //unload previous area map
            areaMatrix[currentX][currentY].unload();

            currentX = newX;
            currentY = newY;

            //load new area map
            try {
                areaMatrix[currentX][currentY].load();
            } catch (SlickException slickExc) {
                System.out.println("ERROR: unable to load area [" + currentX + "][" + currentY + "].");
                slickExc.printStackTrace();
            }
        }
    }

    public TiledMap getCurrentAreaTileMap() {
        return areaMatrix[currentX][currentY].getTiledMap();
    }
}
