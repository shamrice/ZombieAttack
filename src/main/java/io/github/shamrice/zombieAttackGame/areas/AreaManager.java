package io.github.shamrice.zombieAttackGame.areas;

import io.github.shamrice.zombieAttackGame.configuration.areas.WorldsConfiguration;
import io.github.shamrice.zombieAttackGame.logger.Log;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Created by Erik on 7/29/2017.
 */
public class AreaManager {

    private Area[][][] areaMatrix;
    private WorldsConfiguration worldsConfiguration;
    private int currentWorld;
    private int currentX;
    private int currentY;

    public AreaManager(WorldsConfiguration worldsConfiguration) {

        this.worldsConfiguration = worldsConfiguration;

        this.areaMatrix = new Area
                [worldsConfiguration.getMaxWorlds()]
                [worldsConfiguration.getMaxWorldX()]
                [worldsConfiguration.getMaxWorldY()];

        for (int w = 0; w < worldsConfiguration.getMaxWorlds(); w++) {
            for (int x = 0; x < worldsConfiguration.getMaxWorldX(); x++) {
                for (int y = 0; y < worldsConfiguration.getMaxWorldY(); y++) {
                    areaMatrix[w][x][y] = new Area(x,
                            y,
                            worldsConfiguration.getTileFileName(w, x, y),
                            worldsConfiguration.getAreaConfigLocation() + "area_" + w + "_" + x + "_" + y + ".properties"
                    );
                }
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

    public Area getArea(int world, int xPos, int yPos) {
        if ((xPos < worldsConfiguration.getMaxWorldX() && xPos >= 0) && (yPos < worldsConfiguration.getMaxWorldY() && yPos >= 0)) {
            return areaMatrix[world][xPos][yPos];
        }

        return null;
    }

    public Area getCurrentArea() {
        return areaMatrix[currentWorld][currentX][currentY];
    }

    public void setCurrentAreaLocation(int newX, int newY) {
        if ((newX < worldsConfiguration.getMaxWorldX() && newX >= 0) && (newY < worldsConfiguration.getMaxWorldY() && newY >= 0)) {

            //unload previous area map
            areaMatrix[currentWorld][currentX][currentY].unload();

            currentX = newX;
            currentY = newY;

            //load new area map
            try {
                areaMatrix[currentWorld][currentX][currentY].load();
            } catch (SlickException slickExc) {
                Log.logException(
                        "ERROR: unable to load area [" + currentX + "][" + currentY + "].",
                        slickExc
                );
            }
        }
    }

    /**
     * Sets current world to newWorld and sets CurrentX and CurrentY to 0,0 in
     * new world.
     * @param newWorld
     */
    public void setCurrentWorld(int newWorld) {
        this.currentWorld = newWorld;
        setCurrentAreaLocation(0, 0);
    }

    public int getCurrentWorld() {
        return currentWorld;
    }

    public TiledMap getCurrentAreaTileMap() {
        return areaMatrix[currentWorld][currentX][currentY].getTiledMap();
    }
}
