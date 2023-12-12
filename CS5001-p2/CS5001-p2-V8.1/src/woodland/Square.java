package woodland;

import woodland.Animals.Animal;
import woodland.Creatures.Creature;

public class Square {
    int row;
    int col;
    boolean visible;

    Animal animal;
    Creature creature;

    Spell spell ;
    public Square(int row, int col){
        this.row = row;
        this.col = col;
    }


    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public boolean isVisible(){
        return visible;
    }

    public void reveal(){
        visible = true;
    }
    public boolean hasCreature(){
        if (creature !=null ){
            return true;
        }else{
            return false;
        }
    }
    public boolean hasAnimal(){
        if (animal != null){
            return true;
        }else{
            return false;
        }
    }
    public boolean hasSpell(){
        if (spell!=null ){
            return true;
        }else{
            return false;
        }
    }

    public void setAnimal(Animal animal){
      animal.square = this;
      animal.lifePoints = 100;
      this.animal = animal;
    }

    public void setCreature(Creature creature){
        this.creature = creature;
    }

    public Animal getAnimal(){
        return animal;
    }
    public Creature getCreature(){
        return creature;
    }
    public Spell getSpell(){
        return spell;
    }
    public void setSpell(Spell spell){
      this.spell = spell;
    }


}
