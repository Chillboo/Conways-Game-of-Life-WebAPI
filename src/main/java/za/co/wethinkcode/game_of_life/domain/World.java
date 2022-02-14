package za.co.wethinkcode.game_of_life.domain;

public class World {
    private int[][] cells;

    public String getWorldName() {
        return worldName;
    }

    private final String worldName;

    public Integer getWorldSize() {
        return worldSize;
    }

    private Integer worldSize;

    private World(String worldName, Integer worldSize) {
        this.worldName = worldName;
        this.worldSize = worldSize;
    }

    public static World define(String worldName, Integer worldSize, int[][] worldInitialState) {
        World world = new World(worldName, worldSize);
        world.setCells(worldInitialState);

        return world;
    }

    private void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getState() {
        return this.cells;
    }
}
