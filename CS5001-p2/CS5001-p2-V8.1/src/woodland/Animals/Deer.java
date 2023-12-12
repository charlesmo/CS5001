package woodland.Animals;

public class Deer extends Animal implements LongJumpable{
    public Deer(String name) {
        super(name);
        description = "The deer has antlers. The deer is recently divorced and is looking for a new partner.";
    }

    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        //jump two squares in any direction
        if ((diffCol == 2 || diffRow == 2 ) && (diffRow * diffRow ==0 ||diffRow * diffRow ==4)) {
            return  true;
        }
        //jump three squares in any direction
        if ((diffCol == 3 || diffRow == 3 ) && (diffRow * diffRow ==0 ||diffRow * diffRow ==9)) {
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
