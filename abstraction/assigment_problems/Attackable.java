public interface Attackable {
    String attack();
    String attack(String weaponName);
}
public interface Defendable {
    String defend();
}
public abstract class GameCharacter {
    private static int counter = 0;
    private final String characterId;

    public GameCharacter() {
        this.characterId = "CHAR-" + (++counter);
    }

    public String getCharacterId() {
        return characterId;
    }

    public abstract String getSpecialMove();
}
public class Warrior extends GameCharacter implements Attackable, Defendable {
    private String name;

    public Warrior(String name) {
        super();
        this.name = name;
    }

    @Override
    public String attack() {
        return name + " strikes with a blade";
    }

    @Override
    public String attack(String weaponName) {
        return name + " strikes with an " + weaponName;
    }

    @Override
    public String defend() {
        return name + " raises a shield";
    }

    @Override
    public String getSpecialMove() {
        return name + " unleashes Whirlwind Slash";
    }

    public static void resolveDefense(Defendable[] combatants) {
        for (Defendable combatant : combatants) {
            System.out.println(combatant.defend());
        }
    }
}
public class Trap implements Defendable {
    private String trapType;

    public Trap(String trapType) {
        this.trapType = trapType;
    }

    @Override
    public String defend() {
        return trapType + " triggers automatically";
    }
}