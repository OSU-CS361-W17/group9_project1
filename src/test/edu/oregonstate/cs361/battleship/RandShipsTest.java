package edu.oregonstate.cs361.battleship;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by fbolanos on 2/3/2017.
 */
public class RandShipsTest {
    @org.junit.Test
    public void print() throws Exception {
        int count=0;
        RandShips randy= new RandShips();
        RandShips randy2= new RandShips();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(randy.board[x][y]==1)
                    count++;
            }
        }

        //if there are 16 1's in the board variable in the class then each ship
        //part was able to be in the board and non overlaping.
        // so this tests that all ships were succcesfuly placed with legal placements
        assertEquals(16,count);

        //tests that two independent classes created does in fact produce random
        // coordinates if they are unequal. however since the random numbers are
        //restrained to a specific range eventually this assert will fail when
        //both generate the same random numbers.
        assertNotEquals(randy.AircraftCordsStart,randy2.AircraftCordsStart);

    }



}
