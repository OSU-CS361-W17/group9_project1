package edu.oregonstate.cs361.battleship;

import java.awt.*;

public class BattleshipModel {

    //Name of the ship
    String name;

    //Length of the ship in squares
    int length;

    //Start/End coordinates
    Coordinate start, end;

    public BattleshipModel(){

        name = "";
        length = 0;
        start = new Coordinate(0,0);
        end = new Coordinate(0,0);
    }

    public BattleshipModel( String n, int l, Coordinate s, Coordinate e){

        name = n;
        length = l;
        start = s;
        end = e;
    }
    
    public void ResetLoc(){
        start = new Coordinate(0,0);
        end = new Coordinate(0,0);
    }
    
}
