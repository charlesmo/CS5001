package woodland;

import woodland.Animals.*;
import woodland.Creatures.*;
import woodland.Json.JsonParse;

import java.util.ArrayList;
import java.util.Random;

/** The game class : some customized parameters and methods
 *  lastPostAnimal: cache the last valid post animal to track turns
 *  turncheck method: to record turn increment and theck the shieldanimal, charmanimal,confused animal
 */
public class Game {

    int upperbound = 20;
    public int ROW = 20;
    public int COL = 20;
    Square[][] board;
    int turn;

    public String statusMessage;
    public String lastPostAnimal;
    public String currentAnimalTurnType;

    public ArrayList<Animal> animals ;
    public ArrayList<Creature> creatures;
    Animal rabbit;
    Animal fox;
    Animal deer;
    Animal owl;
    Animal badger;
    Creature UU;
    Creature CC;
    Creature DD;
    Creature SS;
    Creature PP;

    public long seed;
    public Random rand;
    public Animal currentAnimalTurn;
    public boolean flagStart;
    public Animal nextAnimalTurn;
    //Game start and board setup
    public Game(long newSeed){
        //Set up the new game

        seed = newSeed;
        reset();
    }

    public void  reset(){
        board = new Square[upperbound][upperbound];
        flagStart = false;
        turn = 0;
        rand = new Random(seed);
        animalSetup();
        statusMessage = "";
        currentAnimalTurnType = "move";
        currentAnimalTurn = rabbit;
        nextAnimalTurn = fox;
        lastPostAnimal = null;
    }

    private void animalSetup(){
        animals = new ArrayList<>();
        this.rabbit = new Rabbit("Rabbit");
        animals.add(rabbit);
        this.fox = new Fox("Fox");
        animals.add(fox);
        this.deer = new Deer("Deer");
        animals.add(deer);
        this.owl = new Owl("Owl");
        animals.add(owl);
        this.badger = new Badger("Badger");
        animals.add(badger);
        //Randomize animal setup placement
        for (Animal n : animals){
            int colAnimal = rand.nextInt(upperbound);
            int rowAnimal = upperbound - 1;
            // random column value to avoid collision
            while (board[rowAnimal][colAnimal] != null)  {
                colAnimal = rand.nextInt(upperbound);
            }
            Square current = new Square(rowAnimal,colAnimal);
            current.setAnimal(n);
            board[rowAnimal][colAnimal] = current;
        }
        System.out.println("animals number: "+animals.size());
    }
    public void creatureSetup(){
        creatures = new ArrayList<>();
        UU = new UnderAppreciatedUnicorn("Under-appreciated Unicorn",14);
        creatures.add(UU);
        CC = new ComplicatedCentaur("Complicated Centaur",36);
        creatures.add(CC);
        DD = new DeceptiveDragon("Deceptive Dragon",29);
        creatures.add(DD);
        PP = new PrecociousPhoenix("Precocious Phoenix",42);
        creatures.add(PP);
        SS = new SassySphinx("Sassy Sphinx",21);
        creatures.add(SS);

        //Randomize animal setup placement
        for (Creature n : creatures){
            int rowCreature = rand.nextInt(upperbound-2)+1;
            int colCreature = rand.nextInt(upperbound);
            while (board[rowCreature][colCreature] != null)  {
                colCreature = rand.nextInt(upperbound);
            }

            Square current = new Square(rowCreature,colCreature);
            current.setCreature(n);
            board[rowCreature][colCreature] = current;
        }
    }

    public void spellSetup(){
        //Randomize spell setup placement
        for (int i=0; i<10; i++) {
            int indexOfSpell = rand.nextInt(5);
            Spell n = Spell.values()[indexOfSpell];
            int rowSpell = rand.nextInt(upperbound - 2) + 1;
            int colSpell = rand.nextInt(upperbound);
            while (board[rowSpell][colSpell] != null)  {
                colSpell = rand.nextInt(upperbound);
            }
            Square current = new Square(rowSpell, colSpell);
            current.setSpell(n);
            board[rowSpell][colSpell] = current;
           //  System.out.println(n.getName()+"  "+rowSpell+" "+colSpell);
        }
    }

    public void checkMove(JsonParse parse){
        Animal animalNow = null;
        for (Animal n:animals){
            if (n.name.equals(parse.animalName) ){
             animalNow = n;
             break;
            }
        }
        if (animalNow == null ){
            return;
        }
        boolean checkValidTurn;
        boolean checkMovable;
        switch (animalNow.name) {
            case "Rabbit" -> {
                Rabbit rabbitNow = (Rabbit) animalNow;
                checkValidTurn = (currentAnimalTurn.name.equals(animalNow.name) && currentAnimalTurnType.equals(parse.action)) || (parse.action.equals("move") && currentAnimalTurnType.equals("spell") && nextAnimalTurn.name.equals(animalNow.name));
                if (!checkValidTurn) {
                    statusMessage = "The last move was invalid.";
                    return;
                }
                checkMovable = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col) || rabbitNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                if (!inBound(parse.row, parse.col) || getSquare(parse.row, parse.col).hasAnimal() || !checkMovable) {
                    statusMessage = "The last move was invalid.";
                    System.out.println(statusMessage);
                } else {
                    int newCol = parse.col;
                    int newRow = parse.row;
                    //check different condition for jump
                    if (rabbitNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol)) {
                        int middleCol = (parse.col + animalNow.getSquare().col) / 2;
                        int middleRow = (parse.row + animalNow.getSquare().row) / 2;
                        if (getSquare(middleRow, middleCol).hasAnimal()) {
                            statusMessage = "The last move was invalid.";
                            System.out.println(statusMessage);
                            return;
                        }
                        if (getSquare(middleRow, middleCol).hasSpell()) {
                            getSquare(middleRow, middleCol).reveal();
                        }

                        if (getSquare(middleRow, middleCol).hasCreature()) {
                            newCol = middleCol;
                            newRow = middleRow;
                            statusMessage = "The last move was interrupted by a creature.";
                        }
                    }
                    moveAnimal(animalNow, animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol);
                    currentAnimalTurn = animalNow;
                    currentAnimalTurnType = "spell";
                    nextAnimalTurn = fox;
                    // check attack
                    if (animalNow.getSquare().hasCreature()) {
                        attackAnimal(animalNow.square.getCreature(), animalNow);
                        animalNow.getSquare().reveal();
                        // gameOver();
                    }
                    //save the spell
                    if (animalNow.getSquare().hasSpell()) {
                        saveSpell(animalNow, animalNow.getSquare().spell);
                        animalNow.getSquare().spell = null;
                    }
                    statusMessage = "The last move was successful.";
                    System.out.println(statusMessage);
                }
            }
            case "Fox" -> {
                Fox foxNow = (Fox) animalNow;
                checkValidTurn = (currentAnimalTurn.name.equals(animalNow.name) && currentAnimalTurnType.equals(parse.action)) || (parse.action.equals("move") && currentAnimalTurnType.equals("spell") && nextAnimalTurn.name.equals(animalNow.name));
                if (!checkValidTurn) {
                    statusMessage = "The last move was invalid.";
                    return;
                }
                checkMovable = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col) || foxNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                if (!inBound(parse.row, parse.col) || getSquare(parse.row, parse.col).hasAnimal() || !checkMovable) {
                    statusMessage = "The last move was invalid.";
                    System.out.println(statusMessage);
                } else {
                    int newCol = parse.col;
                    int newRow = parse.row;
                    int diffCol = newCol - animalNow.getSquare().col;
                    int diffRow = newRow - animalNow.getSquare().row;
                    //check different condition for jump
                    if (foxNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol)) {
                        int step = Math.max(Math.abs(diffCol), Math.abs(diffRow)) - 1;
                        int middleCol;
                        int middleRow;
                        //check each interval square
                        for (int i = 1; i <= step; i++) {
                            if (animalNow.getSquare().col == newCol) {
                                middleCol = newCol;
                            } else {
                                middleCol = animalNow.getSquare().col + diffCol / Math.abs(diffCol) * i;
                            }

                            if (animalNow.getSquare().row == newRow) {
                                middleRow = newRow;
                            } else {
                                middleRow = animalNow.getSquare().row + diffRow / Math.abs(diffRow) * i;
                            }

                            if (getSquare(middleRow, middleCol).hasAnimal()) {
                                statusMessage = "The last move was invalid.";
                                System.out.println(statusMessage);
                                return;
                            }
                            if (getSquare(middleRow, middleCol).hasSpell()) {
                                getSquare(middleRow, middleCol).reveal();
                            }

                            if (getSquare(middleRow, middleCol).hasCreature()) {
                                newCol = middleCol;
                                newRow = middleRow;
                                statusMessage = "The last move was interrupted by a creature.";
                                break;
                            }
                        }

                    }
                    moveAnimal(animalNow, animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol);
                    currentAnimalTurnType = "spell";
                    currentAnimalTurn = animalNow;
                    nextAnimalTurn = deer;
                    // check attack
                    if (animalNow.getSquare().hasCreature()) {
                        attackAnimal(animalNow.square.getCreature(), animalNow);
                        animalNow.getSquare().reveal();
                        // gameOver();
                    }
                    //save the spell
                    if (animalNow.getSquare().hasSpell()) {
                        saveSpell(animalNow, animalNow.getSquare().spell);
                        animalNow.getSquare().spell = null;
                    }
                    statusMessage = "The last move was successful.";
                    System.out.println(statusMessage);
                }
            }
            case "Deer" -> {
                Deer deerNow = (Deer) animalNow;

                checkValidTurn = (currentAnimalTurn.name.equals(animalNow.name) && currentAnimalTurnType.equals(parse.action)) || (parse.action.equals("move") && currentAnimalTurnType.equals("spell") && nextAnimalTurn.name.equals(animalNow.name));
                if (!checkValidTurn) {
                    statusMessage = "The last move was invalid.";
                    return;
                }
                checkMovable = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col) || deerNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                if (!inBound(parse.row, parse.col) || getSquare(parse.row, parse.col).hasAnimal() || !checkMovable) {
                    statusMessage = "The last move was invalid.";
                    System.out.println(statusMessage);
                } else {
                    int newCol = parse.col;
                    int newRow = parse.row;
                    int diffCol = newCol - animalNow.getSquare().col;
                    int diffRow = newRow - animalNow.getSquare().row;

                    if (deerNow.jump(animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol)) {
                        int step = Math.max(Math.abs(diffCol), Math.abs(diffRow)) - 1;
                        int middleCol;
                        int middleRow;
                        //check each interval square
                        for (int i = 1; i <= step; i++) {
                            if (animalNow.getSquare().col == newCol) {
                                middleCol = newCol;
                            } else {
                                middleCol = animalNow.getSquare().col + diffCol / Math.abs(diffCol) * i;
                            }

                            if (animalNow.getSquare().row == newRow) {
                                middleRow = newRow;
                            } else {
                                middleRow = animalNow.getSquare().row + diffRow / Math.abs(diffRow) * i;
                            }

                            if (getSquare(middleRow, middleCol).hasSpell()) {
                                getSquare(middleRow, middleCol).reveal();
                            }
                        }

                    }
                    moveAnimal(animalNow, animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol);
                    currentAnimalTurnType = "spell";
                    currentAnimalTurn = animalNow;
                    nextAnimalTurn = owl;
                    // check attack
                    if (animalNow.getSquare().hasCreature()) {
                        attackAnimal(animalNow.square.getCreature(), animalNow);
                        animalNow.getSquare().reveal();
                        // gameOver();
                    }
                    //save the spell
                    if (animalNow.getSquare().hasSpell()) {
                        saveSpell(animalNow, animalNow.getSquare().spell);
                        animalNow.getSquare().spell = null;
                    }
                    statusMessage = "The last move was successful.";
                    System.out.println(statusMessage);
                }
            }
            case "Owl" -> {
                Owl owlNow = (Owl) animalNow;

                checkValidTurn = (currentAnimalTurn.name.equals(animalNow.name) && currentAnimalTurnType.equals(parse.action)) || (parse.action.equals("move") && currentAnimalTurnType.equals("spell") && nextAnimalTurn.name.equals(animalNow.name));
                // System.out.println(currentAnimalTurn.name);
                // System.out.println(currentAnimalTurnType);
                // System.out.println(nextAnimalTurn.name);
                if (!checkValidTurn) {
                    statusMessage = "The last move was invalid.";
                    return;
                }
                checkMovable = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col) || owlNow.fly(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                if (!inBound(parse.row, parse.col) || getSquare(parse.row, parse.col).hasAnimal() || !checkMovable) {
                    statusMessage = "The last move was invalid.";
                    System.out.println(statusMessage);
                } else {
                    int newCol = parse.col;
                    int newRow = parse.row;
                    int diffCol = newCol - animalNow.getSquare().col;
                    int diffRow = newRow - animalNow.getSquare().row;
                    //check different condition for jump
                    if (owlNow.fly(animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol)) {
                        int step = Math.max(Math.abs(diffCol), Math.abs(diffRow)) - 1;
                        int middleCol;
                        int middleRow;
                        //check each interval square
                        for (int i = 1; i <= step; i++) {
                            if (animalNow.getSquare().col == newCol) {
                                middleCol = newCol;
                            } else {
                                middleCol = animalNow.getSquare().col + diffCol / Math.abs(diffCol) * i;
                            }

                            if (animalNow.getSquare().row == newRow) {
                                middleRow = newRow;
                            } else {
                                middleRow = animalNow.getSquare().row + diffRow / Math.abs(diffRow) * i;
                            }
                            if (getSquare(middleRow, middleCol).hasCreature()) {
                                if (getSquare(middleRow,middleCol).hasAnimal()){
                                    statusMessage = "The last move was invalid.";
                                    return;
                                }
                                newCol = middleCol;
                                newRow = middleRow;
                                statusMessage = "The last move was interrupted by a creature.";
                                break;
                            }
                        }

                    }
                    moveAnimal(animalNow, animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol);
                    currentAnimalTurnType = "spell";
                    currentAnimalTurn = animalNow;
                    nextAnimalTurn = badger;
                    // check attack
                    if (animalNow.getSquare().hasCreature()) {
                        attackAnimal(animalNow.square.getCreature(), animalNow);
                        animalNow.getSquare().reveal();
                        // gameOver();
                    }
                    //save the spell
                    if (animalNow.getSquare().hasSpell()) {
                        saveSpell(animalNow, animalNow.getSquare().spell);
                        animalNow.getSquare().spell = null;
                    }
                    statusMessage = "The last move was successful.";
                    System.out.println(statusMessage);
                }
            }
            case "Badger" -> {
                Badger badgeNow = (Badger) animalNow;

                checkValidTurn = (currentAnimalTurn.name.equals(animalNow.name) && currentAnimalTurnType.equals(parse.action)) || (parse.action.equals("move") && currentAnimalTurnType.equals("spell") && nextAnimalTurn.name.equals(animalNow.name));
                if (!checkValidTurn) {
                    statusMessage = "The last move was invalid.";
                    return;
                }
                boolean m = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                boolean d =  badgeNow.dig(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                checkMovable = animalNow.move(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col) || badgeNow.dig(animalNow.getSquare().row, animalNow.getSquare().col, parse.row, parse.col);
                if (!inBound(parse.row, parse.col) || getSquare(parse.row, parse.col).hasAnimal() || !checkMovable) {
                    statusMessage = "The last move was invalid.";
                    System.out.println(statusMessage);
                } else {
                    int newCol = parse.col;
                    int newRow = parse.row;


                    moveAnimal(animalNow, animalNow.getSquare().row, animalNow.getSquare().col, newRow, newCol);
                    currentAnimalTurnType = "spell";
                    currentAnimalTurn = animalNow;
                    nextAnimalTurn = rabbit;
                    // check attack
                    if (animalNow.getSquare().hasCreature()) {
                        attackAnimal(animalNow.square.getCreature(), animalNow);
                        animalNow.getSquare().reveal();
                        // gameOver();
                    }
                    //save the spell
                    if (animalNow.getSquare().hasSpell()) {
                        saveSpell(animalNow, animalNow.getSquare().getSpell());
                        animalNow.getSquare().spell = null;
                    }
                    statusMessage = "The last move was successful.";
                    System.out.println(statusMessage);
                }
            }
        }
    }

    public void checkSpell(JsonParse parse){
        Animal animalNow = null;
        for (Animal n:animals){
            if (n.name.equals(parse.animalName) ){
                animalNow = n;
                break;
            }
        }
        if (animalNow == null ){ return; }
       Spell spellNow = null;
        for (Spell spell: Spell.values()){
            if (parse.spell.equals(spell.getName())){
                spellNow = spell;
            }
        }
        if (spellNow == null){ return;}
        switch (spellNow.getName()){
            case "Detect" -> {
                for (int i=-1; i<=1; i++){
                    for (int j=-1; j<=1; j++){
                        int rowNow = animalNow.getSquare().row+i;
                        int colNow = animalNow.getSquare().col+j;
                        if (!(i==0 && j==0) && inBound(rowNow,colNow) ){
                            Square squareNow = getSquare(rowNow,colNow);
                            squareNow.reveal();
                        }
                    }
                }

            }
            case "Heal" -> {
               animalNow.lifePoints = Math.min(100,animalNow.lifePoints+10);
            }
            case "Shield" -> {
                if (animalNow.getSquare().hasCreature()) {
                    animalNow.getSquare().getCreature().addShieldAnimal(animalNow);
                }
            }
            case "Confuse" -> {
                for (int i=-1; i<=1; i++){
                    for (int j=-1; j<=1; j++){
                        int rowNow = animalNow.getSquare().row+i;
                        int colNow = animalNow.getSquare().col+j;
                        if (!(i==0 && j==0) && inBound(rowNow,colNow) ){
                            Square squareNow = getSquare(rowNow,colNow);
                            if (squareNow.hasCreature()){
                              squareNow.getCreature().setConfused(true);
                              squareNow.getCreature().confusedAnimal = animalNow;
                            }

                        }
                    }
                }
            }
            case "Charm" -> {
                for (int i=-1; i<=1; i++){
                    for (int j=-1; j<=1; j++){
                        int rowNow = animalNow.getSquare().row+i;
                        int colNow = animalNow.getSquare().col+j;
                        if (!(i==0 && j==0) && inBound(rowNow,colNow) ){
                            Square squareNow = getSquare(rowNow,colNow);
                            if (squareNow.hasCreature()){
                                Creature creatureNow = squareNow.getCreature();
                                creatureNow.addCharmAnimal(animalNow);
                            }

                        }
                    }
                }
            }

        }

        animalNow.updateSpell(spellNow);
        currentAnimalTurnType = "move";
        int index = animals.indexOf(animalNow);
        currentAnimalTurn = animals.get((index+1) % 4);
        nextAnimalTurn = animals.get((index+2) % 4);
        statusMessage = "The last spell was successful.";
    }

    public Square[][] getBoard(){
        return board;
    }
    public Square getSquare(int row,int col){
        if (board[row][col] == null){
            board[row][col] = new Square(row,col);
        }
        return board[row][col];
    }

    public void moveAnimal(Animal animal, int oldRow, int oldCol, int newRow, int newCol){
      Square oldSquare = getSquare(oldRow,oldCol);
      Square newSquare = getSquare(newRow,newCol);
      oldSquare.animal = null;
      newSquare.animal = animal;
      animal.square = newSquare;
      currentAnimalTurnType = "spell";
    }

    /** Before attack, check if the creature can execute the attack
     *
     */
    public void attackAnimal(Creature creature, Animal animal){
        // check if the animal is in the  charmed list
        for (Animal n : creature.charmAnimal.keySet()){
            if (n.name.equals(animal.name)){
                return;
            }
        }
        //check if the animal is shielded
        for (Animal n : creature.shieldAnimal){
            if (n.name.equals(animal.name)){
                return;
            }
        }
        //check if the animal is confused
        if (creature.isConfused()){
            return;
        }
       animal.lifePoints = animal.lifePoints- creature.attackValue;
    }

    public void saveSpell(Animal animal,Spell spell){
        animal.addSpell(spell);
    }

    public void castSpell(Animal animal,Spell spell){
        animal.spells.put(spell,animal.spells.get(spell)-1);
    }
    private boolean inBound (int row, int col){
        if (row<0 || col<0 || row >= upperbound || col>= upperbound){
            return false;
        }
        return true;
    }
   public boolean gameOver(){
   for (Animal n:animals){
       if (n.lifePoints<=0){
           statusMessage = "You have lost the game.";
           return true;
       }
   }
   return  false;
   }

    public boolean gameWin(){
        int count=0;
        for (Animal n:animals){
            if (n.getSquare().row ==0){
                count ++;
            }
        }
        if (count == animals.size()){
            statusMessage = "You have won the game.";
            return true;
        }
        return  false;
    }

    /** turn Check incrementation and check for shield,confuse and charm spells
     * @param animalName the name of the last valid post animal
     */
    public void turnCheck(String animalName){
        Animal animalCheck=null;
        if (animalName.equals(rabbit.name)){
            turn++;
        }
        for (Animal temp:animals){
            if (temp.name.equals(animalName)){
                animalCheck = temp;
            }
        }
        if (animalCheck == null){return;}
       for (Creature n : creatures){
         if (n.charmAnimal.containsKey(animalCheck)){
             n.updateCharmAnimal(animalCheck);
         }
         if (n.shieldAnimal.contains(animalCheck)){
             n.updateShieldAnimal(animalCheck);
         }
           if (n.confusedAnimal!=null && n.confusedAnimal.name.equals(animalCheck.name)){
               n.confusedAnimal = null;
               n.confused = false;
           }

       }
    }
}
