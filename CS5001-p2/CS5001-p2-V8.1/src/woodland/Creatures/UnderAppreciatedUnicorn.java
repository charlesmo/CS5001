package woodland.Creatures;

public class UnderAppreciatedUnicorn extends Creature{


    public UnderAppreciatedUnicorn(String name, int attackValue) {
        super(name, attackValue);
        shortname = "UU";
        description = "The UU is a unicorn that is under-appreciated by the other mythical creatures because it is often mistaken for a horse with a horn.";
    }

    public UnderAppreciatedUnicorn(String name) {
             super(name);
             this.attackValue = 14;
    }

}
