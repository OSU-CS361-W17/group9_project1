package edu.oregonstate.cs361.battleship;
import java.util.*;
import java.util.Random;

/**
 * Created by fbolanos on 2/1/2017.
 */
 
 public class RandShips{

    int[][] board = new int[10][10];

    int[] AircraftCordsStart = new int[2];
    int[] BattleshipCordsStart=new int[2];
    int[] CruiserCordsStart=new int[2];
    int[] DestroyerCordsStart=new int[2];
    int[] SubmarineCordsStart=new int[2];

    int[] AircraftCordsEnd = new int[2];
    int[] BattleshipCordsEnd=new int[2];
    int[] CruiserCordsEnd=new int[2];
    int[] DestroyerCordsEnd=new int[2];
    int[] SubmarineCordsEnd=new int[2];

  
    public RandShips() {
        int lengths[] = {2, 2, 3, 4, 5};

        for (int k = 0; k < 5; k++) {

            int xcord = 0;
            int ycord = 0;
            int orientation = 0;
            int overlap = 0;
            int counter = 0;
            int size= lengths[k];
            int[] StartCord=new int[2];
            int[] EndCord=new int[2];

            Random rand = new Random();
            boolean crash = true;
            boolean sameloc = true;

            while (crash) {
                overlap = 0;
                if (counter == 0)
                    orientation = rand.nextInt(4) + 1;
                else if (counter == 4) {
                    counter = 0;
                    orientation = rand.nextInt(4) + 1;
                    sameloc = true;
                }
                else
                    orientation++;


                while (sameloc) {
                    xcord = rand.nextInt(9);
                    ycord = rand.nextInt(9);

                    if (board[xcord][ycord] == 0)
                        sameloc = false;
                }

                //horizontal right orientation check
                if (orientation % 4 == 1) {
                    if ((ycord + size) <= 9) {
                        for (int i = ycord; i <= (ycord + size); i++) {
                            if (board[xcord][i] == 1)
                                overlap = 1;
                        }

                        if (overlap == 0) {
                            for (int i = ycord; i < (ycord + size); i++) {
                                board[xcord][i] = 1;
                                EndCord[0]=xcord;
                                EndCord[1]=i;
                            }
                            crash = false;
                        }
                    }
                }

                //horizontal left orientation chek
                if (orientation % 4 == 2) {
                    if ((ycord - size) >= 0) {
                        for (int i = ycord - size; i <= ycord; i++) {
                            if (board[xcord][i] == 1)
                                overlap = 1;
                        }

                        if (overlap == 0) {
                            for (int i = ycord - size; i < ycord; i++) {
                                board[xcord][i] = 1;
                            }
                            EndCord[0]=xcord;
                            EndCord[1]=ycord-size;
                            crash = false;
                        }
                    }
                }

                //vertical down orientation check
                if (orientation % 4 == 3) {
                    if ((xcord + size) <= 9) {
                        for (int i = xcord; i <= (xcord + size); i++) {
                            if (board[i][ycord] == 1)
                                overlap = 1;
                        }

                        if (overlap == 0) {
                            for (int i = xcord; i < (xcord + size); i++) {
                                board[i][ycord] = 1;
                                EndCord[0]=i;
                                EndCord[1]=ycord;
                            }
                            crash = false;
                        }
                    }
                }

                // vertical up oreintation check
                if (orientation % 4 == 0) {
                    if ((xcord - size) >= 0) {
                        for (int i = xcord - size; i <= xcord; i++) {
                            if (board[i][ycord] == 1)
                                overlap = 1;
                        }

                        if (overlap == 0) {
                            for (int i = xcord - size; i < xcord; i++) {
                                board[i][ycord] = 1;
                            }
                            EndCord[0]=xcord - size;
                            EndCord[1]=ycord;
                            crash = false;
                        }
                    }
                }
                counter++;
                StartCord[0]=xcord;
                StartCord[1]=ycord;
            }

            //records the starting and ending corddinates for each ship
            if(k==0){
                SubmarineCordsStart=StartCord.clone();
                SubmarineCordsEnd=EndCord.clone();
            }
            else if(k==1){
                DestroyerCordsStart=StartCord.clone();
                DestroyerCordsEnd=EndCord.clone();
            }
            else if(k==2){
                CruiserCordsStart=StartCord.clone();
                CruiserCordsEnd=EndCord.clone();
            }
            else if(k==3){
                BattleshipCordsStart=StartCord.clone();
                BattleshipCordsEnd=EndCord.clone();
            }
            else if(k==4){
                AircraftCordsStart=StartCord.clone();
                AircraftCordsEnd=EndCord.clone();
            }
        }
    }

    //optional function that displays the table of game with ships. 0's are unocupied spaces and 1's are occupied spaces.
    public void print() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(board[x][y]);
            }
            System.out.print("\n");
        }
    }
}
