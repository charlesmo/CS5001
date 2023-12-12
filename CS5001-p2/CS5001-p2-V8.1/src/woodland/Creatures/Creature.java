package woodland.Creatures;

import woodland.Animals.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Creature {
    public String name;
    int charmTurns = 3;
    public int attackValue;
    public Map<Animal,Integer> charmAnimal;
    public Animal confusedAnimal;
    public boolean confused;
    public String shortname;
    public String description;
    public List<Animal> shieldAnimal;

    public Creature(String name, int attackValue){
        this.name = name;
        this.attackValue = attackValue;
        charmAnimal = new HashMap<Animal, Integer>();
        shieldAnimal = new ArrayList<Animal>();
    }

    public Creature(String name) {
        this.name = name;
    }
    public void addShieldAnimal(Animal animal){
      shieldAnimal.add(animal);
    }
    public void addCharmAnimal(Animal animal){
        charmAnimal.put(animal, charmTurns);
    }

    public void setConfused(boolean confused){
        this.confused = confused;
    }

    public boolean isCharmed(Animal animal){
        if (charmAnimal.containsKey(animal)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isConfused(){
        return confused;
    }
    public boolean isShieldAnimal(Animal animal){
        if (shieldAnimal.contains(animal)){
            return true;
        }else{
            return false;
        }
    }
    public void updateShieldAnimal(Animal animal){
        shieldAnimal.remove(animal);
    }

    public void updateCharmAnimal(Animal animal){
        if (charmAnimal.get(animal) == 1){
            charmAnimal.remove(animal);
        }else{
            charmAnimal.put(animal, charmAnimal.get(animal) - 1 );
        }
    }
}
