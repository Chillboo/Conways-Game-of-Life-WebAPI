package za.co.wethinkcode.game_of_life.database;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface WorldDAI extends BaseQuery{

    //Select all the worlds and return the DO for them
    @Select(
            "SELECT * FROM world"
    )
    List<ListWorldDO> getAllWorlds();


    //Select a specific world based on the name of the world
    @Select(
            "SELECT * FROM world WHERE worldName = ?{1}"
    )
    WorldDO getWorldByName(String worldName);


    // Select a world based on the unique ID of that world
    @Select(
            "SELECT * FROM world WHERE id = ?{1}"
    )
    WorldDO getWorldById(int id);


    // Insert a new world into the DB
    @Update(
            "INSERT INTO world(worldName, worldSize, cells, epoch) VALUES (?{1}, ?{2}, ?{3}, 0)"
    )
    void addWorld(String worldName, int worldSize, String cells);


    //Update the values of a world with the next generation
    @Update(
            "UPDATE world SET epoch = ?2, cells = ?3 WHERE id = ?1"
    )
    void updateWorldWithNewValues(int id, int epoch, String cells);
}
