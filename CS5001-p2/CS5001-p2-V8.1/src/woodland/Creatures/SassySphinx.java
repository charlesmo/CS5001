package woodland.Creatures;

public class SassySphinx extends Creature{
    public SassySphinx(String name, int attackValue) {
        super(name, attackValue);
        shortname = "SS";
        description = "The SS is a sphinx that is very sassy. The sphinx is very good at giving sarcastic answers to questions.";
    }

    public SassySphinx(String name) {
        super(name);
        this.attackValue = 21;
    }

}
