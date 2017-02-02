package edu.oregonstate.cs361.battleship;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evtak_000 on 1/29/2017.
 */
public class GameModel {

    //User ships
    public BattleshipModel aircraftCarrier,
                            battleship,
                            cruiser,
                            destroyer,
                            submarine;

    //Computer ships
    public BattleshipModel computer_aircraftCarrier,
                            computer_battleship,
                            computer_cruiser,
                            computer_destroyer,
                            computer_submarine;

    List<Coordinate> playerHits;
    List<Coordinate> playerMisses;
    List<Coordinate> computerHits;
    List<Coordinate> computerMisses;

    public GameModel(){

        aircraftCarrier = new BattleshipModel("AircraftCarrier",5,
                new Coordinate(0,0), new Coordinate(0,0));
        battleship      = new BattleshipModel("Battleship",4,
                new Coordinate(0,0), new Coordinate(0,0));
        cruiser         = new BattleshipModel("Cruiser",3,
                new Coordinate(0,0), new Coordinate(0,0));
        destroyer       = new BattleshipModel("Destroyer",2,
                new Coordinate(0,0), new Coordinate(0,0));
        submarine       = new BattleshipModel("Submarine", 2,
                new Coordinate(0,0), new Coordinate(0,0));

        computer_aircraftCarrier = new BattleshipModel("Computer_AircraftCarrier",5,
                new Coordinate(2,2), new Coordinate(2,6));
        computer_battleship      = new BattleshipModel("Computer_Battleship",4,
                new Coordinate(2,8), new Coordinate(5,8));
        computer_cruiser         = new BattleshipModel("Computer_Cruiser",3,
                new Coordinate(4,1), new Coordinate(4,3));
        computer_destroyer       = new BattleshipModel("Computer_Destroyer",2,
                new Coordinate(7,3), new Coordinate(7,4));
        computer_submarine       = new BattleshipModel("Computer_Submarine", 2,
                new Coordinate(9,6), new Coordinate(9,7));

        playerHits      = new ArrayList<Coordinate>();
        playerMisses    = new ArrayList<Coordinate>();
        computerHits    = new ArrayList<Coordinate>();
        computerMisses  = new ArrayList<Coordinate>();
    }
}
