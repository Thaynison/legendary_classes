package www.legendarycommunity.com.br.legendary_classes;

public class PlayerClassData {
    private final String className;
    private final int classLevel;

    public PlayerClassData(String className, int classLevel) {
        this.className = className;
        this.classLevel = classLevel;
    }

    public String getClassName() {
        return className;
    }

    public int getClassLevel() {
        return classLevel;
    }
}
