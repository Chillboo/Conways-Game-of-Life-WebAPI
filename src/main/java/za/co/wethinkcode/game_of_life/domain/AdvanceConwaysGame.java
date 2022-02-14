package za.co.wethinkcode.game_of_life.domain;

import java.util.Arrays;

public class AdvanceConwaysGame {
    public static int[][] advanceConwaysGame(int[][] worldGrid) {

        int[][] board = new int[worldGrid.length][worldGrid[0].length];
        for(int i=0; i<worldGrid.length; i++) {
            board[i] = Arrays.copyOf(worldGrid[i], worldGrid[i].length);
        }

        int neighLiveCount;
        for(int i=0; i<board.length; i++) {                     // each row
            for(int j=0; j<board[0].length; j++) {              // each column
                neighLiveCount = 0;                             // reset Live neigh = 0
                for(int p=-1; p<=1; p++) {                      // row-offet (-1,0,1)
                    for(int q=-1; q<=1; q++) {                  // col-offset (-1,0,1)
                        if((i+p <0) ||                          // if row offset less than UPPER boundary
                                (i+p > board.length-1) ||       // if row offset more than LOWER boundary
                                (j+q <0) ||                     // if column offset less than LEFT boundary
                                (j+q > board[i].length-1))      // if column offset more than RIGHT boundary
                            continue;                           // skip the neighbour (if not, then ArrayIndexOutOfBounds Excp)
                        neighLiveCount+= board[i+p][j+q];       // incremeent live counter for each live neighbour
                    }
                }
                neighLiveCount-= board[i][j];                   // remove self count

                switch(neighLiveCount) {
                    case 0:
                    case 1:
                        // System.out.println("Less than 2 neigh alive, setting this to dead!");
                        worldGrid[i][j] = 0;
                        break;

                    case 2:
                        // Exactly 2 neigh alive, state stays same!
                        worldGrid[i][j] = board[i][j];
                        break;

                    case 3:
                        //Exactly 3 neigh alive, setting this to Live!
                        worldGrid[i][j] = 1;
                        break;

                    default:
                        // System.out.println("More than 3 neigh alive, setting this to dead!");
                        worldGrid[i][j] = 0;
                }
            }
        }
        return worldGrid;
    }
}
