package woodland.Json;
import woodland.Animals.Animal;
import woodland.Creatures.Creature;
import woodland.Game;
import woodland.Spell;
import woodland.Square;

import java.io.*;
import javax.json.*;
public class JsonProcess {
    /** produce the json file for http request
     * @param game the current game
     * @return the string for json file
     */
    public  String print(Game game){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Square[] row: game.getBoard()){
            JsonArrayBuilder innerArrayBuilder = Json.createArrayBuilder();
            for (Square k : row){
                if (k == null){
                    innerArrayBuilder.add(Json.createArrayBuilder().build());
                    continue;
                }
                innerArrayBuilder.add(printSquare(k));
            }
            jsonArrayBuilder.add(innerArrayBuilder.build());
        }

        JsonArray jsonArray = jsonArrayBuilder.build();
        String printTurnType;
        if (game.currentAnimalTurnType.equals("move")){
            printTurnType = "Move";
        }else{
            printTurnType = "Spell";
        }
        JsonObject jsonBoard = Json.createObjectBuilder()
                .add("board",jsonArray)
                .add("gameOver",game.gameOver())
                .add("currentAnimalTurn",game.currentAnimalTurn.name)
                .add("nextAnimalTurn",game.nextAnimalTurn.name)
                .add("status",game.statusMessage)
                .add("currentAnimalTurnType", printTurnType)
                .add("extendedStatus", "")
                .build();
      return jsonBoard.toString();
    }

    public JsonArrayBuilder printSquare(Square n) {
        JsonArrayBuilder jsonSquareBuilder =Json.createArrayBuilder();
        if (n.hasAnimal()) {
            jsonSquareBuilder.add(printAnimal(n.getAnimal()));
        }
        if (n.hasCreature()){
            jsonSquareBuilder.add(printCreature(n.getCreature()));
        }
        //JsonObject jsonSquare = jsonSquareBuilder.build();
       // System.out.println(jsonSquare.toString());
        return jsonSquareBuilder;
    }

    public JsonObjectBuilder printAnimal(Animal n){
        JsonObjectBuilder jsonAnimalBuilder =Json.createObjectBuilder();

            jsonAnimalBuilder.add("name",n.name);

            jsonAnimalBuilder.add("type","Animal");
            jsonAnimalBuilder.add("description",n.description);
            jsonAnimalBuilder.add("life",n.lifePoints);
        //Create a jsonObject for charmed animals
        JsonArrayBuilder jsonSpellBuilder =Json.createArrayBuilder();
        if (n.spells != null) {
            for (Spell spell : n.spells.keySet()) {
                JsonObjectBuilder jsonEachSpell = Json.createObjectBuilder();
                jsonEachSpell.add("name", spell.getName());
                jsonEachSpell.add("description", spell.getDescription());
                jsonEachSpell.add("amount", n.spells.get(spell));
                jsonSpellBuilder.add(jsonEachSpell);
            }
        }
        jsonAnimalBuilder.add("spells",jsonSpellBuilder);
            // jsonAnimalBuilder.add("description",n.description);
            // System.out.println(jsonAnimalBuilder.build().toString());

       // JsonObject jsonAnimal= jsonAnimalBuilder.build();
       // System.out.println(jsonAnimal.toString());
        return jsonAnimalBuilder;
    }

    public JsonObjectBuilder printCreature(Creature n){
        JsonObjectBuilder jsonCreatureBuilder =Json.createObjectBuilder();
            jsonCreatureBuilder.add("name",n.name);
            jsonCreatureBuilder.add("type","Creature");
            jsonCreatureBuilder.add("shortname",n.shortname);
            jsonCreatureBuilder.add("description",n.description);
            jsonCreatureBuilder.add("attack",n.attackValue);
        jsonCreatureBuilder.add("confused",n.isConfused());
            //Create a jsonObject for charmed animals
            JsonArrayBuilder jsonCharmAnimalBuilder =Json.createArrayBuilder();
            if (n.charmAnimal != null) {
                for (Animal animal : n.charmAnimal.keySet()) {
                    JsonObjectBuilder jsonEachCharm = Json.createObjectBuilder();
                    jsonEachCharm.add("animal", animal.name);
                    jsonEachCharm.add("turnLeft", n.charmAnimal.get(animal));
                    jsonCharmAnimalBuilder.add(jsonEachCharm);
                }
            }
            jsonCreatureBuilder.add("charmed",jsonCharmAnimalBuilder);

        // System.out.println(jsonCreature.toString());
        return jsonCreatureBuilder;
    }
 }


