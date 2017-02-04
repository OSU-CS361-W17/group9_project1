package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;


import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {
    String testPlaceShip = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":5}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testPlaceShip2 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":3,\"Down\":5},\"end\":{\"Across\":7,\"Down\":5}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testPlaceShip4 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":3}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testPlaceShip5 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":2}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testPlaceShip6 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":2}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testPlaceShip3 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":4}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";

    String cleanGame = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String getModelTestRand = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[]}";
    String testFire1 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testFire2 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat1 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat2 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[],\"playerMisses\":[{\"Across\":1,\"Down\":1}],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9},{\"Across\":8,\"Down\":4}]}";
    String testRepeat3 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9}]}";
    String testRepeat4 = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":4,\"Down\":1},\"end\":{\"Across\":4,\"Down\":3}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":7,\"Down\":3},\"end\":{\"Across\":7,\"Down\":4}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":7}},\"playerHits\":[{\"Across\":2,\"Down\":2}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9},{\"Across\":8,\"Down\":4}]}";
    String testEndGame = "{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"cruiser\":{\"name\":\"Cruiser\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"destroyer\":{\"name\":\"Destroyer\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"submarine\":{\"name\":\"Submarine\",\"length\":2,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0}},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":6,\"Down\":8}},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":8,\"Down\":2},\"end\":{\"Across\":8,\"Down\":5}},\"computer_cruiser\":{\"name\":\"Computer_Cruiser\",\"length\":3,\"start\":{\"Across\":6,\"Down\":5},\"end\":{\"Across\":6,\"Down\":7}},\"computer_destroyer\":{\"name\":\"Computer_Destroyer\",\"length\":2,\"start\":{\"Across\":9,\"Down\":7},\"end\":{\"Across\":9,\"Down\":8}},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":2,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":3}},\"playerHits\":[{\"Across\":2,\"Down\":3},{\"Across\":8,\"Down\":2},{\"Across\":8,\"Down\":3},{\"Across\":8,\"Down\":4},{\"Across\":8,\"Down\":5},{\"Across\":6,\"Down\":5},{\"Across\":6,\"Down\":6},{\"Across\":6,\"Down\":7},{\"Across\":6,\"Down\":8},{\"Across\":5,\"Down\":8},{\"Across\":4,\"Down\":8},{\"Across\":3,\"Down\":8},{\"Across\":2,\"Down\":8},{\"Across\":9,\"Down\":8},{\"Across\":9,\"Down\":7}],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[{\"Across\":6,\"Down\":9},{\"Across\":8,\"Down\":4},{\"Across\":5,\"Down\":5},{\"Across\":5,\"Down\":7},{\"Across\":9,\"Down\":9},{\"Across\":10,\"Down\":4},{\"Across\":3,\"Down\":5},{\"Across\":3,\"Down\":3},{\"Across\":7,\"Down\":10},{\"Across\":7,\"Down\":3},{\"Across\":1,\"Down\":10},{\"Across\":5,\"Down\":10},{\"Across\":9,\"Down\":4},{\"Across\":8,\"Down\":3},{\"Across\":6,\"Down\":5},{\"Across\":1,\"Down\":7}]}";

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    //Starting game user story test code.
    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model", null);
        assertEquals(200, res.status);
        assertEquals(getModelTestRand,res.body);
    }
    //Placing ships User Story test code
    @Test
    public void testPlaceShip() {

        TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal", getModelTestRand);
        assertEquals(200, res.status);
        System.out.print(res.body);

        assertEquals(testPlaceShip,res.body);
    }
    //Placing ships User Story test code
    @Test
    public void testPlaceShipBattleship() {

        TestResponse res = request("POST", "/placeShip/battleship/1/1/horizontal", getModelTestRand);
        assertEquals(200, res.status);
        System.out.print(res.body);
        assertEquals(testPlaceShip3,res.body);
    }

    @Test
    public void testPlaceShipCruiser() {

        TestResponse res = request("POST", "/placeShip/cruiser/1/1/horizontal", getModelTestRand);
        assertEquals(200, res.status);
        System.out.print(res.body);
        assertEquals(testPlaceShip4,res.body);
    }

    @Test
    public void testPlaceShipSubmarine() {

        TestResponse res = request("POST", "/placeShip/submarine/1/1/horizontal", getModelTestRand);
        assertEquals(200, res.status);
        System.out.print(res.body);
        assertEquals(testPlaceShip6,res.body);
    }

    @Test
    public void testPlaceShipDestroyer() {

        TestResponse res = request("POST", "/placeShip/destroyer/1/1/horizontal", getModelTestRand);
        assertEquals(200, res.status);
        System.out.print(res.body);
        assertEquals(testPlaceShip5,res.body);
    }
    //Placing ships User Story test code
    @Test
    public void testPlaceShipBounds() {

        TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/10/horizontal", cleanGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }

    @Test
    public void testPlaceShipBounds2() {

        TestResponse res = request("POST", "/placeShip/battleship/1/10/horizontal", cleanGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }

    @Test
    public void testPlaceShipBounds3() {

        TestResponse res = request("POST", "/placeShip/submarine/1/10/horizontal", cleanGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }

    @Test
    public void testPlaceShipBounds4() {

        TestResponse res = request("POST", "/placeShip/destroyer/1/10/horizontal", cleanGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }

    @Test
    public void testPlaceShipBounds5() {

        TestResponse res = request("POST", "/placeShip/cruiser/1/10/horizontal", cleanGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }

    //Placing ships User Story test code
    @Test
    public void testPlaceShipStacked() {

        TestResponse res = request("POST", "/placeShip/Cruiser/1/1/horizontal", testPlaceShip);
        assertEquals(200, res.status);
        assertEquals(testPlaceShip,res.body);
    }
    //Placing ships User Story test code
    @Test
    public void testPlaceShipIntersect() {

        TestResponse res = request("POST", "/placeShip/Cruiser/4/5/vertical", testPlaceShip2);
        assertEquals(200, res.status);
        assertEquals(testPlaceShip2,res.body);
    }
    //Firing at ships user story test code
    @Test
    public void testFireAtMiss() {
        GameModel model = new GameModel();
        Gson gson = new Gson();
        TestResponse res = request("POST", "/fire/1/1", gson.toJson(model));
        assertEquals(200, res.status);
        assertEquals(testFire1,res.body);
    }
    //Firing at ships user story test code
    @Test
    public void testFireAtHit() {
        GameModel model = new GameModel();
        Gson gson = new Gson();
        TestResponse res = request("POST", "/fire/2/2", gson.toJson(model));
        assertEquals(200, res.status);
        assertEquals(testFire2,res.body);
    }
    //Firing at ships user story test code
    @Test
    public void testRepeatFireMiss() {
        TestResponse res = request("POST", "/fire/1/1", testRepeat1);
        assertEquals(200, res.status);
        assertEquals(testRepeat2,res.body);
    }
    //Firing at ships user story test code
    @Test
    public void testRepeatFireHit() {
        TestResponse res = request("POST", "/fire/2/2", testRepeat3);
        assertEquals(200, res.status);
        assertEquals(testRepeat4,res.body);
    }
    //Ending the game user story test code.
    @Test
    public void testEndGame() {
        TestResponse res = request("POST", "/fire/2/2", testEndGame);
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }
    //Ending the game user story test code.
    @Test
    public void testReset() {
        GameModel model = new GameModel();
        //coordinates for hits
        Coordinate hit=new Coordinate(2,2);
        Coordinate hit2=new Coordinate(3,2);
        Coordinate hit3=new Coordinate(4,2);
        Coordinate hit4=new Coordinate(5,2);
        Coordinate hit5=new Coordinate(6,2);
        Coordinate hit6=new Coordinate(8,2);
        Coordinate hit7=new Coordinate(8,3);
        Coordinate hit8=new Coordinate(8,4);
        Coordinate hit9=new Coordinate(8,5);
        Coordinate hit10=new Coordinate(1,4);
        Coordinate hit11=new Coordinate(2,4);
        Coordinate hit12=new Coordinate(3,4);
        Coordinate hit13=new Coordinate(3,7);
        Coordinate hit14=new Coordinate(4,7);
        Coordinate hit15=new Coordinate(6,9);

        //add 15 cordinate hits to playerhits array
        model.playerHits.add(hit);
        model.playerHits.add(hit2);
        model.playerHits.add(hit3);
        model.playerHits.add(hit4);
        model.playerHits.add(hit5);
        model.playerHits.add(hit6);
        model.playerHits.add(hit7);
        model.playerHits.add(hit8);
        model.playerHits.add(hit9);
        model.playerHits.add(hit10);
        model.playerHits.add(hit11);
        model.playerHits.add(hit12);
        model.playerHits.add(hit13);
        model.playerHits.add(hit14);
        model.playerHits.add(hit15);

        Gson gson = new Gson();
        //this test response is the coordinate for the last hit possible to end the game
        TestResponse res = request("POST", "/fire/7/9", gson.toJson(model));
        assertEquals(200, res.status);
        assertEquals(cleanGame,res.body);
    }



    private TestResponse request(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                byte[] outputInBytes = body.getBytes("UTF-8");
                os.write(outputInBytes);
            }
            connection.connect();
            body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}