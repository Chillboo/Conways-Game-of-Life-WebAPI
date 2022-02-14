package za.co.wethinkcode.game_of_life;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import kong.unirest.HttpStatus;
import za.co.wethinkcode.game_of_life.database.GameOfLifeDB;
import za.co.wethinkcode.game_of_life.database.WorldDO;
import za.co.wethinkcode.game_of_life.domain.World;
import za.co.wethinkcode.game_of_life.http.requests.CreateRequest;
import za.co.wethinkcode.game_of_life.http.responses.WorldResponse;

import java.sql.SQLException;

import static za.co.wethinkcode.game_of_life.domain.AdvanceConwaysGame.advanceConwaysGame;

public class WorldApiHandler {
    private GameOfLifeDB db;

    public WorldApiHandler(String dbUrl) {
        try {
            db = new GameOfLifeDB(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNew(Context context) throws JsonProcessingException {
        CreateRequest createRequest = context.bodyAsClass(CreateRequest.class);
        World world = World.define(createRequest.getWorldName(), createRequest.getWorldSize(), createRequest.getWorldInitialState());

        //sql stuff
        WorldDO newWorld = db.addWorld(world);

        context.status(HttpStatus.CREATED);

        context.json(new WorldResponse(newWorld.getId(), newWorld.getEpoch(), world.getState()));
    }

    public void getAllWorlds(Context context) {
        context.json(db.all());
    }

    public void advanceGame(Context context) throws JsonProcessingException {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        //Get the requested world from DB
        WorldDO world = db.getWorld(id);

        //Convert String from DB to int[][]
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        int[][] cells = mapper.readValue(world.getCells() , int[][].class);

        //Get next gen and convert to string for return & DB
        int[][] newCells = advanceConwaysGame(cells);

        //convert next generation cells to a string to send back
        String nextGenerationString = mapper.writeValueAsString(newCells);
        int nextEpoch = world.getEpoch() + 1;

        // update the values in the DB with the new gen values
        WorldDO newWorld = db.updateNewWorld(world.getId(), nextEpoch, nextGenerationString);

        context.json(newWorld);
    }
}
