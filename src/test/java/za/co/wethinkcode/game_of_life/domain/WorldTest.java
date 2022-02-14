package za.co.wethinkcode.game_of_life.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import za.co.wethinkcode.game_of_life.domain.AdvanceConwaysGame.*;

public class WorldTest {

    @Test
    public void testDeathByUnderPopulation(){
        int[][] testGeneration = {
                {0,0,1},
                {0,1,0},
                {0,0,0},
        };
        assertEquals(1, testGeneration[1][1]);

        testGeneration = AdvanceConwaysGame.advanceConwaysGame(testGeneration);

        assertEquals(0, testGeneration[1][1]);
    }

    @Test
    public void testStillAliveInNextStep(){
        int[][] testGeneration = {
                {0,1,1},
                {0,1,0},
                {0,0,0},
        };
        assertEquals(1, testGeneration[1][1]);

        testGeneration = AdvanceConwaysGame.advanceConwaysGame(testGeneration);

        assertEquals(1, testGeneration[1][1]);
    }

    @Test
    public void testDeathByOverPopulation(){
        int[][] testGeneration = {
                {0,1,1},
                {0,1,1},
                {0,1,0},
        };
        assertEquals(1, testGeneration[1][1]);

        testGeneration = AdvanceConwaysGame.advanceConwaysGame(testGeneration);

        assertEquals(0, testGeneration[1][1]);
    }

    @Test
    public void testBirthByReproduction(){
        int[][] testGeneration = {
                {0,1,1},
                {0,1,0},
                {0,0,0},
        };
        assertEquals(0, testGeneration[1][2]);

        testGeneration = AdvanceConwaysGame.advanceConwaysGame(testGeneration);

        assertEquals(1, testGeneration[1][1]);
    }
}
