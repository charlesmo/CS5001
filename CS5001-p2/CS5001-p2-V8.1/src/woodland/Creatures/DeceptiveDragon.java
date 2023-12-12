package woodland.Creatures;

public class DeceptiveDragon extends Creature{
    public DeceptiveDragon(String name, int attackValue) {
        super(name, attackValue);
        shortname = "DD";
        description = "The DD is a dragon that practices social engineering. The dragon is very good at sending phishing emails pretending to be a prince.";
    }

    public DeceptiveDragon (String name) {
        super(name);
        this.attackValue = 29;
    }


}
