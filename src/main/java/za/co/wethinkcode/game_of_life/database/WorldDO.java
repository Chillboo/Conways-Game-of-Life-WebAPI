package za.co.wethinkcode.game_of_life.database;

public class WorldDO {
    private int id;
    private String worldName;
    private int epoch;
    private int worldSize;
    private String cells;


    public int getWorldSize() {
        return worldSize;
    }

    public String getCells() {
        return cells;
    }

    public int getId() {
        return id;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getEpoch() {
        return epoch;
    }

}
