package woodland.Creatures;

public class ComplicatedCentaur extends Creature{
    public ComplicatedCentaur(String name, int attackValue) {
        super(name, attackValue);
        shortname = "CC";
        description = "The CC is a centaur that has mixed feeling about its love interest, a horse. The centaur is unsure whether they can love them fully.";
    }

    public ComplicatedCentaur(String name) {
        super(name);
        this.attackValue = 36;
    }


}
