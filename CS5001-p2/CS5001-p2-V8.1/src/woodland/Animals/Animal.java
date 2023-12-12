package woodland.Animals;

import woodland.Spell;
import woodland.Square;
import java.util.HashMap;
import java.util.Map;

public class Animal {
    public String name;
    public Map<Spell,Integer> spells;
    public int lifePoints;
    public String description;

    public Square square;

    public Animal(String name){
        this.name = name;
        this.lifePoints = 100;
        spells = new HashMap<Spell,Integer>() ;
    }

    //check if this move is valid
    public boolean move(int oldRow,int oldCol, int newRow, int newCol){
         return false;
    }
    public Square getSquare(){
        return square;
    }

    public void heal(){
        lifePoints += 10;
        if (lifePoints >100 ){
            lifePoints = 100;
        }
    }
    public void attacked(int attackValue){
        lifePoints = lifePoints - attackValue;
    }

    public boolean isAlive(){
        if (lifePoints>0){
            return true;
        }else{
            return false;
        }
    }

    public void addSpell(Spell spell){
        spells.put(spell, spells.getOrDefault(spell,0) +1 );
    }
    public void updateSpell(Spell spell){
        if (spells.get(spell) == 1){
            spells.remove(spell);
        }else{
            spells.put(spell, spells.get(spell) - 1 );
        }

    }

}
