package edu.oregonstate.cs361.battleship;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        GameModel model = gson.fromJson(req.body(),GameModel.class);
        Coordinate fire = new Coordinate(Integer.parseInt(req.params(":col")), Integer.parseInt(req.params(":row")));
        int temp = fire.Across;

        if (! checkRepeatFire(fire, model)) {
            if (checkCollision(fire, model) == true) {
                model.playerHits.add(fire);
            } else {
                model.playerMisses.add(fire);
            }
        }
        
        System.out.print(fire.Across);
        System.out.print(fire.Down);
        System.out.println(gson.toJson(model));

        return gson.toJson(model);
    }

    private static Boolean checkCollision(Coordinate cord, GameModel model) {
        //Across = col, Down = row
        //Check if horizontal or vertical

        //computer_aircraftCarrier
        if (model.computer_aircraftCarrier.start.Across == model.computer_aircraftCarrier.end.Across && cord.Across == model.computer_aircraftCarrier.start.Across) {
            if (cord.Down >= model.computer_aircraftCarrier.start.Down && cord.Down <= model.computer_aircraftCarrier.end.Down)
                return true;
        } else if (model.computer_aircraftCarrier.start.Down == cord.Down){ //Horizontal
            if (cord.Across >= model.computer_aircraftCarrier.start.Across && cord.Across <= model.computer_aircraftCarrier.end.Across)
                return true;
        }

        //computer_battleship
        if (model.computer_battleship.start.Across == model.computer_battleship.end.Across && cord.Across == model.computer_battleship.start.Across) {
            if (cord.Down >= model.computer_battleship.start.Down && cord.Down <= model.computer_battleship.end.Down)
                return true;
        } else if (model.computer_battleship.start.Down == cord.Down){ //Horizontal
            if (cord.Across >= model.computer_battleship.start.Across && cord.Across <= model.computer_battleship.end.Across)
                return true;
        }

        //computer_cruiser
        if (model.computer_cruiser.start.Across == model.computer_cruiser.end.Across && cord.Across == model.computer_cruiser.start.Across) {
            if (cord.Down >= model.computer_cruiser.start.Down && cord.Down <= model.computer_cruiser.end.Down)
                return true;
        } else if (model.computer_cruiser.start.Down == cord.Down){ //Horizontal
            if (cord.Across >= model.computer_cruiser.start.Across && cord.Across <= model.computer_cruiser.end.Across)
                return true;
        }

        //computer_destroyer
        if (model.computer_destroyer.start.Across == model.computer_destroyer.end.Across && cord.Across == model.computer_destroyer.start.Across) {
            if (cord.Down >= model.computer_destroyer.start.Down && cord.Down <= model.computer_destroyer.end.Down)
                return true;
        } else if (model.computer_destroyer.start.Down == cord.Down){ //Horizontal
            if (cord.Across >= model.computer_destroyer.start.Across && cord.Across <= model.computer_destroyer.end.Across)
                return true;
        }

        //computer_submarine
        if (model.computer_submarine.start.Across == model.computer_submarine.end.Across && cord.Across == model.computer_submarine.start.Across) {
            if (cord.Down >= model.computer_submarine.start.Down && cord.Down <= model.computer_submarine.end.Down)
                return true;
        } else if (model.computer_submarine.start.Down == cord.Down){ //Horizontal
            if (cord.Across >= model.computer_submarine.start.Across && cord.Across <= model.computer_submarine.end.Across)
                return true;
        }
        return false;
    }

    private static boolean checkRepeatFire(Coordinate cord, GameModel model) {
        for (int i = 0; i < model.playerHits.size(); i++) {
            if (cord.Across == model.playerHits.get(i).Across && cord.Down == model.playerHits.get(i).Down)
                return true;
        }
        for (int i = 0; i < model.playerMisses.size(); i++) {
            if (cord.Across == model.playerMisses.get(i).Across && cord.Down == model.playerMisses.get(i).Down)
                return true;
        }
        return false;
    }

}