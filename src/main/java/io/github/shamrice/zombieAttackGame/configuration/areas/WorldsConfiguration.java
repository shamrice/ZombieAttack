package io.github.shamrice.zombieAttackGame.configuration.areas;

/**
 * Created by Erik on 8/13/2017.
 */
public class WorldsConfiguration {

    private int maxWorlds;
    private int maxWorldX;
    private int maxWorldY;
    private String[][][] tileFileNames;
    private String areaConfigLocation;

    public WorldsConfiguration(int maxWorlds, int maxWorldX, int maxWorldY, String areaConfigLocation) {
        this.maxWorlds = maxWorlds;
        this.maxWorldX = maxWorldX;
        this.maxWorldY = maxWorldY;
        this.areaConfigLocation = areaConfigLocation;

        this.tileFileNames = new String[maxWorlds][maxWorldX][maxWorldY];
    }

    public void setTileFileNames(String[][][] tileFileNames) {
        this.tileFileNames = tileFileNames;
    }

    public String getTileFileName(int world, int x, int y) {
        if (world < maxWorlds && x < maxWorldX && y < maxWorldY) {
            return tileFileNames[world][x][y];
        }

        return "";
    }

    public int getMaxWorlds() {
        return maxWorlds;
    }

    public int getMaxWorldX() {
        return maxWorldX;
    }

    public int getMaxWorldY() {
        return maxWorldY;
    }

    public String getAreaConfigLocation() {
        return areaConfigLocation;
    }


}
