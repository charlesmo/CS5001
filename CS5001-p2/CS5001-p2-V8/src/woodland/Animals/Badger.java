package woodland.Animals;

public class Badger extends Animal implements Digable{
    public Badger(String name) {
        super(name);
        description = "The badger has a black and white face. The badger is a often mistaken for a very small panda. The badger wears a t-shirt that says “I am not a panda” to combat this.";
    }

    @Override
    public boolean dig(int oldRow, int oldCol, int newRow, int newCol) {
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);
        if ((diffCol == 2 || diffRow == 2 ) && (diffRow * diffRow ==0 ||diffRow * diffCol ==4)) {
            return  true;
        }
        return false;
    }

    @Override // Move one square in any direction
    public boolean move(int oldRow,int oldCol, int newRow, int newCol){
        int diffRow = Math.abs(oldRow - newRow);
        int diffCol = Math.abs(oldCol - newCol);

        if ((diffCol == 1 || diffRow == 1) && diffCol *diffRow <=1 ){
            return  true;
        }
        return false;
    }
}
