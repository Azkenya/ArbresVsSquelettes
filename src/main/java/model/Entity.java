package model;
import model.config.Map;
import controller.Updatable;
public abstract class Entity implements Updatable {
    private int hp;
    private int line;
    private int column;
    private int damage;
    private static final Map map = new Map();

    public Entity(int hp, int line, int column, int damage, Map map) {
        this.hp = hp;
        this.line = line;
        this.column = column;
        this.damage = damage;
    }

    public void kill(int damageDealt) {
        if (this.hp > damageDealt)
            this.hp -= damageDealt;
        else {
            this.hp = 0;
            map.removeEntity(this.line, this.column);
        }
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public static Map getMap() {
        return Entity.map;
    }

}
