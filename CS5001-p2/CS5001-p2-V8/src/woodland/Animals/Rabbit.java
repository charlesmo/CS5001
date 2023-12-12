package woodland.Animals;

import woodland.Animals.Animal;

public class Rabbit extends Animal implements ShortJumpable{
    public Rabbit(String name) {
        super(name);
        description = "The rabbit has fluffy ears and tail. The rabbit really likes to eat grass.";
    }

    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);

        //Jump two squares in any directions
        if ((diffCol == 2 || diffRow == 2 ) && (diffRow * diffRow ==0 ||diffRow * diffRow ==4)) {
           return  true;
        }
        return false;
    }
    @Override
    public boolean move(int oldRow,int oldCol, int newRow, int newCol){
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        // Move one square in any direction
        if ((diffCol == 1 || diffRow == 1 ) && diffCol * diffRow <=1 ){
            return  true;
        }
        return false;
    }

}
