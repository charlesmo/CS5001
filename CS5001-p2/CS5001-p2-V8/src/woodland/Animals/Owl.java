package woodland.Animals;

public class Owl extends Animal implements Flyable{
    public Owl(String name) {
        super(name);
        description = "The owl has wings. The owl has prescription contact lenses but cannot put them on.";
    }
    @Override
    public boolean fly(int oldRow, int oldCol, int newRow, int newCol){
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        if (diffCol + diffRow >0 && diffCol * diffRow ==0){
            return true;
        }
        if (diffCol==diffRow && diffCol >0){
            return true;
        }
        return false;
    };
    // walk one square in any direction
    @Override
    public boolean move(int oldRow,int oldCol, int newRow, int newCol){
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        // Move one square in any direction
        if ((diffCol == 1 || diffRow == 1) && diffCol *diffRow <=1 ){
            return  true;
        }
        return false;
    }

}
