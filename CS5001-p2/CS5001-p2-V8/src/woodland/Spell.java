package woodland;

public enum Spell {
    DETECT("Detect","The detect spell allows the animal to detect the mythical creatures on the adjacent squares."),
    HEAL("Heal","The heal spell allows the animal to heal 10 life points."),
    SHIELD("Shield","The shield spell allows the animal to block a mythical creature attack for that turn."),
    CONFUSE("Confuse","The confuse spell allows the animal to confuse a mythical creature on a square adjacent to the animal but not the square the animal is occupying. The mythical creature will not attack any animal for the next turn."),
    CHARM("Charm","The charm spell allows the animal to charm a mythical creature on a square adjacent to the animal but not the square the animal is occupying. The mythical creature will not attack the charming animal for the next three turns.");

    private final String description;
    private final String name;
    Spell(String name ,String description){
        this.name = name;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
}
