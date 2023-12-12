package woodland.Animals;

public class Fox extends Animal implements  LongJumpable{

    public Fox (String name) {
        super(name);
        description = "The fox has a bushy tail. The fox really enjoys looking at butterflies in the sunlight.";
    }

    @Override
    public boolean jump(int oldRow, int oldCol, int newRow, int newCol) {
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        //jump up to three squares either horizontally or vertically
        if ((diffCol + diffRow == 2 && diffCol*diffRow ==0) || (diffCol + diffRow == 3 && diffCol*diffRow ==0)) {
            return  true;
        }
        return false;
    }
    @Override
    public boolean move(int oldRow,int oldCol, int newRow, int newCol){
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        //Move one square either horizontally or vertically
        if (diffCol + diffRow == 1 ){
            return  true;
        }
        return false;
    }

}
