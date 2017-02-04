package edu.oregonstate.cs361.battleship;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function should return a new model
    static String newModel() {

        GameModel model = new GameModel();
        Gson gson = new Gson();
        RandShips computerShips = new RandShips();
        computerShips.print();
        //Initialize computer_aircraftCarrier random coordinates.
        model.computer_aircraftCarrier.start.Across = computerShips.AircraftCordsStart[1] + 1;
        model.computer_aircraftCarrier.start.Down = computerShips.AircraftCordsStart[0] + 1;
        model.computer_aircraftCarrier.end.Across = computerShips.AircraftCordsEnd[1] + 1;
        model.computer_aircraftCarrier.end.Down = computerShips.AircraftCordsEnd[0] + 1;
        //Initialize computer_battleship random coordinates.
        model.computer_battleship.start.Across = computerShips.BattleshipCordsStart[1] + 1;
        model.computer_battleship.start.Down = computerShips.BattleshipCordsStart[0] + 1;
        model.computer_battleship.end.Across = computerShips.BattleshipCordsEnd[1] + 1;
        model.computer_battleship.end.Down = computerShips.BattleshipCordsEnd[0] + 1;
        //Initialize computer_cruiser random coordinates.
        model.computer_cruiser.start.Across = computerShips.CruiserCordsStart[1] + 1;
        model.computer_cruiser.start.Down = computerShips.CruiserCordsStart[0] + 1;
        model.computer_cruiser.end.Across = computerShips.CruiserCordsEnd[1] + 1;
        model.computer_cruiser.end.Down = computerShips.CruiserCordsEnd[0] + 1;
        //Initialize computer_destroyer random coordinates.
        model.computer_destroyer.start.Across = computerShips.DestroyerCordsStart[1] + 1;
        model.computer_destroyer.start.Down = computerShips.DestroyerCordsStart[0] + 1;
        model.computer_destroyer.end.Across = computerShips.DestroyerCordsEnd[1] + 1;
        model.computer_destroyer.end.Down = computerShips.DestroyerCordsEnd[0] + 1;
        //Initialize computer_submarine random coordinates.
        model.computer_submarine.start.Across = computerShips.SubmarineCordsStart[1] + 1;
        model.computer_submarine.start.Down = computerShips.SubmarineCordsStart[0] + 1;
        model.computer_submarine.end.Across = computerShips.SubmarineCordsEnd[1] + 1;
        model.computer_submarine.end.Down = computerShips.SubmarineCordsEnd[0] + 1;
        System.out.println(gson.toJson(model));

        return gson.toJson(model);
    }

    public static boolean checkPlacement(GameModel model, Request req){
        System.out.println("made it to check function");
        int xcord = Integer.parseInt(req.params(":col"));//x coord of ship to place
        int ycord = Integer.parseInt(req.params(":row"));//y coord of ship to place
        int coords[] = new int[20];//array to hold start and end coords of every ship

        Coordinate start = new Coordinate(xcord, ycord);//initialize every segment of the ship
        Coordinate seg1 = new Coordinate(xcord, ycord);
        Coordinate seg2 = new Coordinate(xcord, ycord + 2);
        Coordinate seg3 = new Coordinate(xcord, ycord + 3);
        Coordinate end = new Coordinate(xcord, ycord + 4);

        if(req.params(":orientation").equals("horizontal")) {//change the coordinates of segments to horizontal if the ship is arranged as such
            if (req.params(":id").equals("aircraftCarrier")) {
                seg1 = new Coordinate(xcord + 1, ycord);
                seg2 = new Coordinate(xcord + 2, ycord);
                seg3 = new Coordinate(xcord + 3, ycord);
                end = new Coordinate(xcord + 4, ycord);
            }
        }

        coords[0] = model.aircraftCarrier.start.Across;//set values of coords array, will be replaced with collision code once the bug is found
        coords[1] = model.aircraftCarrier.start.Down;
        coords[2] = model.aircraftCarrier.end.Across;
        coords[3] = model.aircraftCarrier.end.Down;
        coords[4] = model.battleship.start.Across;
        coords[5] = model.battleship.start.Down;
        coords[6] = model.battleship.end.Across;
        coords[7] = model.battleship.end.Down;
        coords[8] = model.cruiser.start.Across;
        coords[9] = model.cruiser.start.Down;
        coords[10] = model.cruiser.end.Across;
        coords[11] = model.cruiser.end.Down;
        coords[12] = model.destroyer.start.Across;
        coords[13] = model.destroyer.start.Down;
        coords[14] = model.destroyer.end.Across;
        coords[15] = model.destroyer.end.Down;
        coords[16] = model.submarine.start.Across;
        coords[17] = model.submarine.start.Down;
        coords[18] = model.submarine.end.Across;
        coords[19] = model.submarine.end.Down;

        String shipname = req.params(":id");

        if (shipname.equals("aircraftCarrier")) {
            if (coords[0] != 0)//Check if the ship has already been placed
                return false;
            if (req.params(":orientation").equals("vertical")) {//checks for out of bounds placement
                if (ycord + 4 > 10)
                    return false;
            } else {
                if (xcord + 4 > 10)
                    return false;
            }
            for (int i = 0; i <= 18; i = i + 2) {//check if the heads/tails of the ship to be placed colllides with the heads/tails of all other ships
                if (coords[i] == xcord && coords[i + 1] == ycord)
                    return false;
            }/*Following code is meant to replace the above for loop by check for a collision of all occupied coordinate but does not work at the moment
               The issue stems from an unintended "collision" detection every time meaning no ship can be placed, most likely because of my misuse of the check
               collision function
            if (checkCollision(start, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false) {
                System.out.println("start passed");
                return false;
            }
            if (checkCollision(seg1, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false) {
                System.out.println("seg1 passed");
                return false;
            }
            if (checkCollision(seg2, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false) {
                System.out.println("seg2 passed");
                return false;
            }
            if(checkCollision(seg3, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false) {
                System.out.println("seg3 passed");
                return false;
            }
            if(checkCollision(end, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false) {
                System.out.println("end passed");
                return false;
            }*/
        } else if (shipname.equals("battleship")){
            if(coords[4] != 0)
                return false;
            System.out.println("Checking battleship placement, passed already placed test");
            if (req.params(":orientation").equals("vertical")){
                if(ycord + 3 > 10)
                    return false;
            } else {
                if (xcord + 3 > 10)
                    return false;
            }
            System.out.println("Checking battleship placement, passed oob test");
            for(int i = 0; i <= 18; i = i + 2){
                if(coords[i] == xcord && coords[i+1] == ycord)
                    return false;
            }
            System.out.println("Checking battleship placement, passed start end collision test");
            /*
            if(checkCollision(start, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg1, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg2, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg3, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            */
            if(coords[4] != 0)
                return false;
        } else if (shipname.equals("cruiser")){
            if(coords[8] != 0)
                return false;
            if (req.params(":orientation").equals("vertical")){
                if(ycord + 2 > 10)
                    return false;
            } else {
                if (xcord + 2 > 10)
                    return false;
            }
            for(int i = 0; i <= 18; i = i + 2){
                if(coords[i] == xcord && coords[i+1] == ycord)
                    return false;
            }
            /*
            if(checkCollision(start, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg1, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg2, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            */
        } else if (shipname.equals("destroyer") || shipname.equals("submarine")){
            if(coords[12] != 0 && coords[16] != 0)
                return false;
            if (req.params(":orientation").equals("vertical")){
                if(ycord + 1 > 10)
                    return false;
            } else {
                if (xcord + 1 > 10)
                    return false;
            }
            for(int i = 0; i <= 18; i = i + 2){
                if(coords[i] == xcord && coords[i+1] == ycord)
                    return false;
            }
            /*
            if(checkCollision(start, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            if(checkCollision(seg1, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine) == false)
                return false;
            */
        }
        System.out.println("Placement passed test");
        return true;
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        Gson gson = new Gson();
        GameModel model = gson.fromJson(req.body(),GameModel.class);
        String shipName = req.params(":id");
        Coordinate fire = new Coordinate(Integer.parseInt(req.params(":row")), Integer.parseInt(req.params(":col")));

        if (checkPlacement(model, req) == true) {

            if (shipName.equals("aircraftCarrier")) {
                model.aircraftCarrier.start = fire;
                if (req.params(":orientation").equals("horizontal")) {
                    model.aircraftCarrier.end.Down = fire.Down + 4;
                    model.aircraftCarrier.end.Across = fire.Across;
                } else {
                    model.aircraftCarrier.end.Across = fire.Across + 4;
                    model.aircraftCarrier.end.Down = fire.Down;
                }
            } else if (shipName.equals("battleship")) {
                model.battleship.start = fire;
                if (req.params(":orientation").equals("horizontal")) {
                    model.battleship.end.Down = fire.Down + 3;
                    model.battleship.end.Across = fire.Across;
                } else {
                    model.battleship.end.Across = fire.Across + 3;
                    model.battleship.end.Down = fire.Down;
                }
            } else if (shipName.equals("cruiser")) {
                model.cruiser.start = fire;
                if (req.params(":orientation").equals("horizontal")) {
                    model.cruiser.end.Down = fire.Down + 2;
                    model.cruiser.end.Across = fire.Across;
                } else {
                    model.cruiser.end.Across = fire.Across + 2;
                    model.cruiser.end.Down = fire.Down;
                }
            } else if (shipName.equals("destroyer")) {
                model.destroyer.start = fire;
                if (req.params(":orientation").equals("horizontal")) {
                    model.destroyer.end.Down = fire.Down + 1;
                    model.destroyer.end.Across = fire.Across;
                } else {
                    model.destroyer.end.Across = fire.Across + 1;
                    model.destroyer.end.Down = fire.Down;
                }
            } else if (shipName.equals("submarine")) {
                model.submarine.start = fire;
                if (req.params(":orientation").equals("horizontal")) {
                    model.submarine.end.Down = fire.Down + 1;
                    model.submarine.end.Across = fire.Across;
                } else {
                    model.submarine.end.Across = fire.Across + 1;
                    model.submarine.end.Down = fire.Down;
                }
            }
        } else
            System.out.println("Invalid placement");
        return gson.toJson(model);
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        Random rand = new Random(1);
        Gson gson = new Gson();
        GameModel model = gson.fromJson(req.body(),GameModel.class);
        Coordinate fire = new Coordinate(Integer.parseInt(req.params(":col")), Integer.parseInt(req.params(":row")));

        if (! checkRepeatFire(fire, model.playerHits, model.playerMisses)) {
            if (checkCollision(fire, model.computer_aircraftCarrier, model.computer_battleship, model.computer_cruiser, model.computer_destroyer, model.computer_submarine)) {
                model.playerHits.add(fire);
            } else {
                model.playerMisses.add(fire);
            }
        }


        Coordinate fireAI = new Coordinate(rand.nextInt(10) + 1, rand.nextInt(10) + 1);
        //Check for location that isn't already on board.
        while (checkRepeatFire(fireAI, model.computerHits, model.computerMisses)) {
            fireAI.Across = rand.nextInt(10) + 1;
            fireAI.Down = rand.nextInt(10) + 1;
        }

        //Check if AI hit the players ships
        if (checkCollision(fireAI, model.aircraftCarrier, model.battleship, model.cruiser, model.destroyer, model.submarine)) {
            model.computerHits.add(fireAI);
        } else {
            model.computerMisses.add(fireAI);
        }

        if (checkWin(model.playerHits, model.computerHits)) {
            model.ResetGame();
        }

        System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    static boolean checkCollision(Coordinate cord, BattleshipModel ac, BattleshipModel bs, BattleshipModel c, BattleshipModel d, BattleshipModel s) {
        //Across = col, Down = row
        //Check if horizontal or vertical
        ArrayList<BattleshipModel> shipList = new ArrayList<BattleshipModel>();
        shipList.add(ac);
        shipList.add(bs);
        shipList.add(c);
        shipList.add(d);
        shipList.add(s);
        for (int i = 0; i<5; i++) {
            BattleshipModel temp = shipList.get(i);
            if (temp.start.Across == temp.end.Across && cord.Across == temp.start.Across) {
                if (cord.Down >= temp.start.Down && cord.Down <= temp.end.Down)
                    return true;
            } else if (temp.start.Down == cord.Down) { //Horizontal
                if (cord.Across >= temp.start.Across && cord.Across <= temp.end.Across)
                    return true;
            }
        }
        return false;
    }

    static boolean checkRepeatFire(Coordinate cord, List<Coordinate> hit, List<Coordinate> miss) {

        for (Coordinate aHit : hit) {
            if (cord.Across == aHit.Across && cord.Down == aHit.Down)
                return true;
        }
        for (Coordinate aMiss : miss) {
            if (cord.Across == aMiss.Across && cord.Down == aMiss.Down)
                return true;
        }
        return false;
    }
    
    //function that checks if won
    private static boolean checkWin(List<Coordinate> phits, List<Coordinate> chits){
        boolean playerWin=false;
        boolean computerWin=false;

        if(phits.size()==16)
            playerWin=true;

        if(chits.size()==16)
            computerWin=true;


        //checks if any of the two players has won and if so it resets the game
        if(playerWin || (playerWin && computerWin)) {
            System.out.println("Game won by player");
            return true;
        }
        else if(computerWin && (!playerWin)){
            System.out.println("Game won by computer");
            return true;
        }
        else
            return false;
    }
    

}
