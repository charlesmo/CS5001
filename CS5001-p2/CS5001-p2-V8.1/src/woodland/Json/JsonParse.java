package woodland.Json;
import woodland.Animals.Animal;
import woodland.Creatures.Creature;
import woodland.Game;
import woodland.Spell;
import woodland.Square;

import java.io.*;
import javax.json.*;

/**
 * parse json information from the request
 */
public class JsonParse {
    public String action;
    public String animalName;
    public String spell;
    public String creatureName;
    public int row;
    public int col;
    public JsonParse (JsonObject body){
        action = body.getString("action");
        animalName = body.getString("animal");
        if (action.equals("spell")){
            spell = body.getString("spell");
        }

        if (action.equals("move")) {
            JsonObject toSquare = body.getJsonObject("toSquare");
            row = toSquare.getInt("row");
            col = toSquare.getInt("col");
        }
    }


}
