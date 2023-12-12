package woodland.Creatures;

public class PrecociousPhoenix extends Creature{
    public PrecociousPhoenix(String name, int attackValue) {
        super(name, attackValue);
        shortname = "PP";
        description = "The PP is a phoenix that is very precocious. The phoenix understands the meaning of life and the universe.";
    }

    public PrecociousPhoenix(String name) {
        super(name);
        this.attackValue = 42;
    }
}
