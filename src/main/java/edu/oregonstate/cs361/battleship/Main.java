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
    private static String newModel() {

        GameModel model = new GameModel();
        Gson gson = new Gson();

        System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){

        Gson gson = new Gson();

        //Populate a  BattleshipModel object using JSON data
        return gson.fromJson(req.body(),BattleshipModel.class);

    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
        return null;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        Gson gson = new Gson();
        Random rand = new Random(1);
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

        //checks if any of the two players has won and if so it resets the game
        if(checkWin(model.playerHits) || (checkWin(model.playerHits) && checkWin(model.computerHits))) {
            System.out.println("game won by player");
            model.ResetGame();
        }
        else if(checkWin(model.computerHits) && (!checkWin(model.playerHits))){
            System.out.println("game won by computer");
            model.ResetGame();
        }
        
        System.out.println(gson.toJson(model));

        return gson.toJson(model);
    }

    private static Boolean checkCollision(Coordinate cord, BattleshipModel ac, BattleshipModel bs, BattleshipModel c, BattleshipModel d, BattleshipModel s) {
        //Across = col, Down = row
        //Check if horizontal or vertical
        List<BattleshipModel> shipList;
        shipList = new ArrayList<>();
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

    private static boolean checkRepeatFire(Coordinate cord, List<Coordinate> hit, List<Coordinate> miss) {
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
    private static boolean checkWin(List<Coordinate> hits){
        if(hits.size()==16){
            return true;
        }
        return false;
    }
    

}
