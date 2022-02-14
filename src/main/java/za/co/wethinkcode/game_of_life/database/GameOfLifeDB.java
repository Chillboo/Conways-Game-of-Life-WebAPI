package za.co.wethinkcode.game_of_life.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.lemnik.eodsql.QueryTool;
import za.co.wethinkcode.game_of_life.domain.World;

import java.sql.*;
import java.util.List;

// "jdbc:sqlite:game_of_life.db"

public class GameOfLifeDB {
    public final Connection connection;
    public final WorldDAI worldQuery;

    public GameOfLifeDB(String dbUrl) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
        this.worldQuery = QueryTool.getQuery(this.connection, WorldDAI.class);
    }


    //Returns a list of World data objects, all that exist in the Database
    public List<ListWorldDO> all() {
        return worldQuery.getAllWorlds();
    }


    // Defines a new world based on the body sent as a request, adds this to the DB.
    public WorldDO addWorld(World world) throws JsonProcessingException {
        //Set int[][] to String for DB
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        String cells = mapper.writeValueAsString(world.getState());

        //Add world to DB
        worldQuery.addWorld(world.getWorldName(), world.getWorldSize(), cells);
        return worldQuery.getWorldByName(world.getWorldName());
    }

    // Gets a world based on the id given, returns the world Data Object for that world
    public WorldDO getWorld(int id) {
        return worldQuery.getWorldById(id);
    }

    //Update the new generations value
    public WorldDO updateNewWorld(int id, int epoch, String nextGeneration) {
        worldQuery.updateWorldWithNewValues(id, epoch, nextGeneration);
        return worldQuery.getWorldById(id);
    }
}
